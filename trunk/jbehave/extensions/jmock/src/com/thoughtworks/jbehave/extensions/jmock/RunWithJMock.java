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
import com.thoughtworks.jbehave.core.listeners.BehaviourListeners;
import com.thoughtworks.jbehave.core.listeners.TextListener;
import com.thoughtworks.jbehave.core.verify.BehaviourVerifier;
import com.thoughtworks.jbehave.extensions.jmock.listener.JMockListener;
import com.thoughtworks.jbehave.util.InvokeMethodWithSetUpAndTearDown;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class RunWithJMock {
	public static void main(String [] args) throws Exception {
        TextListener textListener = new TextListener(new OutputStreamWriter(System.out));
		BehaviourListeners listener = new BehaviourListeners();
		listener.add(new JMockListener());
        listener.add(textListener);
        BehaviourVerifier verifier = new BehaviourVerifier(listener);
        verifier.verify(new BehaviourClass(Class.forName(args[0]), verifier, new InvokeMethodWithSetUpAndTearDown()));
	}
}
