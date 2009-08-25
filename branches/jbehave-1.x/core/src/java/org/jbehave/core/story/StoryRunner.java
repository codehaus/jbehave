/*
 * Created on 25-Aug-2004 (c) 2003-2004 ThoughtWorks Ltd See license.txt for
 * license details
 */
package org.jbehave.core.story;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.jbehave.core.exception.PendingException;
import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.result.Result;
import org.jbehave.core.story.codegen.parser.TextStoryParser;
import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.listener.PlainTextScenarioListener;
import org.jbehave.core.util.CamelCaseConverter;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class StoryRunner {

    private StoryLoader storyLoader;
    private boolean succeeded = true;

    /**
     * Creates a story runner with default story loader
     */
    public StoryRunner() {
        this(new StoryLoader(new TextStoryParser()));
    }
    
    /**
     * Creates a story runner with a given story loader
     * 
     * @param storyLoader the StoryLoader
     */
    public StoryRunner(StoryLoader storyLoader) {
        this.storyLoader = storyLoader;
    }

    public void run(String storyClassName) {
        Story story = storyLoader.loadStoryClass(storyClassName);
        story.specify();
        run(story);
    }

    public void run(Story story) {
        run(story, new PlainTextScenarioListener());
    }

    /**
     * @deprecated Use #run(String,BehaviourListener) instead
     */
    public void run(String storyClassName, OutputStream outputStream) {
        Story story = storyLoader.loadStoryClass(storyClassName);
        story.specify();
        run(story, outputStream);
    }

    /**
     * @deprecated Use #run(Story,BehaviourListener) instead
     */
    public void run(Story story, OutputStream outputStream) {
        run(story, new PlainTextScenarioListener(new OutputStreamWriter(outputStream)));
    }

    public void run(String storyClassName, BehaviourListener listener) {
        Story story = storyLoader.loadStoryClass(storyClassName);
        story.specify();
        run(story, listener);
    }
    
    public void run(Story story, BehaviourListener listener) {
        story.addListener(listener);
        try {
            story.run();
        } catch (PendingException e) {
            listener.gotResult(new Result("Pending", new CamelCaseConverter(story.getClass()).toPhrase(), e));
        }
        listener.printReport();
        succeeded = succeeded && !listener.hasBehaviourFailures();
    }
    
    private boolean succeeded() {
        return succeeded;
    }

    public static void main(String[] args) {
        StoryRunner runner = new StoryRunner();
        BehaviourListener listener = new PlainTextScenarioListener();
        for (int i = 0; i < args.length; i++) {
            runner.run(args[i]);
        }
        System.exit(runner.succeeded() ? 0 : 1);
    }

}
