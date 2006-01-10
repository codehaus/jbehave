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
import jbehave.core.story.invoker.VisitingScenarioInvoker;
import jbehave.core.story.listener.PlainTextScenarioListener;
import jbehave.core.story.verifier.VisitingScenarioVerifier;
import jbehave.core.story.visitor.Visitor;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {

    public static void main(String[] args) {
        try {
            story(args[0], System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void story(String clazz, PrintStream printStream) 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    	
        story((Story) Class.forName(clazz).newInstance(), printStream);
    }
    
    public static void story(Story story) {
        story(story, System.out);
    }

    public static void story(Story story, PrintStream printStream) {
        World world = new HashMapWorld();
        Visitor scenarioInvoker = new VisitingScenarioInvoker(story.getClass().getName(), world);
        Visitor scenarioVerifier = new VisitingScenarioVerifier(story.getClass().getName(), world);
		PlainTextScenarioListener listener = new PlainTextScenarioListener(new OutputStreamWriter(printStream));
        story.addListener(listener);
        story.run(scenarioInvoker, scenarioVerifier);
        listener.printReport();
    }
}
 