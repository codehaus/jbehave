/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions;

import jbehave.extensions.jmock.listener.JMockListenerSpec;
import jbehave.extensions.junit.adapter.JUnitAdapterBehaviour;
import jbehave.framework.AggregateSpec;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviour implements AggregateSpec {

	public Class[] getSpecs() {
        return new Class[] {
            jbehave.AllSpecs.class,
            JUnitAdapterBehaviour.class,
            JMockListenerSpec.class
        };
	}

}
