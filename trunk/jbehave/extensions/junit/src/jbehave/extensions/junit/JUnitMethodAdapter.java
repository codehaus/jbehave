/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.junit;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import jbehave.framework.Listener;
import jbehave.framework.responsibility.ExecutingResponsibilityVerifier;
import jbehave.framework.responsibility.Result;
import jbehave.util.StringUtils;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import junit.framework.TestResult;


public class JUnitMethodAdapter extends TestCase {
    private final Method method;

    public JUnitMethodAdapter(Method method) {
        super(StringUtils.unCamelCase(method.getName()));
        this.method = method;
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
            new ExecutingResponsibilityVerifier().verifyResponsibility(Listener.NULL, method);
        if (result.failed()) {
            testResult.addFailure(this, buildAssertionFailedError(result.getTargetException()));
        }
        else if (result.threwException()) {
            testResult.addError(this, result.getTargetException());
        }
        else if (result.isPending()) {
            System.out.println(result.getName() + ": " + result.getTargetException().getMessage());
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