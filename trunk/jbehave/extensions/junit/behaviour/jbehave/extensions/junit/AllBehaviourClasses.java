/*
 * Created on 11-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.junit;

import jbehave.framework.BehaviourClassContainer;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AllBehaviourClasses implements BehaviourClassContainer {

    public Class[] getBehaviourClasses() {
        return new Class[] {
                JUnitAdapterBehaviour.class
        };
    }

}
