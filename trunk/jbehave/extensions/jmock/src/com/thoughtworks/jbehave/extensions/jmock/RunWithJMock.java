/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import java.io.OutputStreamWriter;

import com.thoughtworks.jbehave.core.BehaviourClass;
import com.thoughtworks.jbehave.core.BehaviourVerifier;
import com.thoughtworks.jbehave.core.invokers.InvokeMethodWithSetUpAndTearDown;
import com.thoughtworks.jbehave.core.listeners.TextListener;
import com.thoughtworks.jbehave.extensions.jmock.listener.JMockListener;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class RunWithJMock {
	public static void main(String [] args) throws Exception {
        TextListener textListener = new TextListener(new OutputStreamWriter(System.out));
        BehaviourVerifier verifier = new BehaviourVerifier();
		verifier.registerListener(new JMockListener());
        verifier.registerListener(textListener);
        verifier.verify(new BehaviourClass(Class.forName(args[0]), verifier, new InvokeMethodWithSetUpAndTearDown()));
	}
}
