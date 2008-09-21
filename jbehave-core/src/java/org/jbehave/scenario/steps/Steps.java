package org.jbehave.scenario.steps;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.StepPatternBuilder;

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
 * For instance, you could define a method as: <code lang="java">
 * 
 * @When(&quot; I log in as $username with password: $password&quot;) public
 *                void logIn(String username, String password) { //... } </code>
 *                and this would match the step:
 *                <code>When I log in as Liz with password: Pa55word</code>
 *                <p>
 *                When the step is perfomed, the parameters in the scenario
 *                definition will be passed to the class, so in this case the
 *                effect will be <code>mySteps.logIn("Liz", "Pa55word");</code>
 *                </p>
 *                <p>
 *                ParameterConverters can be used to convert parameters from any
 *                String format to another class. Custom converters can be
 *                provided here in addition to the defaults.
 *                </p>
 */
public class Steps {

    private final StepPatternBuilder patternBuilder;
    private final StepMonitor stepMonitor;
    private final ParameterConverters parameterConverters;
    private final String[] startingWords;

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
     * Creates Steps with all custom dependencies
     * 
     * @param configuration the StepsConfiguration
     */
    public Steps(StepsConfiguration configuration) {
        this.patternBuilder = configuration.getPatternBuilder();
        this.stepMonitor = configuration.getMonitor();
        this.parameterConverters = configuration.getParameterConverters();
        this.startingWords = configuration.getStartingWords();
    }

    /**
     * @return all the steps that can be performed by this class
     */
    public CandidateStep[] getSteps() {
        ArrayList<CandidateStep> steps = new ArrayList<CandidateStep>();
        for (Method method : this.getClass().getMethods()) {
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

    private void createCandidateStep(ArrayList<CandidateStep> steps, Method method, String stepAsString) {
        steps.add(new CandidateStep(stepAsString, method, this, patternBuilder, stepMonitor, parameterConverters,
                startingWords));
    }
}
