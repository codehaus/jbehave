/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.jmock;

import jbehave.listeners.CompositeListener;
import jbehave.listeners.TextListener;
import jbehave.extensions.jmock.listener.JMockListener;
import jbehave.framework.BehaviourClassVerifier;

import java.io.OutputStreamWriter;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class JMockBehaviourClassVerifier {
	public static void main(String [] args) throws Exception {
		CompositeListener listener = new CompositeListener();
		listener.add(new JMockListener());
		listener.add(new TextListener(new OutputStreamWriter(System.out)));
		new BehaviourClassVerifier(Class.forName(args[0])).verifyBehaviourClass(listener);
	}
}
