package org.jbehave.mojo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.story.StoryBuilder;
import org.jbehave.core.story.StoryRunner;
import org.jbehave.core.story.codegen.domain.StoryDetails;
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
            Story story = buildStory(storyPath, storyPackage);            
            storyRunner.run(story);
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to run story "+storyPath+" with package "+storyPackage, e);
        }
    }

    private Story buildStory(String storyPath, String storyPackage) throws MalformedURLException {
        StoryDetails storyDetails = storyParser.parseStory(getReader(storyPath));
        return new StoryBuilder(storyDetails, storyPackage).story();
    }

    private Reader getReader(String resource) throws MalformedURLException {
        BehavioursClassLoader cl = new BehavioursClassLoader(classpathElements);
        return getReader(resource, cl);
    }

    protected Reader getReader(String resource, ClassLoader classLoader) {
        InputStream is = classLoader.getResourceAsStream(resource);
        if ( is == null ){
            throw new NoSuchElementException("Resource "+resource+" not found in ClassLoader "+classLoader.getClass());
        }
        return new InputStreamReader(is);
    }
}
