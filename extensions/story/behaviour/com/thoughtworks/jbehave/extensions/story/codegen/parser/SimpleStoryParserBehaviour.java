/*
 * Created on 23-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.parser;

import java.io.StringReader;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.BasicDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ContextDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.OutcomeDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ScenarioDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class SimpleStoryParserBehaviour {
    
    private String storyText = "Story: User withdraws cash\n" + "\n"
    					+ "As a Bank card holder\n"
    					+ "I want to be able to withdraw cash from an ATM\n"
    					+ "So that I don't have to visit the bank\n";
    
    private String happyScenario = "Scenario: Happy scenario\n" +
		"Given Account is in credit (set balance = 50)\n" +
		"When User requests cash (user requests 20)\n" +
		"Then ATM should dispense cash (ATM dispenses 20)\n";
    
    private String overdraftScenario = "Scenario: Happy story with overdraft\n" +
		"Given Account has overdraft facility\n" +
		"and Account is easily within overdraft limit\n" +
		"When User requests cash (user requests 20)\n" +
		"Then ATM should dispense cash (ATM dispenses 20)\n";
    
    private SimpleStoryParser storyParser;
    
    public void setUp() {
        storyParser = new SimpleStoryParser();
    }
    
    public void shouldParseStoryTitle() throws Exception {
        // given...
        storyParser = new SimpleStoryParser();

        StoryDetails expectedDetails = createStoryDetailsForDefaultStory();

        // when...
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(
                storyText));

        // verify...
        Verify.equal(expectedDetails, storyDetails);
    }

    /**
     * @return
     */
    private StoryDetails createStoryDetailsForDefaultStory() {
        // expect...
        StoryDetails expectedDetails = new StoryDetails("User withdraws cash",
                "Bank card holder", "be able to withdraw cash from an ATM",
                "I don't have to visit the bank");
        return expectedDetails;
    }

    public void shouldParseOtherStories() throws Exception {
        // given...
        String otherStoryText = "Story: User deposits cash\n" + "\n"
                + "As a Bank card holder\n"
                + "I want to be able to deposit cash via an ATM\n"
                + "So that I don't have to visit the bank\n";
       
        // expect...
        StoryDetails expectedDetails = new StoryDetails("User deposits cash",
                "Bank card holder", "be able to deposit cash via an ATM",
                "I don't have to visit the bank");

        // when...
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(
                otherStoryText));

        // verify...
        Verify.equal(expectedDetails, storyDetails);
    }
    
    private ScenarioDetails createHappyScenarioDetails() {
        OutcomeDetails outcome = new OutcomeDetails();
        outcome.addExpectation(new BasicDetails("ATM should dispense cash", "ATM dispenses 20"));
        ContextDetails context = new ContextDetails();
        context.addGiven(new BasicDetails("Account is in credit", "set balance = 50"));
        BasicDetails event = new BasicDetails("User requests cash", "user requests 20");
        return new ScenarioDetails("Happy scenario", context, event, outcome);
    }
    
    public void shouldParseStoryWithSimpleScenario() throws Exception {
        // given...
        String storyWithScenario = storyText + "\n" + happyScenario;
        
        // expect...  
        StoryDetails expectedDetails = createStoryDetailsForDefaultStory();
        expectedDetails.addScenario(createHappyScenarioDetails());
        
        // when...
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(storyWithScenario));
        
        // verify...
        Verify.equal(expectedDetails, storyDetails);
    }
    
    public void shouldParseStoryWithMultipleExpectations() throws Exception {
        // given
        String otherExpectations =  "and ATM should return bank card\n" +
        							"and account balance should be reduced (account balance = 30)\n";
        
        String story = storyText + "\n" + happyScenario + otherExpectations;
        
        // expect ...
        StoryDetails expectedDetails = createStoryDetailsForDefaultStory();
        ScenarioDetails scenarioDetails = createHappyScenarioDetails();
        
        OutcomeDetails outcomeDetails = scenarioDetails.getOutcome();
        outcomeDetails.addExpectation(new BasicDetails("ATM should return bank card", ""));
        outcomeDetails.addExpectation(new BasicDetails("account balance should be reduced", "account balance = 30"));
        expectedDetails.addScenario(scenarioDetails);
        
        // when ...
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(story));
        
        // verify...
        Verify.equal(expectedDetails, storyDetails);      
    }
    
    private ScenarioDetails createOverdraftScenarioDetails() {
        OutcomeDetails outcome = new OutcomeDetails();
        outcome.addExpectation(new BasicDetails("ATM should dispense cash", "ATM dispenses 20"));
        ContextDetails context = new ContextDetails();
        context.addGiven(new BasicDetails("Account has overdraft facility", ""));
        context.addGiven(new BasicDetails("Account is easily within overdraft limit", ""));
        BasicDetails event = new BasicDetails("User requests cash", "user requests 20");
        return new ScenarioDetails("Happy story with overdraft", context, event, outcome);
    }
    
    public void shouldParseScenarioWithMultipeGivens() throws Exception {
        // given
        
        String story = storyText + "\n" + overdraftScenario;
        
        // expect
        StoryDetails expectedDetails = createStoryDetailsForDefaultStory();
        expectedDetails.addScenario(createOverdraftScenarioDetails());
        
        // when
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(story));
        
        // verify
        Verify.equal(expectedDetails, storyDetails);        
    }
    
    public void shouldParseScenariosWithMixedCaseKeywords() {
        //      given
        String scenario = "scenario: Happy story with overdraft\n" +
        		"gIvEn Account has overdraft facility\n" +
        		"And Account is easily within overdraft limit\n" +
        		"when User requests cash (user requests 20)\n" +
        		"theN ATM should dispense cash (ATM dispenses 20)\n";
        
        String story = storyText + "\n" + scenario;
        
        // expect
        StoryDetails expectedDetails = createStoryDetailsForDefaultStory();
        expectedDetails.addScenario(createOverdraftScenarioDetails());
        
        // when
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(story));
        
        // verify
        Verify.equal(expectedDetails, storyDetails);
    }
    
    public void shouldParseStoryWithMultipleScenarios() throws Exception {
        // given
        String story = storyText + "\n" + happyScenario + "\n" + overdraftScenario;
        
        // expect
        StoryDetails expectedDetails = createStoryDetailsForDefaultStory();
        expectedDetails.addScenario(createHappyScenarioDetails());
        expectedDetails.addScenario(createOverdraftScenarioDetails());
       
        // when
        StoryDetails storyDetails = storyParser.parseStory(new StringReader(story));
        
        // verify
        Verify.equal(expectedDetails, storyDetails);        
    }
}