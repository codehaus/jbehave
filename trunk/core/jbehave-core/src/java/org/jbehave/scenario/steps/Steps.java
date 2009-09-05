package org.jbehave.scenario.steps;

import static org.jbehave.scenario.annotations.AfterScenario.Outcome.ANY;
import static org.jbehave.scenario.annotations.AfterScenario.Outcome.FAILURE;
import static org.jbehave.scenario.annotations.AfterScenario.Outcome.SUCCESS;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.annotations.*;
import org.jbehave.scenario.annotations.AfterScenario.Outcome;
import org.jbehave.scenario.errors.BeforeOrAfterScenarioException;
import org.jbehave.scenario.reporters.ScenarioReporter;

/**
 * <p>
 * Extend this class to provide the definition of steps that match the scenario
 * you want to run.
 * </p>
 * <p>
 * You can define the methods that should be run when each step is performed by
 * annotating them with @Given, @When or @Then, and providing a value for each
 * annotation that matches the step. By default, the match is performed using a
 * '$' prefix to pick up parameters.
 * </p>
 * <p>
 * For instance, you could define a method as:
 * 
 * <pre>
 * &lt;code lang=&quot;java&quot;&gt; 
 * &#064;When(&quot;I log in as $username with password: $password&quot;) &lt;br/&gt; 
 * public void logIn(String username, String password) { //... } 
 * &lt;/code&gt;
 * </pre>
 * 
 * and this would match the step:
 * 
 * <pre>
 * When I log in as Liz with password: Pa55word
 * </pre>
 * 
 * </p>
 * <p>
 * When the step is perfomed, the parameters in the scenario definition will be
 * passed to the class, so in this case the effect will be
 * 
 * <pre>
 * mySteps.logIn(&quot;Liz&quot;, &quot;Pa55word&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * StepsConfiguration can be used to provide customization to the defaults
 * configuration elements, eg custom parameters converters.
 * </p>
 */
public class Steps implements CandidateSteps {

	private final StepsConfiguration configuration;

	/**
	 * Creates Steps with default configuration
	 */
	public Steps() {
		this(new StepsConfiguration());
	}

	/**
	 * Creates Steps with all default configuration except for custom starting
	 * keywords
	 * 
	 * @param startingWords
	 *            the words with which we expect steps in the scenarios to start
	 */
	public Steps(String... startingWords) {
		this(new StepsConfiguration(startingWords));
	}

	/**
	 * Creates Steps with all default dependencies except for custom parameter
	 * converters.
	 * 
	 * @param converters
	 *            a set of converters which can change strings into other
	 *            objects to pass into executable steps
	 */
	public Steps(ParameterConverters converters) {
		this(new StepsConfiguration(converters));
	}

	/**
	 * Creates Steps with all custom dependencies
	 * 
	 * @param configuration
	 *            the StepsConfiguration
	 */
	public Steps(StepsConfiguration configuration) {
		this.configuration = configuration;
	}

	public CandidateStep[] getSteps() {
		return getSteps(this.getClass());
	}

	public CandidateStep[] getSteps(Class<?> stepsClass) {
		List<CandidateStep> steps = new ArrayList<CandidateStep>();
		for (Method method : stepsClass.getMethods()) {
			if (method.isAnnotationPresent(Given.class)) {
				String value = method.getAnnotation(Given.class).value();
				createCandidateStep(steps, method, value);
				createCandidateStepsFromAliases(steps, method);
			}
			if (method.isAnnotationPresent(When.class)) {
				String value = method.getAnnotation(When.class).value();
				createCandidateStep(steps, method, value);
				createCandidateStepsFromAliases(steps, method);
			}
			if (method.isAnnotationPresent(Then.class)) {
				String value = method.getAnnotation(Then.class).value();
				createCandidateStep(steps, method, value);
				createCandidateStepsFromAliases(steps, method);
			}
		}
		return steps.toArray(new CandidateStep[steps.size()]);
	}

