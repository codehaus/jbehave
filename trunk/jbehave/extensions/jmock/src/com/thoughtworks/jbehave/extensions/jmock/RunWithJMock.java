/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import java.io.OutputStreamWriter;

import com.thoughtworks.jbehave.core.listeners.ResponsibilityListeners;
import com.thoughtworks.jbehave.core.listeners.TextListener;
import com.thoughtworks.jbehave.core.responsibility.BehaviourClassVerifier;
import com.thoughtworks.jbehave.core.responsibility.ExecutingResponsibilityVerifier;
import com.thoughtworks.jbehave.extensions.jmock.listener.JMockListener;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class RunWithJMock {
	public static void main(String [] args) throws Exception {
        TextListener textListener = new TextListener(new OutputStreamWriter(System.out));
		ResponsibilityListeners listener = new ResponsibilityListeners();
		listener.add(new JMockListener());
        listener.add(textListener);
		new BehaviourClassVerifier(Class.forName(args[0]), new ExecutingResponsibilityVerifier()).verifyBehaviourClass(textListener, listener);
	}
}
