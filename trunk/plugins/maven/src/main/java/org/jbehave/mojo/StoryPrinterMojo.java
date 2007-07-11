package org.jbehave.mojo;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryPrinter;

/**
 * Mojo to print stories 
 * 
 * @author Mauro Talevi
 * @goal print-story
 */
public class StoryPrinterMojo extends AbstractStoryMojo {
      
    public void execute() throws MojoExecutionException, MojoFailureException {
        String storyPackage = getStoryPackage();
        List storyPaths = getStoryPaths();
        try {
            for ( Iterator i = storyPaths.iterator(); i.hasNext(); ){
                String storyPath = (String)i.next();
                printStory(storyPackage, storyPath);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to print stories "+storyPaths+" with package "+storyPackage, e);
        }
    }

    private void printStory(String storyPackage, String storyPath) throws MalformedURLException {
        getLog().info("Printing story "+ storyPath+" using package "+storyPackage);
        StoryPrinter storyPrinter = new StoryPrinter(getStoryLoader(), getStoryRenderer());            
        storyPrinter.print(storyPath, storyPackage);
    }
  
}
