package jbehave.core.story.codegen.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import jbehave.core.story.codegen.domain.StoryDetails;
import jbehave.core.story.codegen.sablecc.lexer.Lexer;
import jbehave.core.story.codegen.sablecc.lexer.LexerException;
import jbehave.core.story.codegen.sablecc.node.Start;
import jbehave.core.story.codegen.sablecc.parser.Parser;
import jbehave.core.story.codegen.sablecc.parser.ParserException;


public class TextStoryParser implements StoryParser {
	public StoryDetails parseStory(Reader in) {
		try {
			Lexer lexer = new Lexer(new PushbackReader(in, 1024));
			Parser parser = new Parser(lexer);
			Start root = parser.parse();
			StoryDetailsBuilder builder = new StoryDetailsBuilder();
			root.apply(builder);
			return builder.getStoryDetails();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		} catch (LexerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (ParserException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

}
