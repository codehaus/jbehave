package org.jbehave.mojo;

import java.net.MalformedURLException;

import org.jbehave.core.story.StoryLoader;
import org.jbehave.core.story.codegen.parser.StoryParser;
import org.jbehave.core.story.renderer.Renderer;

/**
 * Abstract mojo for story-related goals 
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractStoryMojo  extends AbstractJBehaveMojo {
      
    /**
     * @parameter
     * @required true
     */
    protected String storyPath;

    /**
     * @parameter
     * @required true
     */
    protected String storyPackage;

    /**
     * @parameter default-value="org.jbehave.core.story.codegen.parser.TextStoryParser" 
     */
    private String storyParserClassName;
        
    /**
     * @parameter default-value="org.jbehave.core.story.renderer.ConsolePlainTextRenderer" 
     */
    private String storyRendererClassName;
   
    private Object createInstance(String className) {
        try {
            BehavioursClassLoader cl = createBehavioursClassLoader();
            return cl.loadClass(className).newInstance();
        } catch (Exception e) {
            throw new InvalidClassNameException(className, e);
        }
    }
        
    protected StoryParser getStoryParser() {
        StoryParser storyParser = (StoryParser) createInstance(storyParserClassName);       
        getLog().debug("Using story parser "+storyParser.getClass().getName());
        return storyParser;
    }

    protected Renderer getStoryRenderer() {
        Renderer storyRenderer = (Renderer) createInstance(storyRendererClassName);       
        getLog().debug("Using story renderer "+storyRenderer.getClass().getName());
        return storyRenderer;
    }

    protected StoryLoader getStoryLoader() throws MalformedURLException {
        return new StoryLoader(getStoryParser(), createBehavioursClassLoader());
    }

    public static class InvalidClassNameException extends RuntimeException {
        public InvalidClassNameException(String message, Throwable cause) {
            super(message, cause);
        }        
    }

}
