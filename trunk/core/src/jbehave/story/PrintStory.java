/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.story;
import jbehave.story.domain.Story;
import jbehave.story.renderer.PlainTextRenderer;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PrintStory {

    public static void main(String[] args) {
        
        try {
            Story story = (Story) Class.forName(args[0]).newInstance();
            story.accept(new PlainTextRenderer(System.out));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
