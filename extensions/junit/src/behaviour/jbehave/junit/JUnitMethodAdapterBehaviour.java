/*
 * Created on 13-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.junit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jbehave.core.mock.UsingConstraints;
import junit.framework.Test;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JUnitMethodAdapterBehaviour extends UsingConstraints {
    private static final List sequenceOfEvents = new ArrayList();
    
    public static class SomeBehaviourClass {
        public void shouldDoSomething() throws Exception {
            sequenceOfEvents.add("shouldDoSomething");
        }
    }
    
    public void shouldWrapMethodAsTestCase() throws Exception {
        // setup
        Method method = SomeBehaviourClass.class.getMethod("shouldDoSomething", null);
        Object instance = SomeBehaviourClass.class.newInstance();

        // execute
        Test test = new JUnitMethodAdapter(method, instance);
        
        // verify
        ensureThat(test, not(eq(null)));
    }
}
