/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.junit.listener;

import java.lang.reflect.Method;

import jbehave.core.behaviour.BehaviourClass;
import jbehave.core.behaviour.BehaviourMethod;
import jbehave.core.exception.JBehaveFrameworkError;
import jbehave.core.result.Result;
import jbehave.junit.JUnitMethodAdapter;
import junit.framework.TestSuite;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TestSuitePopulater {
    private final TestSuite[] suiteRef;
    private TestSuite currentSuite = null;
    private Class currentBehaviourClass;

    public TestSuitePopulater(TestSuite[] suiteRef) {
        this.suiteRef = suiteRef;
    }

    public void gotResult(Result result) {
    }

    public void visitBehaviourClass(BehaviourClass behaviourClass) {
        currentSuite = new TestSuite(behaviourClass.classToVerify().getName());
        if (suiteRef[0] == null) {
            suiteRef[0] = currentSuite;
        }
        else {
            suiteRef[0].addTest(currentSuite);
        }
        currentBehaviourClass = behaviourClass.classToVerify();
    }

    public void visitBehaviourMethod(BehaviourMethod behaviourMethod) {
        Method method = behaviourMethod.method();
        try {
            Object instance = currentBehaviourClass.newInstance();
            currentSuite.addTest(new JUnitMethodAdapter(method, instance));
        } catch (Exception e) {
            String message = "Problem adding test for " + currentBehaviourClass.getName() + "." + method.getName();
            throw new JBehaveFrameworkError(message, e);
        }
    }
}
