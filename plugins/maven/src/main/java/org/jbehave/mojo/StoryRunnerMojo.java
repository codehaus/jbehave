package org.jbehave.mojo;


import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryLoader;
import org.jbehave.core.story.StoryRunner;
import org.jbehave.core.story.domain.Story;

/**
 * Mojo to run stories 
 * 
 * @author Mauro Talevi
 * @goal run-story
 */
public class StoryRunnerMojo extends AbstractStoryMojo {
      
    /** The story runner */
    private StoryRunner storyRunner = new StoryRunner();
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        List storyPaths = getStoryPaths();
        try {
            for ( Iterator i = storyPaths.iterator(); i.hasNext(); ){
                String storyPath = (String)i.next();
                runStory(storyPath);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run stories "+storyPaths, e);
        }
    }

    private void runStory(String storyPath) throws MalformedURLException {
        getLog().info("Running story "+ storyPath);
        StoryLoader loader = getStoryLoader();
        Story story = loader.loadStory(storyPath);
        story.specify();
        storyRunner.run(story);
    }

}
