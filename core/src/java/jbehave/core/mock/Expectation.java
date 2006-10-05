/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 * 
 * See license.txt for license details
 */
package jbehave.core.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import jbehave.core.Ensure;
import jbehave.core.exception.VerificationException;

/**
 * Represents an expectation on a mock
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Expectation extends UsingConstraints {
    
    private static final InvocationHandler NULL_INVOKER = new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) {
            return null;
        }
    };
    
    private final ExpectationRegistry registry;
    private final String methodName;
    private Constraint[] constraints = null; // initially we don't care about args
    private int minInvocations = 1;
    private int maxInvocations = 1;
    private int invocations;
    private Expectation after;
    private String id;
    private InvocationHandler invoker = NULL_INVOKER;

    /**
     *  Construct an expectation in a default state.
     * 
     * It initially expects to be called exactly once and will use a null invoker.
     */
    public Expectation(ExpectationRegistry registry, String methodName) {
        this.registry = registry;
        this.id = this.methodName = methodName;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (after != null) {
			after.verify();
        }
        Ensure.that(methodName + " called more than " + maxInvocations + " times", invocations < maxInvocations);
        invocations++;
        return invoker.invoke(proxy, method, args);
    }
    
    public boolean matches(String actualName, Object[] args) {
        // avoid NPEs
        if (args == null) {
			args = new Object[0];
        }
        if (!methodName.equals(actualName)) return false;
        if (constraints == null) return true; // allow any args 
        if (constraints.length != args.length) return false;

        for (int i = 0; i < args.length; i++) {
            if (!constraints[i].matches(args[i])) return false;
        }
        return true;
    }
    
    public void verify() {
		if (invocations < minInvocations) {
            String message = "Expected method not called: " + methodToString();        
            if (!id.equals(methodName)) {
                message += " id=" + id;
            }
            throw new VerificationException(message);
		}
    }

	/**
	 * Assemble the method name and signature
	 */
	private String methodToString() {
		return methodName
			+ ( constraints != null ? Arrays.asList(constraints).toString() : "[anything]");
	}
    
    // Return value
    
    public Expectation will(InvocationHandler result) {
        this.invoker = result;
        return this;
    } 
    
    // Invocations
    
    public Expectation once() {
        minInvocations = maxInvocations = 1;
        return this;
    }
    
    public Expectation never() {
        minInvocations = maxInvocations = 0;
        return this;
    }
    
    public Expectation atLeastOnce() {
        minInvocations = 1;
        maxInvocations = Integer.MAX_VALUE;
        return this;
    }
    
    public Expectation zeroOrMoreTimes() {
        minInvocations = 0;
        maxInvocations = Integer.MAX_VALUE;
        return this;
    }
    
    public Expectation times(int calls) {
        minInvocations = maxInvocations = calls;
        return this;
    }
    
    // with arguments

    public Expectation with(Object arg) {
        return with(sameInstanceAs(arg));
    }

    public Expectation with(Object arg1, Object arg2) {
        return with(sameInstanceAs(arg1), sameInstanceAs(arg2));
    }

    public Expectation with(Constraint constraint) {
        return with(new Constraint[] {constraint});
    }

    public Expectation with(Constraint constraint1, Constraint constraint2) {
        return with(new Constraint[] {constraint1, constraint2});
    }

    public Expectation with(Constraint[] constraints) {
        this.constraints = constraints;
        return this;
    }
    
    public Expectation withNoArguments() {
        this.constraints = new Constraint[0];
        return this;
    }

    // after
    
    public Expectation after(Mock otherMock, String otherId) {
        after = ((ExpectationRegistry)otherMock).lookup(otherId);
        return this;
    }
    
    public Expectation after(String otherId) {
        after = registry.lookup(otherId);
        return this;
    }
    
    // id
    
    public String id() {
        return id;
    }

    public Expectation id(String id) {
        this.id = id;
        return this;
    }
    
    public String toString() {
        return methodName + Arrays.asList(constraints);
    }
    
    public String methodName() {
        return methodName;
    }
    
    public boolean matches(String actualName) {
        return (methodName.equals(actualName));
    }    
}
