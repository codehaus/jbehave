package org.jbehave.scenario.steps;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;

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
 * <pre>
 * <code lang="java"> 
 * @When("I log in as $username with password: $password") <br/> 
 * public void logIn(String username, String password) { //... } 
 * </code>
 * </pre>
 * and this would match the step:
 * <pre>
 * When I log in as Liz with password: Pa55word
 * </pre>
 * </p>
 * <p>When the step is perfomed, the parameters in the scenario
 * definition will be passed to the class, so in this case the
 * effect will be
 * <pre>
 * mySteps.logIn(&quot;Liz&quot;, &quot;Pa55word&quot;);
 * </pre>
 * </p>
 * <p>
 * StepsConfiguration can be used to provide customization to the defaults configuration
 * elements, eg custom parameters converters. 
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
     * Creates Steps with all custom dependencies
     * 
     * @param configuration the StepsConfiguration
     */
    public Steps(StepsConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * @return all the steps that can be performed by this class
     */
    public CandidateStep[] getSteps() {
        List<CandidateStep> steps = new ArrayList<CandidateStep>();
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

    private void createCandidateStep(List<CandidateStep> steps, Method method, String stepAsString) {
        steps.add(new CandidateStep(stepAsString, method, this, configuration.getPatternBuilder(), configuration
                .getMonitor(), configuration.getParameterConverters(), configuration.getStartingWords()));
    }
}
