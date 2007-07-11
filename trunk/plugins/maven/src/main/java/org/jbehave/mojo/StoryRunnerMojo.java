package org.jbehave.mojo;


import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryLoader;
import org.jbehave.core.story.StoryRunner;
import org.jbehave.core.story.domain.Story;

/**
 * Mojo to run a story 
 * 
 * @author Mauro Talevi
 * @goal run-story
 */
public class StoryRunnerMojo extends AbstractStoryMojo {
      
    /** The story runner */
    private StoryRunner storyRunner = new StoryRunner();
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().debug("Running story "+ storyPath);
            StoryLoader loader = getStoryLoader();
            Story story = loader.loadStory(storyPath, storyPackage);
			story.specify();
            storyRunner.run(story);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run story "+storyPath+" with package "+storyPackage, e);
        }
    }

  
}
