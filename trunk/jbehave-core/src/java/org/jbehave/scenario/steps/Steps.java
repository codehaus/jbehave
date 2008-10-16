package org.jbehave.scenario.steps;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.annotations.AfterSuccessfulScenario;
import org.jbehave.scenario.annotations.AfterUnsuccessfulScenario;
import org.jbehave.scenario.annotations.BeforeScenario;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
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
     * @param startingWords the words with which we expect steps in the
     *            scenarios to start
     */
    public Steps(String... startingWords) {
        this(new StepsConfiguration(startingWords));
    }
    
    /**
     * Creates Steps with all default dependencies except for custom parameter converters.
     * 
     * @param converters a set of converters which can change strings into other objects to pass into executable steps
     */
    public Steps(ParameterConverters converters) {
    	this(new StepsConfiguration(converters));
    }

    /**
     * Creates Steps with all custom dependencies
     * 
     * @param configuration the StepsConfiguration
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
                createCandidateStep(steps, method, method.getAnnotation(Given.class).value());
            }
            if (method.isAnnotationPresent(When.class)) {
                createCandidateStep(steps, method, method.getAnnotation(When.class).value());
            }
            if (method.isAnnotationPresent(Then.class)) {
                createCandidateStep(steps, method, method.getAnnotation(Then.class).value());
            }
        }
        return steps.toArray(new CandidateStep[steps.size()]);
    }

    void createCandidateStep(List<CandidateStep> steps, Method method, String stepAsString) {
        steps.add(new CandidateStep(stepAsString, method, this, configuration.getPatternBuilder(), configuration
                .getMonitor(), configuration.getParameterConverters(), configuration.getStartingWords()));
    }

	public List<Step> runBeforeScenario() {
		return stepsHaving(BeforeScenario.class, new OkayToRun(), new OkayToRun());
	}
	
	public List<Step> runAfterScenario() {
		List<Step> steps = new ArrayList<Step>();
		steps.addAll(stepsHaving(AfterScenario.class, new OkayToRun(), new OkayToRun()));
		steps.addAll(stepsHaving(AfterSuccessfulScenario.class, new OkayToRun(), new DoNotRun()));
		steps.addAll(stepsHaving(AfterUnsuccessfulScenario.class, new DoNotRun(), new OkayToRun()));
		return steps;
	}

	private List<Step> stepsHaving(final Class<? extends Annotation> annotation, final StepPart forSuccessfulScenarios, final StepPart forUnsuccessfulScenarios) {
		ArrayList<Step> steps = new ArrayList<Step>();
        for (final Method method : this.getClass().getMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				steps.add(new Step() {

					public StepResult doNotPerform() {
						return forUnsuccessfulScenarios.run(annotation, method);
					}

					public StepResult perform() {
								return forSuccessfulScenarios.run(annotation, method);
					}
					
				});
			}
        }
        return steps;
	}
	
	private class OkayToRun implements StepPart {
		public StepResult run(final Class<? extends Annotation> annotation, Method method) {
			try {
				method.invoke(Steps.this);
			} catch (InvocationTargetException e) {
				if (e.getCause() != null) { throw new BeforeOrAfterScenarioException(annotation, method, e.getCause()); }
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			return new SilentStepResult();
		}
	}
	
	private class DoNotRun implements StepPart {
		public StepResult run(Class<? extends Annotation> annotation, Method method) {
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
		public void describeTo(ScenarioReporter reporter) {}
	}
}
