package org.jbehave.mojo;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryLoader;
import org.jbehave.core.story.codegen.CodeGenerator;
import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.story.codegen.velocity.VelocityCodeGenerator;

/**
 * Mojo to generatore code for stories
 * 
 * @author Mauro Talevi
 * @goal generate-story-code
 */
public class StoryCodeGeneratorMojo extends AbstractStoryMojo {
      
    /** The code generator */
    private CodeGenerator generator;
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        generator = createCodeGenerator();
        List storyPaths = getStoryPaths();
        try {
            for ( Iterator i = storyPaths.iterator(); i.hasNext(); ){
                String storyPath = (String)i.next();
                generateStoryCode(storyPath);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to generate code for stories "+storyPaths, e);
        }
    }

    //TODO  make code generator configurable 
    private CodeGenerator createCodeGenerator() {
        return new VelocityCodeGenerator(getStorySourceDirectory().getPath());
    }

    private void generateStoryCode(String storyPath) throws MalformedURLException {
        getLog().info("Generate code for story "+ storyPath);
        StoryLoader loader = getStoryLoader();
        StoryDetails storyDetails = loader.loadStoryDetails(storyPath);
        generator.generateStory(storyDetails);
    }
  
}
