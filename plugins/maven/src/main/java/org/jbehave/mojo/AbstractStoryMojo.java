package org.jbehave.mojo;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.story.StoryLoader;
import org.jbehave.core.story.codegen.parser.StoryParser;
import org.jbehave.core.story.renderer.Renderer;
import org.jbehave.core.util.PathFinder;

/**
 * Abstract mojo for story-related goals
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractStoryMojo extends AbstractJBehaveMojo {

    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     * @readonly
     */
    private String sourceDirectory;

    /**
     * @parameter expression="${project.build.testSourceDirectory}"
     * @required
     * @readonly
     */
    private String testSourceDirectory;

    /**
     * Path specifying a single story
     * 
     * @parameter
     */
    private String storyPath;

    /**
     * Directory containing multiple stories, relative to the sourceDirectory
     * 
     * @parameter
     */
    private String storyDirectory;

    /**
     * Story include filters, relative to the storyDirectory
     * 
     * @parameter
     */
    private List storyIncludes;

    /**
     * Story exclude filters, relative to the storyDirectory
     * 
     * @parameter
     */
    private List storyExcludes;

    /**
     * @parameter
     * @required true
     */
    private String storyPackage;

    /**
     * @parameter default-value="org.jbehave.core.story.codegen.parser.TextStoryParser"
     */
    private String storyParserClassName;

    /**
     * @parameter default-value="org.jbehave.core.story.renderer.ConsolePlainTextRenderer"
     */
    private String storyRendererClassName;

    /**
     * PathFinder used to list story paths
     */
    private PathFinder pathFinder = new PathFinder();

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
        getLog().debug("Using story parser " + storyParser.getClass().getName());
        return storyParser;
    }

    protected Renderer getStoryRenderer() {
        Renderer storyRenderer = (Renderer) createInstance(storyRendererClassName);
        getLog().debug("Using story renderer " + storyRenderer.getClass().getName());
        return storyRenderer;
    }

    protected StoryLoader getStoryLoader() throws MalformedURLException {
        return new StoryLoader(getStoryParser(), createBehavioursClassLoader());
    }

    protected String getStoryPackage() {
        return storyPackage;
    }

    protected List getStoryPaths() {
        // a single story path is specified
        if (storyPath != null) {
            List storyPaths = new ArrayList();
            storyPaths.add(storyPath);
            return storyPaths;
        }
        return listStoryPaths();
    }

    private List listStoryPaths() {
        List storyPaths = pathFinder.listPaths(getStorySourceDirectory().getPath(), storyDirectory, storyIncludes,
                storyExcludes);
        getLog().debug("Listed story paths " + storyPaths);
        return storyPaths;
    }

    private String getRootSourceDirectory() {
        if (isTestScope()) {
            return testSourceDirectory;
        }
        return sourceDirectory;
    }

    protected File getStorySourceDirectory() {
        return new File(getRootSourceDirectory(), storyDirectory);
    }

    public static class InvalidClassNameException extends RuntimeException {
        public InvalidClassNameException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
