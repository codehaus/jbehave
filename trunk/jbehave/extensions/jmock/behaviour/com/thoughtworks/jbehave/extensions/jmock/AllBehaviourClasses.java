/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import com.thoughtworks.jbehave.core.BehaviourClassContainer;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AllBehaviourClasses implements BehaviourClassContainer {
    public Class[] getBehaviourClasses() {
        return new Class[] {
                UsingJMockBehaviour.class
        };
	}
}
