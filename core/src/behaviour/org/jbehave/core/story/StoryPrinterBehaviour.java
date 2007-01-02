package org.jbehave.core.story;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jbehave.core.mock.UsingMatchers;

public class StoryPrinterBehaviour extends UsingMatchers {
    
    
    public void shouldSpecifyAndPrintStoryAsPlainText() throws Exception {
        
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);
        
        StoryPrinter printer = new StoryPrinter(printStream);        
        printer.print(SimpleStory.class.getName());
        String result = byteStream.toString();
        
        ensureThat(result, eq(SimpleStory.expectedDescription()));
    }

}
