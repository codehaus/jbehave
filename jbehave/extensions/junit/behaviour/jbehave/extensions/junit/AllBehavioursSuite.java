/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.junit;
import jbehave.AllSpecs;
import jbehave.extensions.junit.adapter.JUnitAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * So we can run all the behaviours inside the junit runner
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class AllBehavioursSuite {
    
    public static Test suite() {
        TestSuite suite = new TestSuite();
        Class[] behaviourClasses = new AllSpecs().getSpecs();
        for (int i = 0; i < behaviourClasses.length; i++) {
            suite.addTest(wrap(behaviourClasses[i]));
        }
        return suite;
    }

    private static Test wrap(Class behaviourClass) {
        return new JUnitAdapter(behaviourClass);
    }
}
