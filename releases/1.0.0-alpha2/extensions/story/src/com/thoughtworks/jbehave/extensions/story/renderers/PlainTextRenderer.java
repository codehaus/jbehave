/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.renderers;

import java.io.PrintStream;

import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.util.ConvertCase;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.EventUsingMiniMock;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.ExpectationUsingMiniMock;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.GivenScenario;
import com.thoughtworks.jbehave.extensions.story.domain.GivenUsingMiniMock;
import com.thoughtworks.jbehave.extensions.story.domain.Narrative;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextRenderer implements Visitor {
    
    private final PrintStream out;
    private String nextWord;
    private String outcomeString;

    public PlainTextRenderer(PrintStream out) {
        this.out = out;
    }
    
    public void visit(Visitable visitable) {
        if (visitable instanceof Story) {
            visitStory((Story) visitable);
        }
        else if (visitable instanceof Narrative) {
            visitNarrative((Narrative) visitable);
        }
        else if (visitable instanceof Scenario) {
            visitScenario((Scenario) visitable);
        }
        else if (visitable instanceof Context) {
            visitContext((Context) visitable);
        }
        else if (visitable instanceof GivenUsingMiniMock) {
            visitGiven((Given) visitable);
        }
        else if (visitable instanceof EventUsingMiniMock) {
            visitEvent((Event) visitable);
        }
        else if (visitable instanceof Outcome) {
            visitOutcome((Outcome) visitable);
        }
        else if (visitable instanceof ExpectationUsingMiniMock) {
            visitExpectation((Expectation) visitable);
        }
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
        out.println(outcomeString);
    }

    public void visitOutcome(Outcome outcome) {
        nextWord = "Then ";
        outcomeString = "";
    }

    public void visitExpectation(Expectation expectation) {
        outcomeString = outcomeString + nextWord + new ConvertCase(expectation).toSeparateWords();
        nextWord = System.getProperty("line.separator") + "and ";
    }

    public void gotResult(Result result) {
    }
}
