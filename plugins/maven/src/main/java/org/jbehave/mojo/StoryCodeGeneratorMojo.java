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
        String storyPackage = getStoryPackage();
        List storyPaths = getStoryPaths();
        try {
            for ( Iterator i = storyPaths.iterator(); i.hasNext(); ){
                String storyPath = (String)i.next();
                generateStoryCode(storyPackage, storyPath);
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to generate code for stories "+storyPaths+" with package "+storyPackage, e);
        }
    }

    //TODO  To make code generator configurable the story source directory and package need to 
    //      added to the StoryDetails parsed from the story representation
    private CodeGenerator createCodeGenerator() {
        return new VelocityCodeGenerator(getStorySourceDirectory().getPath(), getStoryPackage());
    }

    private void generateStoryCode(String storyPackage, String storyPath) throws MalformedURLException {
        getLog().info("Generate code for story "+ storyPath+" using package "+storyPackage);
        StoryLoader loader = getStoryLoader();
        StoryDetails storyDetails = loader.loadStoryDetails(storyPath, storyPackage);
        generator.generateStory(storyDetails);
    }
  
}
