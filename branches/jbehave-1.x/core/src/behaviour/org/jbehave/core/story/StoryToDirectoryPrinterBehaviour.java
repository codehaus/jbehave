package org.jbehave.core.story;

import java.io.File;

import org.jbehave.core.Block;
import org.jbehave.core.mock.UsingMatchers;

public class StoryToDirectoryPrinterBehaviour extends UsingMatchers {

    public void shouldThrowIllegalArgumentExceptionIfDirectoryDoesNotExist() throws Exception {
        Exception exception = runAndCatch(IllegalArgumentException.class, new Block() {
            public void run() throws Exception {
                new StoryToDirectoryPrinter(null, new File("FileWhichDoesNotExist"));
            }
        });
        
        ensureThat(exception, isNotNull());
    }
    

    public void shouldThrowIllegalArgumentExceptionIfFileIsNotDirectory() throws Exception {
        Exception exception = runAndCatch(IllegalArgumentException.class, new Block() {
            public void run() throws Exception {
                new StoryToDirectoryPrinter(null, new File("FileWhichExists"));
            }
        });
        
        ensureThat(exception, isNotNull());
    }
    
    public void shouldUseStoryPrinterToOutputStoriesByNameToDirectory() {
        // No way of describing this behaviour yet without creating a number of files,
        // which I don't want to do. If you change the StoryToDirectoryPrinter,
        // please describe this behaviour or at least make sure that the
        // print-example-stories task works.
    }
}
