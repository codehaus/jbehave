/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.junit;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;

import com.thoughtworks.jbehave.core.listeners.NULLMethodListener;
import com.thoughtworks.jbehave.core.verify.ExecutingMethodVerifier;
import com.thoughtworks.jbehave.core.verify.Result;
import com.thoughtworks.jbehave.util.ConvertCase;


public class JUnitMethodAdapter extends TestCase {
    private final Method method;
    private final Object instance;

    public JUnitMethodAdapter(Method method, Object instance) {
        super(new ConvertCase(method.getName()).toSeparateWords());
        this.method = method;
        this.instance = instance;
    }

    public int countTestCases() {
        return 1;
    }
    
    public void run(TestResult testResult) {
        testResult.startTest(this);
        verifyResponsibility(testResult);
        testResult.endTest(this);
    }

    private void verifyResponsibility(TestResult testResult) {
        final Result result =
            new ExecutingMethodVerifier().verifyMethod(new NULLMethodListener(), method, instance);
        if (result.failed()) {
            testResult.addFailure(this, buildAssertionFailedError(result.getCause()));
        }
        else if (result.threwException()) {
            testResult.addError(this, result.getCause());
        }
        else if (result.isPending()) {
            System.out.println(result.getName() + ": " + result.getCause().getMessage());
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
}