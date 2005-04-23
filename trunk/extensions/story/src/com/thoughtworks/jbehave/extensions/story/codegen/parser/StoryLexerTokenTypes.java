package com.thoughtworks.jbehave.extensions.story.codegen.parser;

// $ANTLR 2.7.3: "story.g" -> "com.thoughtworks.jbehave.extensions.story.codegen.parser.StoryLexer.java"$

public interface StoryLexerTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int TEXT = 4;
	int NEWLINE = 5;
	int DOT = 6;
	int WS = 7;
	// "Story:" = 8
	int LITERAL_As_a = 9;
	int LITERAL_I_want = 10;
	int LITERAL_So_that = 11;
	// "Scenario:" = 12
	int LITERAL_Given = 13;
	int LITERAL_When = 14;
	int LITERAL_Then = 15;
	int LITERAL_and = 16;
}
