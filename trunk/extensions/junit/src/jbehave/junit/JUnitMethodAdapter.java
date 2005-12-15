/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.junit;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import jbehave.core.behaviour.Behaviour;
import jbehave.core.behaviour.BehaviourMethod;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.result.Result;
import jbehave.core.util.ConvertCase;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestResult;


public class JUnitMethodAdapter implements BehaviourListener, Test {
    private final Method method;
    private final Object instance;
    private Result result;

    public JUnitMethodAdapter(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public int countTestCases() {
        return 1;
    }
    
    public void run(TestResult testResult) {
        testResult.startTest(this);
        verifyMethod(testResult);
        testResult.endTest(this);
    }

    private void verifyMethod(TestResult testResult) {
        new BehaviourMethod(instance, method).verifyTo(this);
        if (result.failed()) {
            testResult.addFailure(this, buildAssertionFailedError(result.cause()));
        }
        else if (result.failed()) {
            testResult.addError(this, result.cause());
        }
        else if (result.isPending()) {
            System.out.println(result.name() + ": " + result.cause().getMessage());
        }
    }

    private AssertionFailedError buildAssertionFailedError(final Throwable targetException) {
        AssertionFailedError error = new AssertionFailedError(targetException.getMessage()) {
            public void printStackTrace() {
                targetException.printStackTrace();
            }
            public void printStackTrace(PrintStream out) {
                targetException.printStackTrace(out);
            }
            public void printStackTrace(PrintWriter out) {
                targetException.printStackTrace(out);
            }
        };
        
        // try to fake the stacktrace - only works in 1.4
        try {
            error.setStackTrace(targetException.getStackTrace());
        }
        catch (NoSuchMethodError shame) {
            // shame - not running in 1.4 JRE
        }
        return error;
    }

    public void gotResult(Result result) {
        this.result = result;
    }
    
    public String toString() {
        return new ConvertCase(method.getName()).toSeparateWords();
    }

    public void before(Behaviour behaviour) {
        // TODO Auto-generated method stub
        
    }

    public void after(Behaviour behaviour) {
        // TODO Auto-generated method stub
        
    }
}