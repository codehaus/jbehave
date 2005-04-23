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
                "and do something else\n");

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
        Verify.equal(1, details.getScenarios().size());
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
    }
}
