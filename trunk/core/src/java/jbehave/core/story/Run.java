/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import jbehave.core.story.domain.HashMapWorld;
import jbehave.core.story.domain.Story;
import jbehave.core.story.domain.World;
import jbehave.core.story.listener.PlainTextScenarioListener;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class Run {
    
    private ClassLoader classLoader;
    
    public Run(){
        this(Thread.currentThread().getContextClassLoader());
    }

    public Run(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void story(String storyClassName, PrintStream printStream) 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException {    
        story(loadStory(storyClassName, classLoader), printStream);
    }
    
    public void story(Story story) {
        story(story, System.out);
    }

    public void story(Story story, PrintStream printStream) {
        World world = new HashMapWorld();
		PlainTextScenarioListener listener = new PlainTextScenarioListener(new OutputStreamWriter(printStream));
        story.addListener(listener);
        story.run(world);
        listener.printReport();
    }

    private Story loadStory(String className, ClassLoader classLoader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Story) classLoader.loadClass(className).newInstance();
    }
    
    public static void main(String[] args) {
        try {
            Run run = new Run();
            run.story(args[0], System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
 