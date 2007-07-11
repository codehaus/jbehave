package org.jbehave.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryLoader;
import org.jbehave.core.story.StoryPrinter;
import org.jbehave.core.story.codegen.parser.StoryParser;
import org.jbehave.core.story.codegen.parser.TextStoryParser;
import org.jbehave.core.story.renderer.PlainTextRenderer;

/**
 * Mojo to print a story 
 * 
 * @author Mauro Talevi
 * @goal print-story
 */
public class StoryPrinterMojo  extends AbstractJBehaveMojo {
      
    /**
     * @parameter
     * @required true
     */
    private String storyPath;

    /**
     * @parameter
     * @required true
     */
    private String storyPackage;

    /** The story parser */
    private StoryParser storyParser = new TextStoryParser();

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().debug("Printing story "+ storyPath);
            StoryLoader loader = new StoryLoader(storyParser, new BehavioursClassLoader(getClasspathElements()));
            StoryPrinter storyPrinter = new StoryPrinter(loader, new PlainTextRenderer(System.out));            
            storyPrinter.print(storyPath, storyPackage);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to print story "+storyPath+" with package "+storyPackage, e);
        }
    }
  
}
