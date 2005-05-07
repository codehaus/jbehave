package com.thoughtworks.jbehave.extensions.story.codegen;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.lexer.Lexer;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.lexer.LexerException;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.Start;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.parser.Parser;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.parser.ParserException;

public class TextStoryParser {
	public StoryDetails parse(Reader in) {
		try {
			Lexer lexer = new Lexer(new PushbackReader(in, 1024));
			Parser parser = new Parser(lexer);
			Start root = parser.parse();
			StoryDetailsBuilder builder = new StoryDetailsBuilder();
			root.apply(builder);
			return builder.getStoryDetails();
		} catch (IOException e) {
			throw new RuntimeException("bastard", e);
		} catch (LexerException e) {
			throw new RuntimeException("bastard", e);
		} catch (ParserException e) {
			throw new RuntimeException("bastard", e);
		}
		
	}

}
