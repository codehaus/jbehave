/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.renderers;

import java.io.PrintStream;

import com.thoughtworks.jbehave.extensions.story.base.Event;
import com.thoughtworks.jbehave.extensions.story.base.Expectation;
import com.thoughtworks.jbehave.extensions.story.base.Given;
import com.thoughtworks.jbehave.extensions.story.base.Story;
import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.GivenScenario;
import com.thoughtworks.jbehave.extensions.story.domain.Narrative;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;
import com.thoughtworks.jbehave.util.ConvertCase;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextRenderer implements Visitor {

    private final PrintStream out;
    private String nextWord;

    public PlainTextRenderer(PrintStream out) {
        this.out = out;
    }

    public void visitStory(Story story) {
        out.println("Story: " + story.getTitle());
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
    }
    
    public void visitContext(Context context) {
        nextWord = "Given ";
    }

    public void visitGiven(Given given) {
        out.print(nextWord);
        if (given instanceof GivenScenario) {
            GivenScenario givenScenario = (GivenScenario) given;
            out.print("\"" + new ConvertCase(givenScenario.getScenario().getDescription()).toSeparateWords() + "\"");
            out.print(" from ");
            out.println("\"" + new ConvertCase(givenScenario.getStory()).toSeparateWords() + "\"");
        }
        else {
            out.println(new ConvertCase(given).toSeparateWords());
        }
        nextWord = "and ";
    }

    public void visitEvent(Event event) {
        out.println("When " + new ConvertCase(event).toSeparateWords());
    }

    public void visitOutcome(Outcome outcome) {
        nextWord = "Then ";
    }
    
    public void visitExpectationBeforeTheEvent(Expectation expectation) {
    }

    public void visitExpectationAfterTheEvent(Expectation expectation) {
        out.println(nextWord + new ConvertCase(expectation).toSeparateWords());
        nextWord = "and ";
    }
}
