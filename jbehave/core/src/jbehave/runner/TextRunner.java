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

/**
 * Basic command-line behaviour runner
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TextRunner {

    public static void main(String[] args) {
        BehaviourRunner runner = new BehaviourRunner();
        runner.registerListener(new TextListener(new OutputStreamWriter(System.out)));
        for (int i = 0; i < args.length; i++) {
            try {
                runner.addBehaviourClass(Class.forName(args[i]));
            }
            catch (ClassNotFoundException e) {
                System.err.println("Unknown class: " + args[i]);
            }
        }
        runner.runBehaviours();
    }
}
