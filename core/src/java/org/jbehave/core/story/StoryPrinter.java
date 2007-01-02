/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story;
import java.io.PrintStream;

import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.renderer.PlainTextRenderer;



/**
 * TODO Introduce StoryLoader
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class StoryPrinter {

    private ClassLoader classLoader;
    private final PrintStream stream;
    
    public StoryPrinter(){
        this(System.out);
    }
    
    public StoryPrinter(PrintStream stream) {
        this(Thread.currentThread().getContextClassLoader(), stream);
    }

    private StoryPrinter(ClassLoader classLoader, PrintStream stream) {
        this.classLoader = classLoader;
        this.stream = stream;
    }
    
    public void print(String storyClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        Story story = (Story) classLoader.loadClass(storyClassName).newInstance();
        story.specify();
        story.narrateTo(new PlainTextRenderer(stream));
    }

    public static void main(String[] args) {        
        try {
            StoryPrinter printer = new StoryPrinter();
            printer.print(args[0]);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
