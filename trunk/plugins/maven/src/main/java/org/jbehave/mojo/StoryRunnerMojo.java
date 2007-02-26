package org.jbehave.mojo;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryLoader;
import org.jbehave.core.story.StoryRunner;
import org.jbehave.core.story.codegen.parser.StoryParser;
import org.jbehave.core.story.codegen.parser.TextStoryParser;
import org.jbehave.core.story.domain.Story;

/**
 * Mojo to run a story 
 * 
 * @author Mauro Talevi
 * @goal run-story
 */
public class StoryRunnerMojo  extends AbstractMojo {
    
    /**
     * Compile classpath.
     *
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    List classpathElements;
    
    /**
     * @parameter
     * @required true
     */
    String storyPath;

    /**
     * @parameter
     * @required true
     */
    String storyPackage;

    /** The story parser */
    private StoryParser storyParser = new TextStoryParser();

    /** The story runner */
    private StoryRunner storyRunner = new StoryRunner();
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().debug("Running story "+ storyPath);
            StoryLoader loader = new StoryLoader(storyParser, new BehavioursClassLoader(classpathElements));
            Story story = loader.loadStory(storyPath, storyPackage);            
            storyRunner.run(story);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run story "+storyPath+" with package "+storyPackage, e);
        }
    }

  
}
