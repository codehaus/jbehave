/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story;
import jbehave.core.story.domain.Story;
import jbehave.core.story.renderer.PlainTextRenderer;



/**
 * @todo Introduce StoryLoader
 * @todo Rename PrintStory to StoryPrinter
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public class PrintStory {

    private ClassLoader classLoader;
    
    public PrintStory(){
        this(Thread.currentThread().getContextClassLoader());
    }

    public PrintStory(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public void print(String storyClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        Story story = (Story) classLoader.loadClass(storyClassName).newInstance();
        story.accept(new PlainTextRenderer(System.out));
    }

    public static void main(String[] args) {        
        try {
            PrintStory printer = new PrintStory();
            printer.print(args[0]);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
