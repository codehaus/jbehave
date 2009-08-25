/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.listener.PlainTextScenarioListener;




/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class StoryRunner {
    
    private ClassLoader classLoader;
    private boolean succeeded = true;
    
    public StoryRunner(){
        this(Thread.currentThread().getContextClassLoader());
    }

    public StoryRunner(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void run(String storyClassName, PrintStream printStream) 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException {    
        Story story = loadStory(storyClassName, classLoader);
        story.specify();
        run(story, printStream);
    }
    
    public void run(Story story) {
        run(story, System.out);
    }

    public void run(Story story, PrintStream printStream) {
		PlainTextScenarioListener listener = new PlainTextScenarioListener(new OutputStreamWriter(printStream));
        story.addListener(listener);
        story.run();
        listener.printReport();
        succeeded = succeeded && !listener.hasBehaviourFailures();
    }

    private boolean succeeded() {
        return succeeded;
    }
    
    private Story loadStory(String className, ClassLoader classLoader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Story) classLoader.loadClass(className).newInstance();
    }
    
    public static void main(String[] args) {
        try {
            StoryRunner runner = new StoryRunner();
            for (int i = 0; i < args.length; i++) {
                runner.run(args[i], System.out);
            }
            System.exit(runner.succeeded() ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    

}
 