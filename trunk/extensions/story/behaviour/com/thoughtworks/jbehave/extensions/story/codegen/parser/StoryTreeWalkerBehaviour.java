package com.thoughtworks.jbehave.extensions.story.codegen.parser;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.BasicDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ContextDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ScenarioDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.OutcomeDetails;

import java.io.Reader;
import java.io.StringReader;

public class StoryTreeWalkerBehaviour {

    // This should be split up into multiple behaviours, just want to get it working
    // for now.
    public void shouldParseTheStory() throws TokenStreamException, RecognitionException {
        Reader r = new StringReader(
                "Story: this is some text\n" +
                "As_a user\n" +
                "I_want some food\n" +
                "So_that I can do stuff\n" +
                "Scenario: Happy Scenario\n" +
                "Given a sentence\n" +
                "and another sentence\n" +
                "When something happens\n" +
                "Then do something interesting\n" +
                "and do something else\n" +
                "Scenario: UnHappy Path\n" +
                "Given some other sentence\n" +
                "When some other thing happens\n" +
                "Then do something good\n");

        StoryLexer lexer = new StoryLexer(r);
        AntlrStoryParser parser = new AntlrStoryParser(lexer);

        parser.story();
        StoryTreeWalker walker = new StoryTreeWalker();
        StoryDetails details = walker.storyDetail(parser.getAST());
        Verify.notNull(details);
        Verify.equal("this is some text",details.getName());
        Verify.equal("user",details.getRole());
        Verify.equal("some food",details.getFeature());
        Verify.equal("I can do stuff",details.getBenefit());
        Verify.equal(2, details.getScenarios().size());
        ScenarioDetails scenario = (ScenarioDetails) details.getScenarios().get(0);
        Verify.equal("Happy Scenario", scenario.getName());
        ContextDetails ctx = scenario.getContext();
        Verify.equal("a sentence", ((BasicDetails)ctx.getGivens().get(0)).getName());
        Verify.equal("another sentence", ((BasicDetails)ctx.getGivens().get(1)).getName());
        BasicDetails event = scenario.getEvent();
        Verify.equal("something happens", event.getName());
        OutcomeDetails outcome = scenario.getOutcome();
        Verify.equal("do something interesting",((BasicDetails)outcome.getExpectations().get(0)).getName());
        Verify.equal("do something else", ((BasicDetails)outcome.getExpectations().get(1)).getName());

        ScenarioDetails scenario2 = (ScenarioDetails)details.getScenarios().get(1);
        Verify.equal("UnHappy Path", scenario2.getName());
        ContextDetails ctx2 = scenario2.getContext();
        Verify.equal("some other sentence", ((BasicDetails)ctx2.getGivens().get(0)).getName());
        BasicDetails s2Event = scenario2.getEvent();
        Verify.equal("some other thing happens",s2Event.getName());
        OutcomeDetails s2Outcome = scenario2.getOutcome();
        Verify.equal("do something good", ((BasicDetails)s2Outcome.getExpectations().get(0)).getName());
    }

}
