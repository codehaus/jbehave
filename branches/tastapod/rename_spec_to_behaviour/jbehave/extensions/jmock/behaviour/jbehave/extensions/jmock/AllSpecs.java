/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.jmock;

import jbehave.framework.SpecContainer;
import jbehave.extensions.jmock.listener.JMockListenerSpec;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AllSpecs implements SpecContainer {
    public Class[] getSpecs() {
        return new Class[] {JMockerSpec.class,
							JMockListenerSpec.class};
	}
}
