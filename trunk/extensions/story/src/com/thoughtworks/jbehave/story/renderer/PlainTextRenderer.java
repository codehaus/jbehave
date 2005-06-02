/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.renderer;

import java.io.PrintStream;

import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.util.ConvertCase;
import com.thoughtworks.jbehave.story.domain.Givens;
import com.thoughtworks.jbehave.story.domain.Event;
import com.thoughtworks.jbehave.story.domain.Outcome;
import com.thoughtworks.jbehave.story.domain.Given;
import com.thoughtworks.jbehave.story.domain.GivenScenario;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Outcomes;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.domain.Story;
import com.thoughtworks.jbehave.story.visitor.VisitorSupport;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextRenderer extends VisitorSupport {
    
    private final PrintStream out;
	private boolean visitedAnyOutcomes = false;
	private boolean visitedAnyGivens = false;
	private StringBuffer outcomesText = new StringBuffer();

    public PlainTextRenderer(PrintStream out) {
        this.out = out;
    }
        
    public void visitStory(Story story) {
        out.println("Story: " + story.title());
        out.println();
    }
    
    public void visitNarrative(Narrative narrative) {
        out.println("As a " + narrative.getRole());
        out.println("I want " + narrative.getFeature());
        out.println("So that " + narrative.getBenefit());

    }

    public void visitScenario(Scenario scenario) {
        out.println();
        out.println("Scenario: " + scenario.getDescription());
        out.println();
		visitedAnyGivens = false;
		visitedAnyOutcomes = false;
		outcomesText = new StringBuffer();
    }
    
    public void visitGiven(Given given) {
		StringBuffer phrase = new StringBuffer();
		phrase.append(visitedAnyGivens ? "and " : "Given ");
        if (given instanceof GivenScenario) {
            Scenario givenScenario = ((GivenScenario) given).getScenario();
            phrase.append("\"" + new ConvertCase(givenScenario.getDescription()).toSeparateWords() + "\"")
            	.append(" from ").append("\"" + new ConvertCase(givenScenario.getStoryName()).toSeparateWords() + "\"");
        }
        else {
            phrase.append(new ConvertCase(given).toSeparateWords());
        }
		out.println(phrase);
		visitedAnyGivens = true;
    }

    public void visitEvent(Event event) {
        out.println("When " + new ConvertCase(event).toSeparateWords());
		out.print(outcomesText);
    }

    public void visitOutcome(Outcome outcome) {
		outcomesText.append(visitedAnyOutcomes ? "and " : "Then ")
			.append(new ConvertCase(outcome).toSeparateWords())
			.append(System.getProperty("line.separator"));
		visitedAnyOutcomes = true;
    }

    public void gotResult(Result result) {
    }
}