	private void createCandidateStep(List<CandidateStep> steps, Method method,
			String stepAsString) {
		checkForDuplicateCandidateSteps(steps, stepAsString);
        CandidateStep step = new CandidateStep(stepAsString, method, this, configuration
                .getPatternBuilder(), configuration
                .getParameterConverters(), configuration.getStartingWords());
        step.useStepMonitor(configuration.getMonitor());        
        step.useParanamer(configuration.getParanamer());
        steps.add(step);
	}

	private void checkForDuplicateCandidateSteps(List<CandidateStep> steps,
			String stepAsString) {
		for (CandidateStep step : steps) {
			if (step.getStepAsString().equals(stepAsString)) {
				throw new DuplicateCandidateStepFoundException(stepAsString);
			}
		}
	}

	private void createCandidateStepsFromAliases(List<CandidateStep> steps,
			Method method) {
		if (method.isAnnotationPresent(Aliases.class)) {
			String[] aliases = method.getAnnotation(Aliases.class).values();
			for (String alias : aliases) {
				createCandidateStep(steps, method, alias);
			}
		}
		if (method.isAnnotationPresent(Alias.class)) {
		    createCandidateStep(steps, method, method.getAnnotation(Alias.class).value());
		}
	}

	public List<Step> runBeforeScenario() {
		return stepsHaving(BeforeScenario.class, new OkayToRun());
	}

	public List<Step> runAfterScenario() {
		List<Step> steps = new ArrayList<Step>();
		steps.addAll(stepsHavingOutcome(AfterScenario.class, ANY,
				new OkayToRun(), new OkayToRun()));
		steps.addAll(stepsHavingOutcome(AfterScenario.class, SUCCESS,
				new OkayToRun(), new DoNotRun()));
		steps.addAll(stepsHavingOutcome(AfterScenario.class, FAILURE,
				new DoNotRun(), new OkayToRun()));
		return steps;
	}

	private List<Step> stepsHaving(
			final Class<? extends Annotation> annotationClass,
			final StepPart forScenarios) {
		List<Step> steps = new ArrayList<Step>();
		for (final Method method : this.getClass().getMethods()) {
			if (method.isAnnotationPresent(annotationClass)) {
				steps.add(new Step() {
					public StepResult doNotPerform() {
						return forScenarios.run(annotationClass, method);
					}

					public StepResult perform() {
						return forScenarios.run(annotationClass, method);
					}

				});
			}
		}
		return steps;
	}

	private List<Step> stepsHavingOutcome(
			final Class<? extends AfterScenario> annotationClass,
			final Outcome outcome, final StepPart forSuccessfulScenarios,
			final StepPart forUnsuccessfulScenarios) {
		List<Step> steps = new ArrayList<Step>();
		for (final Method method : this.getClass().getMethods()) {
			if (method.isAnnotationPresent(annotationClass)) {
				AfterScenario annotation = method
						.getAnnotation(annotationClass);
				if (outcome.equals(annotation.uponOutcome())) {
					steps.add(new Step() {

						public StepResult doNotPerform() {
							return forUnsuccessfulScenarios.run(
									annotationClass, method);
						}

						public StepResult perform() {
							return forSuccessfulScenarios.run(annotationClass,
									method);
						}

					});
				}
			}
		}
		return steps;
	}

	private class OkayToRun implements StepPart {
		public StepResult run(final Class<? extends Annotation> annotation,
				Method method) {
			try {
				method.invoke(Steps.this);
			} catch (InvocationTargetException e) {
				if (e.getCause() != null) {
					throw new BeforeOrAfterScenarioException(annotation,
							method, e.getCause());
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			return new SilentStepResult();
		}
	}

	private class DoNotRun implements StepPart {
		public StepResult run(Class<? extends Annotation> annotation,
				Method method) {
			return new SilentStepResult();
		}
	}

	private interface StepPart {
		StepResult run(Class<? extends Annotation> annotation, Method method);
	}

	public class SilentStepResult extends StepResult {
		public SilentStepResult() {
			super("");
		}

		@Override
		public void describeTo(ScenarioReporter reporter) {
		}
	}

	@SuppressWarnings("serial")
	public static class DuplicateCandidateStepFoundException extends RuntimeException {

		public DuplicateCandidateStepFoundException(String message) {
			super(message);
		}

	}
}
