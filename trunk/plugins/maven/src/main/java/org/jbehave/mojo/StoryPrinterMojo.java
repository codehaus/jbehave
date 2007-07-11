package org.jbehave.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryPrinter;

/**
 * Mojo to print a story 
 * 
 * @author Mauro Talevi
 * @goal print-story
 */
public class StoryPrinterMojo extends AbstractStoryMojo {
      
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().debug("Printing story "+ storyPath);
            StoryPrinter storyPrinter = new StoryPrinter(getStoryLoader(), getStoryRenderer());            
            storyPrinter.print(storyPath, storyPackage);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to print story "+storyPath+" with package "+storyPackage, e);
        }
    }
  
}
