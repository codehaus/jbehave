/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave;

import jbehave.framework.BehaviourClassContainer;
import jbehave.extensions.jmock.JMockerBehaviour;
import jbehave.extensions.jmock.listener.JMockListenerBehaviour;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AllBehaviourClasses implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {JMockerBehaviour.class,
							JMockListenerBehaviour.class};
	}
}
