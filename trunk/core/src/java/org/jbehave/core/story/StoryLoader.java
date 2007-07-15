package org.jbehave.core.story;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;

import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.story.codegen.parser.StoryParser;
import org.jbehave.core.story.domain.Story;

/**
 * StoryLoader parses story details from a resource in the classpath and build a Story via the StoryBuilder.
 * 
 * @author Mauro Talevi
 * @see StoryBuilder
 */
public class StoryLoader {

    private ClassLoader classLoader;
    private StoryParser storyParser;

    public StoryLoader(StoryParser storyParser, ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.storyParser = storyParser;
    }

    public StoryDetails loadStoryDetails(String storyPath) throws MalformedURLException {
        return storyParser.parseStory(getReader(storyPath, classLoader));
    }
    
    public Story loadStory(String storyPath) throws MalformedURLException {
        StoryDetails storyDetails = loadStoryDetails(storyPath);
        return new StoryBuilder(storyDetails, classLoader).story();
    }

    public Story loadStory(Class storyClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Story) classLoader.loadClass(storyClass.getName()).newInstance();        
    }
    
    protected Reader getReader(String resource, ClassLoader classLoader) {
        InputStream is = classLoader.getResourceAsStream(resource);
        if ( is == null ){
            throw new NoSuchElementException("Resource "+resource+" not found in ClassLoader "+classLoader.getClass());
        }
        return new InputStreamReader(is);
    }
}
