/*
 * Created on 25-Jan-2004
 * 
 * (c) 2004 Dan North
 * 
 * See license.txt for licence details
 */
package jbehave.runner;

import java.io.OutputStreamWriter;

import jbehave.runner.listener.TextListener;
import jbehave.runner.listener.TraceListener;

/**
 * Basic command-line behaviour runner
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TextEvaluator {

    public static void main(String[] args) {
        Evaluator runner = new Evaluator();
        runner.registerListener(new TextListener(new OutputStreamWriter(System.out)));
        runner.registerListener(new TraceListener());
        for (int i = 0; i < args.length; i++) {
            try {
                runner.addBehaviourClass(Class.forName(args[i]));
            }
            catch (ClassNotFoundException e) {
                System.err.println("Unknown class: " + args[i]);
            }
        }
        runner.evaluateCriteria();
    }
}
