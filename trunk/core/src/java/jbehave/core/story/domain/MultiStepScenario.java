package jbehave.core.story.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import jbehave.core.story.renderer.Renderer;

/**
 * <p>A Scenario describes a series of events, run in a particular
 * context, and for which certain outcomes are expected.</p>
 * 
 * <p>This class allows a scenario to be implemented with
 * instances of particular givens, events and outcomes.
 * Each {@link Given}, {@link Event} or {@link Outcome} should be 
 * represented by a different class, which can be reused in different
 * scenarios.</p>
 * 
 * <p>A scenario's elements can be used to describe it thus:<ul>
 * <li>Given <a context></li>
 * <li>When <an event happens></li>
 * <li>Then <an outcome should occur></li>.
 * </ul></p>
 * 
 * <p>eg:<ul>
 * <li>Given that I already have two crosses in opposite corners</li>
 * <li>When I put a cross in the middle</li>
 * <li>Then I should win the game.</li>
 * </ul></p>
 * 
 * <p>Sometimes a scenario may only finish after several events and
 * the outcomes that result from them. Also, you may want to carry out
 * checks on the way through using Outcomes that are not related to
 * the outcome of the scenario. For instance, you may login to a web app
 * and then ensure you are at the "welcome" page before continuing with
 * the scenario. Each Given, Event and Outcome is considered a {@link Step},
 * and Steps can be chained together in any arbitrary sequence to accomplish
 * this.</p>
 * 
 * <p>Each scenario runs as follows:<ul>
 * <li>Assemble the steps (Givens, Events and Outcomes)</li>
 * <li>Perform each step</li>
 * <li>In reverse order, tidy up each step</li>
 * </ul>
 * 
 * <p>Multiple contexts, events and outcomes can be bound together
 * using {@link Givens}, {@link Events} and {@link Outcomes}, so that
 * they aggregate to form a single component.</p>
 * 
 * <p>A number of utility constructors are provided to make construction
 * of a ScenarioUsingSteps easier. The annotation methods <code>given</code>,
 * <code>when</code> and <code>then</code> can be used to make the Scenario
 * code easier to read, and to create aggregates simply.</p>
 * 
 * <p>ScenarioUsingSteps also gives you easy access to JBehave's MiniMock
 * framework. This allows you to mock out the bits of the system that you
 * haven't written yet. The Scenario will report the use of mocks, but
 * will not consider them a failure.</p>
 *
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:liz@thoughtworks.com">Elizabeth Keogh</a>
 */
public abstract class MultiStepScenario implements Scenario {
    private List steps = new ArrayList();
    
    public MultiStepScenario() {
        assemble();
    }

    public abstract void assemble();

    public void run(World world) {
        perform(world);
        cleanUp(world);
    }

    public void perform(World world) {
        for (Iterator i = steps.iterator(); i.hasNext();) {
            Step step = (Step) i.next();
            step.perform(world);
        }
    }

    public void cleanUp(World world) {
        for (ListIterator i = steps.listIterator(steps.size()); i.hasPrevious();) {
            Object step = i.previous();
            if (step instanceof HasCleanUp) {
                ((HasCleanUp)step).cleanUp(world);
            }
        }
    }

    public void narrateTo(Renderer renderer) {
        renderer.renderScenario(this);
        for (Iterator i = steps.iterator(); i.hasNext();) {
            ((Step)i.next()).narrateTo(renderer);
        }
    }

    public boolean containsMocks() {
        for (Iterator i = steps.iterator(); i.hasNext();) {
            Step step = (Step) i.next();
            if (step.containsMocks()) {
                return true;
            }
        }
        return false;
    }
    
    public void verifyMocks() {
        
    }
    
    protected void given(Given given) {
        steps.add(new GivenStep(given));
    }
    
    protected void given(Scenario scenario) {
        steps.add(new GivenStep(new GivenScenario(scenario)));
    }

    protected void when(Event event) {
        steps.add(new EventStep(event));
    }
    
    protected void then(Outcome outcome) {
        steps.add(new OutcomeStep(outcome));
    }
}
