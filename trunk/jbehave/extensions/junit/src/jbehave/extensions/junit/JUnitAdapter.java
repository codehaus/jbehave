/*
 * Created on 11-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.junit;

import java.io.InputStream;
import java.util.Properties;

import jbehave.extensions.junit.listener.TestSuitePopulaterListener;
import jbehave.framework.BehaviourClassVerifier;
import jbehave.framework.Listener;
import jbehave.framework.NotifyingResponsibilityVerifier;
import jbehave.framework.exception.BehaviourFrameworkError;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Runs a behaviour class in a JUnit test runner.
 * 
 * Looks for a properties file called <tt>jbehave.properties</tt> on
 * the classpath, containing a property called <tt>behaviourClass</tt>.<br/>
 * <br/>
 * This means you can set your project up with the jars for JBehave core
 * and the JUnit extension, as well as junit.jar, and then run
 * <tt>jbehave.extensions.junit.JUnitAdapter</tt> as a test inside any
 * old test runner (such as your IDE's). This will look for the properties
 * file and run the behaviour class it finds in there.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JUnitAdapter {

    private static Class BEHAVIOUR_CLASS = null;
    
    public static void setBehaviourClass(Class behaviourClass) {
        JUnitAdapter.BEHAVIOUR_CLASS = behaviourClass;
    }

    public static Test suite() {
        Class behaviourClass = (BEHAVIOUR_CLASS != null
                ? BEHAVIOUR_CLASS : readBehaviourClassFromResource());
        final TestSuite[] suiteRef = new TestSuite[1]; // Collecting Parameter
        BehaviourClassVerifier behaviourClassVerifier =
            new BehaviourClassVerifier(behaviourClass, new NotifyingResponsibilityVerifier());
        final Listener listener = new TestSuitePopulaterListener(suiteRef);
        behaviourClassVerifier.verifyBehaviourClass(listener);
        return suiteRef[0];
    }

    private static Class readBehaviourClassFromResource() {
        String behaviourClassName = null;
        try {
            System.out.println("Looking for behaviour class");
            InputStream in = JUnitAdapter.class.getClassLoader().getResourceAsStream("jbehave.properties");
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                in.close();
                behaviourClassName = props.getProperty("behaviourClass", behaviourClassName);
            }
            System.out.println("Loading " + behaviourClassName);
            return Class.forName(behaviourClassName);
        } catch (Exception e) {
            throw new BehaviourFrameworkError("No behaviour class found for " + behaviourClassName);
        }
    }
}
