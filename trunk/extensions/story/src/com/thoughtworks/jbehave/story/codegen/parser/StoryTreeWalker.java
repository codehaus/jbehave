// $ANTLR 2.7.3: "story.g" -> "StoryTreeWalker.java"$

package com.thoughtworks.jbehave.story.codegen.parser;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

import com.thoughtworks.jbehave.story.codegen.domain.BasicDetails;
import com.thoughtworks.jbehave.story.codegen.domain.ContextDetails;
import com.thoughtworks.jbehave.story.codegen.domain.OutcomeDetails;
import com.thoughtworks.jbehave.story.codegen.domain.ScenarioDetails;
import com.thoughtworks.jbehave.story.codegen.domain.StoryDetails;


public class StoryTreeWalker extends antlr.TreeParser       implements StoryLexerTokenTypes
 {
   private StoryDetails story = null;
    private String storyTitle = null;
    private String role = null;
    private String feature = null;
    private String benefit = null;
    private String scenarioTitle = null;
    private BasicDetails given;
    private ContextDetails context;
    private BasicDetails event;
    private OutcomeDetails outcome;
    
 public StoryTreeWalker() {
	tokenNames = _tokenNames;
}

	public final StoryDetails storyDetail(AST _t) throws RecognitionException {
		
		AST storyDetail_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case 9:
			{
				AST tmp1_AST_in = (AST)_t;
				match(_t,9);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				storyTitle = buf.toString().trim();
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_As_a:
			{
				AST tmp2_AST_in = (AST)_t;
				match(_t,LITERAL_As_a);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				role = buf.toString().trim();
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_I_want:
			{
				AST tmp3_AST_in = (AST)_t;
				match(_t,LITERAL_I_want);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				feature = buf.toString().trim();
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_So_that:
			{
				AST tmp4_AST_in = (AST)_t;
				match(_t,LITERAL_So_that);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				benefit = buf.toString().trim();
					story = new StoryDetails(storyTitle, role, feature, benefit);
					
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case 13:
			{
				AST tmp5_AST_in = (AST)_t;
				match(_t,13);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				scenarioTitle = buf.toString().trim();
					 context = null;
					 outcome = null;
					
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_Given:
			{
				AST tmp6_AST_in = (AST)_t;
				match(_t,LITERAL_Given);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				given = new BasicDetails(buf.toString().trim(), null);
					 context = new ContextDetails();
				context.addGiven(given);
				
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_When:
			{
				AST tmp7_AST_in = (AST)_t;
				match(_t,LITERAL_When);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				event = new BasicDetails(buf.toString().trim(), null);
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_Then:
			{
				AST tmp8_AST_in = (AST)_t;
				match(_t,LITERAL_Then);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				outcome = new OutcomeDetails();
				outcome.addExpectation(new BasicDetails(buf.toString().trim(), null));
				story.addScenario(new ScenarioDetails(scenarioTitle, context, event, outcome));
				
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_and:
			{
				AST tmp9_AST_in = (AST)_t;
				match(_t,LITERAL_and);
				_t = _t.getNextSibling();
				StringBuffer buf = new StringBuffer();
				sentence(_t,buf);
				_t = _retTree;
				BasicDetails details = new BasicDetails(buf.toString().trim(), null);
					 	if (outcome == null) {
					        	context.addGiven(details);
					        } else {
					                outcome.addExpectation(details);
				}
				
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case NEWLINE:
			{
				AST tmp10_AST_in = (AST)_t;
				match(_t,NEWLINE);
				_t = _t.getNextSibling();
				storyDetail(_t);
				_t = _retTree;
				break;
			}
			case LITERAL_endStory:
			{
				AST tmp11_AST_in = (AST)_t;
				match(_t,LITERAL_endStory);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
        return story;
	}
	
	public final void sentence(AST _t,
		StringBuffer buf
	) throws RecognitionException {
		
		AST sentence_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST txt = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case TEXT:
			{
				txt = (AST)_t;
				match(_t,TEXT);
				_t = _t.getNextSibling();
				buf.append(txt.getText());
				buf.append(" ");
				
				sentence(_t,buf);
				_t = _retTree;
				break;
			}
			case NEWLINE:
			{
				AST tmp12_AST_in = (AST)_t;
				match(_t,NEWLINE);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"TEXT",
		"NEWLINE",
		"DOT",
		"WS",
		"\"endStory\"",
		"\"Story:\"",
		"\"As_a\"",
		"\"I_want\"",
		"\"So_that\"",
		"\"Scenario:\"",
		"\"Given\"",
		"\"When\"",
		"\"Then\"",
		"\"and\""
	};
	
	}
	
