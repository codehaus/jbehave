/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example;
import java.io.OutputStreamWriter;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Story;
import com.thoughtworks.jbehave.extensions.story.listener.TextScenarioListener;
import com.thoughtworks.jbehave.extensions.story.renderers.PlainTextRenderer;
import com.thoughtworks.jbehave.extensions.story.runner.StoryRunner;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

import example.stories.UserWithdrawsCash;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {

    public static void main(String[] args) {
        
        try {
            Environment environment = new Environment() {
                public Object get(String key, Object defaultValue) {
                    return defaultValue;
                }

                public void put(String key, Object value) {
                }
            };
            Visitor visitor = new StoryRunner(environment, new TextScenarioListener(new OutputStreamWriter(System.out))); // new PlainTextRenderer(System.out);
            visitor = new PlainTextRenderer(System.out);
            Story story = new UserWithdrawsCash();
            story.accept(visitor);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
