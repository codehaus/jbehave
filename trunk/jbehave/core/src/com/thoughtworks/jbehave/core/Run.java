/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.io.PrintWriter;

import com.thoughtworks.jbehave.core.listeners.TextListener;
import com.thoughtworks.jbehave.core.verify.BehaviourVerifier;
import com.thoughtworks.jbehave.util.InvokeMethodWithSetUpAndTearDown;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {
    public static void main(String[] args) {
        try {
            Class classToVerify = Class.forName(args[0]);
            TextListener textListener = new TextListener(new PrintWriter(System.out));
            BehaviourVerifier methodVerifier = new BehaviourVerifier(textListener);
            BehaviourClass behaviourClass = new BehaviourClass(classToVerify, methodVerifier, new InvokeMethodWithSetUpAndTearDown());
            new BehaviourVerifier(textListener).verify(behaviourClass);
            textListener.printReport();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem verifying behaviour class " + args[0]);
            System.exit(1);
        }
    }
}
