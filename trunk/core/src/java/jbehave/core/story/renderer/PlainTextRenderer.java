/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.renderer;

import java.io.PrintStream;

import jbehave.core.result.Result;
import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.GivenScenario;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.ScenarioComponent;
import jbehave.core.story.domain.Scenarios;
import jbehave.core.story.domain.Story;
import jbehave.core.util.ConvertCase;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextRenderer implements Renderer {
    
    private final PrintStream out;
    private ScenarioComponent previousComponent;

    public PlainTextRenderer(PrintStream out) {
        this.out = out;
    }
        
    public void renderStory(Story story) {
        previousComponent = null;
        out.println("Story: " + story.title());
        out.println();
    }
    
    public void renderNarrative(Narrative narrative) {
        previousComponent = null;
        out.println("As a " + narrative.getRole());
        out.println("I want " + narrative.getFeature());
        out.println("So that " + narrative.getBenefit());

    }

    public void renderScenario(Scenario scenario) {
        previousComponent = null;
        out.println();
        out.println("Scenario: " + scenario.getDescription());
        out.println();
    }
    
    public void renderGiven(Given given) {
		StringBuffer phrase = new StringBuffer();
		phrase.append(previousComponentWasA(Given.class) ? "and " : "Given ");
        if (given instanceof GivenScenario) {
            Scenario givenScenario = ((GivenScenario) given).getScenario();
            phrase.append("\"" + new ConvertCase(givenScenario.getDescription()).toSeparateWords() + "\"")
            	.append(" from ").append("\"" + new ConvertCase(givenScenario.getStoryName()).toSeparateWords() + "\"");
        }
        else {
            phrase.append(new ConvertCase(given).toSeparateWords());
        }
        previousComponent = given;
		out.println(phrase);
    }


    public void renderEvent(Event event) {
        String phrase = (previousComponentWasA(Event.class) ? "and " : "When ") + new ConvertCase(event).toSeparateWords();
        previousComponent = event;
        out.println(phrase);
    }

    public void renderOutcome(Outcome outcome) {
        StringBuffer phrase = new StringBuffer();
        phrase.append(previousComponentWasA(Outcome.class) ? "and " : "Then ")
			.append(new ConvertCase(outcome).toSeparateWords());

        previousComponent = outcome;
        out.println(phrase);
    }


    private boolean previousComponentWasA(Class clazz) {
        return previousComponent != null && clazz.isAssignableFrom(previousComponent.getClass());
    }    
    
    public void renderScenarios(Scenarios criteria) {
        // Do nothing
    }

    public void gotResult(Result result) {
    }

}
