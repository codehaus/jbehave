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
import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;
import com.thoughtworks.jbehave.core.exception.JBehaveFrameworkError;
import com.thoughtworks.jbehave.extensions.junit.JUnitMethodAdapter;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TestSuitePopulater implements Visitor {
    private final TestSuite[] suiteRef;
    private TestSuite currentSuite = null;
    private Class currentBehaviourClass;

    public TestSuitePopulater(TestSuite[] suiteRef) {
        this.suiteRef = suiteRef;
    }

    public void before(Visitable behaviour) {
        if (behaviour instanceof BehaviourClass) {
            beforeClass((BehaviourClass) behaviour);
        }
        else {
            beforeMethod((BehaviourMethod) behaviour);
        }
    }

    public void after(Visitable behaviour) {
    }

    public void gotResult(Result result) {
    }

    private void beforeClass(BehaviourClass visitableClass) {
        currentSuite = new TestSuite(visitableClass.classToVerify().getName());
        if (suiteRef[0] == null) {
            suiteRef[0] = currentSuite;
        }
        else {
            suiteRef[0].addTest(currentSuite);
        }
        currentBehaviourClass = visitableClass.classToVerify();
    }

    private void beforeMethod(BehaviourMethod behaviourMethod) {
        Method method = behaviourMethod.methodToVerify();
        try {
            Object instance = currentBehaviourClass.newInstance();
            currentSuite.addTest(new JUnitMethodAdapter(method, instance));
        } catch (Exception e) {
            String message = "Problem adding test for " + currentBehaviourClass.getName() + "." + method.getName();
            throw new JBehaveFrameworkError(message, e);
        }
    }
}
