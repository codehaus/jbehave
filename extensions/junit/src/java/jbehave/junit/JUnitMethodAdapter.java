/*
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.junit;

import java.io.PrintStream;
import java.io.PrintWriter;

import jbehave.core.behaviour.Behaviour;
import jbehave.core.behaviour.BehaviourMethod;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.result.Result;
import jbehave.core.util.CamelCaseConverter;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestResult;

/**
 * Adapter wrapping a BehaviourMethod as a JUnit Test 
 * 
 * @author Dan North
 * @author Mauro Talevi
 * @see {@link Test}
 */
public class JUnitMethodAdapter implements BehaviourListener, Test {
    private final BehaviourMethod behaviourMethod;
    private Result result;

    public JUnitMethodAdapter(BehaviourMethod behaviourMethod) {
        this.behaviourMethod = behaviourMethod;
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
        behaviourMethod.verifyTo(this);
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
            private static final long serialVersionUID = 1L;
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
    
    public void before(Behaviour behaviour) {
        // no-op
    }

    public void after(Behaviour behaviour) {
        // no-op
    }

    public String toString() {
        return new CamelCaseConverter(behaviourMethod.method().getName()).toPhrase();
    }

}