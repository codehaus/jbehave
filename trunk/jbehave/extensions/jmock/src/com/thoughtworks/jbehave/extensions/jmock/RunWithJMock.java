/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import java.io.OutputStreamWriter;

import com.thoughtworks.jbehave.core.listeners.MethodListeners;
import com.thoughtworks.jbehave.core.listeners.TextListener;
import com.thoughtworks.jbehave.core.verify.BehaviourClassVerifier;
import com.thoughtworks.jbehave.core.verify.ExecutingMethodVerifier;
import com.thoughtworks.jbehave.extensions.jmock.listener.JMockListener;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class RunWithJMock {
	public static void main(String [] args) throws Exception {
        TextListener textListener = new TextListener(new OutputStreamWriter(System.out));
		MethodListeners listener = new MethodListeners();
		listener.add(new JMockListener());
        listener.add(textListener);
		new BehaviourClassVerifier(Class.forName(args[0]), new ExecutingMethodVerifier()).verifyBehaviourClass(textListener, listener);
	}
}
