/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.junit.listener;

import java.lang.reflect.Method;

import junit.framework.TestSuite;

import com.thoughtworks.jbehave.core.Behaviour;
import com.thoughtworks.jbehave.core.BehaviourClass;
import com.thoughtworks.jbehave.core.BehaviourListener;
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.extensions.junit.JUnitMethodAdapter;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TestSuitePopulater implements BehaviourListener {
    private final TestSuite[] suiteRef;
    private TestSuite currentSuite = null;
    private Class currentBehaviourClass;

    public TestSuitePopulater(TestSuite[] suiteRef) {
        this.suiteRef = suiteRef;
    }

    private void behaviourClassVerificationStarting(BehaviourClass behaviourClass) {
        currentSuite = new TestSuite(behaviourClass.getClassToVerify().getName());
        if (suiteRef[0] == null) {
            suiteRef[0] = currentSuite;
        }
        else {
            suiteRef[0].addTest(currentSuite);
        }
        currentBehaviourClass = behaviourClass.getClassToVerify();
    }

    private void behaviourMethodVerificationStarting(BehaviourMethod behaviourMethod) {
        Method method = behaviourMethod.getMethodToVerify();
        try {
            Object instance = currentBehaviourClass.newInstance();
            currentSuite.addTest(new JUnitMethodAdapter(method,
                    instance));
        } catch (Exception e) {
            String message = "Problem adding test for "
                + currentBehaviourClass.getName() + "." + method.getName();
            throw new JBehaveFrameworkError(message, e);
        }
    }

    public void behaviourVerificationStarting(Behaviour behaviour) {
        if (behaviour instanceof BehaviourClass) {
            behaviourClassVerificationStarting((BehaviourClass) behaviour);
        }
        else {
            behaviourMethodVerificationStarting((BehaviourMethod) behaviour);
        }
    }

    public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
        return result;
    }

    public boolean caresAbout(Behaviour behaviour) {
        return behaviour instanceof BehaviourClass || behaviour instanceof BehaviourMethod;
    }
}
