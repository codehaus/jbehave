/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story;
import java.net.MalformedURLException;

import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.renderer.Renderer;

/**
 * A StoryPrinter loads a story and narrates it to a given renderer.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class StoryPrinter {

    private StoryLoader storyLoader;
    private Renderer renderer;
    
    public StoryPrinter(StoryLoader storyLoader,  Renderer renderer) {
        this.storyLoader = storyLoader;
        this.renderer = renderer;
    }

    public void print(String storyPath, String storyPackage) throws MalformedURLException {
        Story story = storyLoader.loadStory(storyPath, storyPackage);
        story.specify();
        story.narrateTo(renderer);
    }

    public void print(String storyClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        Story story = storyLoader.loadStory(storyClassName);
        story.specify();
        story.narrateTo(renderer);
    }

}
