/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions;

import jbehave.extensions.jmock.listener.JMockListenerBehaviour;
import jbehave.extensions.junit.adapter.JUnitAdapterBehaviours;
import jbehave.framework.Aggregate;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviours implements Aggregate {

	public Class[] getBehaviourClasses() {
        return new Class[] {
            jbehave.AllBehaviours.class,
            JUnitAdapterBehaviours.class,
            JMockListenerBehaviour.class
        };
	}

}
