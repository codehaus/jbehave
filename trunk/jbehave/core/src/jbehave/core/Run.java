/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core;

import java.io.PrintWriter;

import jbehave.core.responsibility.BehaviourClassVerifier;
import jbehave.core.responsibility.ExecutingResponsibilityVerifier;
import jbehave.listeners.TextListener;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {

    public static void main(String[] args) {
        try {
            Class behaviourClass = Class.forName(args[0]);
            TextListener textListener = new TextListener(new PrintWriter(System.out));
            new BehaviourClassVerifier(behaviourClass, new ExecutingResponsibilityVerifier())
                    .verifyBehaviourClass(textListener);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem verifying behaviour class " + args[0]);
            System.exit(1);
        }
    }
}
