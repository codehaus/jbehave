/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework;

import java.io.PrintWriter;

import jbehave.listeners.TextListener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {

    public static void main(String[] args) {
        try {
            Class behaviourClass = Class.forName(args[0]);
            new BehaviourClassVerifier(behaviourClass)
                    .verifyBehaviourClass(new TextListener(new PrintWriter(
                            System.out)));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem verifying behaviour class " + args[0]);
        }
    }
}
