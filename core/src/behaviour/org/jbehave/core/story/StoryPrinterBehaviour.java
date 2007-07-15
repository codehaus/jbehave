package org.jbehave.core.story;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jbehave.core.mock.UsingMatchers;
import org.jbehave.core.story.codegen.parser.TextStoryParser;
import org.jbehave.core.story.renderer.PlainTextRenderer;

public class StoryPrinterBehaviour extends UsingMatchers {
        
    public void shouldSpecifyAndPrintStoryAsPlainText() throws Exception {
        
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);
        
        StoryPrinter printer = new StoryPrinter(
                new StoryLoader(new TextStoryParser(), Thread.currentThread().getContextClassLoader()),
                new PlainTextRenderer(printStream));
        printer.print(SimpleStory.class);
        String result = byteStream.toString();
        
        ensureThat(result, eq(SimpleStory.expectedDescription()));
    }

}
