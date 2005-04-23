header {
package com.thoughtworks.jbehave.extensions.story.codegen.parser;
}

class StoryLexer extends Lexer;
options {k=2; testLiterals=false;}

TEXT
  options {testLiterals=true;}
  : ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9' | '_' | ':')*
  ;

NEWLINE
    : ( "\r\n"  // DOS/Windows
      | '\r'    // Macintosh
      | '\n'    // Unix
      )
      // increment the line count in the scanner
      { newline(); 
        $setType(Token.SKIP);};

DOT : '.';

WS : (
    ' '
    | '\t'
    | '\f'){ $setType(Token.SKIP); };


class AntlrStoryParser extends Parser;
options {buildAST=true;}

story       : titleDecl narrative (scenario)+ "endStory";
titleDecl   : "Story:" sentence;
narrative   : as_a i_want so_that;
as_a        : "As_a" sentence;
i_want      : "I_want" sentence;
so_that     : "So_that" sentence;
scenario    :  scenario_title context event outcome;
scenario_title : "Scenario:" sentence;
context     : "Given" sentence (and)*;
event       : "When" sentence;
outcome     : "Then" sentence (and)*;
and         : "and" sentence;
sentence    : (TEXT)+ DOT;

{
import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ScenarioDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.BasicDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.ContextDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.domain.OutcomeDetails;
}

class StoryTreeWalker extends TreeParser;
{   private StoryDetails story = null;
    private String storyTitle = null;
    private String role = null;
    private String feature = null;
    private String benefit = null;
    private String scenarioTitle = null;
    private BasicDetails given;
    private ContextDetails context;
    private BasicDetails event;
    private OutcomeDetails outcome;
    
 }

storyDetail:
    "Story:" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence[buf] 
    	{ storyTitle = buf.toString().trim();} storyDetail
    	
    | "As_a" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence [buf] 
    	{role = buf.toString().trim();}
    	storyDetail
    	
    | "I_want" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence [buf] 
    	{feature = buf.toString().trim();}
    	storyDetail
    	
    | "So_that" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence[buf] 
    	{benefit = buf.toString().trim();
    	story = new StoryDetails(storyTitle, role, feature, benefit);
    	}
    	storyDetail
    	
    | "Scenario:" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence[buf] 
    	{scenarioTitle = buf.toString().trim();
    	 context = null;
    	 outcome = null;
    	}
    	storyDetail
    	
    | "Given" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence[buf] 
    	{given = new BasicDetails(buf.toString().trim(), null);
	 context = new ContextDetails();
         context.addGiven(given);
         }
    	storyDetail
    	
    | "When" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence[buf] 
    	{event = new BasicDetails(buf.toString().trim(), null);}
    	storyDetail
    	
    | "Then" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence [buf] 
    	{outcome = new OutcomeDetails();
         outcome.addExpectation(new BasicDetails(buf.toString().trim(), null));
         story.addScenario(new ScenarioDetails(scenarioTitle, context, event, outcome));
        }
    	storyDetail
    		
    | "and" 
    	{StringBuffer buf = new StringBuffer();} 
    	sentence[buf] 
    	{BasicDetails details = new BasicDetails(buf.toString().trim(), null);
	 	if (outcome == null) {
	        	context.addGiven(details);
	        } else {
	                outcome.addExpectation(details);
                }
        }
    	storyDetail
    | "endStory" {};
    
   

sentence [StringBuffer buf]:
    txt:TEXT 
    	{buf.append(txt.getText());
                        buf.append(" ");
        } 
        sentence[buf]
    | dot:DOT {};

