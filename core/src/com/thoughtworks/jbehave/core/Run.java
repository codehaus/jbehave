/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.io.PrintWriter;

import com.thoughtworks.jbehave.core.behaviour.BehaviourClass;
import com.thoughtworks.jbehave.core.behaviour.BehaviourMethodVerifier;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.core.invoker.InvokeMethodWithSetUpAndTearDown;
import com.thoughtworks.jbehave.core.listener.TextListener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {
    public static void main(String[] args) {
        try {
            Class classToVerify = Class.forName(args[0]);
            BehaviourClass visitableClass = new BehaviourClass(classToVerify);
            TextListener textListener = new TextListener(new PrintWriter(System.out));
            BehaviourMethodVerifier verifier = new BehaviourMethodVerifier(new InvokeMethodWithSetUpAndTearDown());
            verifier.addListener(textListener);
            visitableClass.accept(verifier);
            textListener.printReport();
            if (textListener.hasBehaviourFailures()) {
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Problem verifying behaviour class " + args[0]);
            e.printStackTrace();
            System.exit(1);
        } catch (JBehaveFrameworkError e) {
            System.err.println("Problem verifying behaviour class " + args[0]);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
