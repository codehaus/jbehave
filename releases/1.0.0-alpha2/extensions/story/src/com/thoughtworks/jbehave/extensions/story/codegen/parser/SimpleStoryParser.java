/*
 * Created on 23-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import com.thoughtworks.jbehave.extensions.story.codegen.CodeGenerator;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.BasicDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ContextDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.OutcomeDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ScenarioDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class SimpleStoryParser implements StoryParser {
    
    class KeywordMatcher {
        
        private final String matchingToken;

        public KeywordMatcher(String matchingToken) {
            this.matchingToken = matchingToken;          
        }
        
        boolean matches(String line) {
           if (line == null) return false;
           return line.toUpperCase().startsWith(matchingToken.toUpperCase());
        }
        
        int keywordLength() {
            return matchingToken.length();
        }
    }
    
    private KeywordMatcher thenMatcher = new KeywordMatcher("then ");
    private KeywordMatcher andMatcher = new KeywordMatcher("and ");
    private KeywordMatcher givenMatcher = new KeywordMatcher("given ");
    private KeywordMatcher whenMatcher = new KeywordMatcher("when ");
    private KeywordMatcher scenarioMatcher = new KeywordMatcher("scenario: ");
    private final CodeGenerator codeGenerator;
  
    public SimpleStoryParser(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    public StoryDetails parseStory(Reader reader) {
        BufferedReader buffered = new BufferedReader(reader);
        try {
            StoryDetails storyDetails = parseStoryText(buffered);
            codeGenerator.generateStory(storyDetails);
            parseScenarios(storyDetails, buffered);
            return storyDetails;
        }catch(Exception exception) {
            throw new StoryParserException(exception);
        }
    }
    
    /**
     * @param storyDetails
     */
    private void parseScenarios(StoryDetails storyDetails, BufferedReader reader) throws Exception {
        String input = null;
        while((input = reader.readLine())!=null) {
            if (scenarioMatcher.matches(input)) {
                parseScenario(storyDetails, reader, input);
            }
        }
    }

    private void parseScenario(StoryDetails storyDetails, BufferedReader reader, String input) throws Exception {
        String scenarioTitle = input.substring(scenarioMatcher.keywordLength());
        
        ContextDetails contextDetails = parseContext(reader);
        BasicDetails eventDetails = parse(whenMatcher, reader);   
        OutcomeDetails outcomeDetails = parseExpectations(reader);
        
        ScenarioDetails scenario = new ScenarioDetails(scenarioTitle, contextDetails, eventDetails, outcomeDetails);
        storyDetails.addScenario(scenario);
    }

    private ContextDetails parseContext(BufferedReader reader) throws Exception {
        ContextDetails contextDetails = new ContextDetails();
        
        contextDetails.addGiven(parse(givenMatcher, reader));
        BasicDetails given = null;
        while((given = parse(andMatcher, reader)) != null) {
            contextDetails.addGiven(given);
        }
        return contextDetails;
    }
    
    private OutcomeDetails parseExpectations(BufferedReader reader) throws Exception {
        OutcomeDetails outcomeDetails = new OutcomeDetails();
        outcomeDetails.addExpectation(parse(thenMatcher, reader));
        BasicDetails expectation = null;
        while ((expectation = parse(andMatcher, reader)) != null) {
            outcomeDetails.addExpectation(expectation);
        }    
        return outcomeDetails;
    }

    private BasicDetails parse(KeywordMatcher matcher, BufferedReader reader) throws Exception {
        String description = "";
        String name;
        
        reader.mark(80);
        String line = gobbleUpNewLines(reader);
               
        if (line == null || !matcher.matches(line)){
            reader.reset();
            return null;
        }
        
        line = line.substring(matcher.keywordLength());
        
        if (line.indexOf("(") > 0) {
            description = line.substring(line.indexOf("(") + 1, line.indexOf(")")); 
            name = line.substring(0, line.indexOf("(") -1);
        } else {
            name = line;
        }
        return new BasicDetails(name, description);
    }

    private StoryDetails parseStoryText(BufferedReader reader) throws Exception {
        String storyTitle = gobbleUpNewLines(reader).substring(7);       
        String role = gobbleUpNewLines(reader).substring(5);
        String feature = gobbleUpNewLines(reader).substring(10);
        String benefit = gobbleUpNewLines(reader).substring(8);
        return new StoryDetails(storyTitle,
                role, feature,
                benefit);
    }

    /**
     * @param reader
     * @return
     */
    private String gobbleUpNewLines(BufferedReader reader) throws IOException {
        String line = null;
        do {
            line = reader.readLine();
        }while("".equals(line));
        return line;
    }
}