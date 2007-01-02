/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.renderer;

import java.io.PrintStream;

import org.jbehave.core.result.Result;
import org.jbehave.core.story.domain.Event;
import org.jbehave.core.story.domain.Given;
import org.jbehave.core.story.domain.GivenScenario;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.Outcome;
import org.jbehave.core.story.domain.Scenario;
import org.jbehave.core.story.domain.ScenarioComponent;
import org.jbehave.core.story.domain.Story;
import org.jbehave.core.util.CamelCaseConverter;


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
        out.println("Story: " + new CamelCaseConverter(story).toPhrase());
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
        out.println("Scenario: " + new CamelCaseConverter(scenario).toPhrase());
        out.println();
    }
    
    public void renderGiven(Given given) {
		StringBuffer phrase = new StringBuffer();
		phrase.append(previousComponentWasA(Given.class) ? "and " : "Given ");
        if (given instanceof GivenScenario) {
            Scenario givenScenario = ((GivenScenario) given).getScenario();
            phrase.append(new CamelCaseConverter(givenScenario).toPhrase());
        }
        else {
            phrase.append(new CamelCaseConverter(given).toPhrase());
        }
        previousComponent = given;
		out.println(phrase);
    }


    public void renderEvent(Event event) {
        String phrase = (previousComponentWasA(Event.class) ? "and " : "When ") + new CamelCaseConverter(event).toPhrase();
        previousComponent = event;
        out.println(phrase);
    }

    public void renderOutcome(Outcome outcome) {
        StringBuffer phrase = new StringBuffer();
        phrase.append(previousComponentWasA(Outcome.class) ? "and " : "Then ")
			.append(new CamelCaseConverter(outcome).toPhrase());

        previousComponent = outcome;
        out.println(phrase);
    }

    public void render(Object obj) {
        out.println(obj.toString());
    }

    private boolean previousComponentWasA(Class clazz) {
        return previousComponent != null && clazz.isAssignableFrom(previousComponent.getClass());
    }    
    
    public void gotResult(Result result) {
    }

}
