/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example;
import java.util.Iterator;

import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.renderers.PlainTextRenderer;

import example.stories.UserWithdrawsCash;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {

    public static void main(String[] args) {
        
        try {
            Visitor visitor = new PlainTextRenderer(System.out);
            
            Story story = new UserWithdrawsCash();
            
            for (Iterator i = story.scenarios().iterator(); i.hasNext();) {
                Scenario scenario = (Scenario) i.next();
            }
            story.accept(visitor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
