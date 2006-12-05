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
public class Expectation extends UsingMatchers {
    
    private static final InvocationHandler NULL_INVOKER = new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) {
            return null;
        }
    };
    
    private final ExpectationRegistry registry;
    private final String methodName;
    private Matcher[] matchers = null; // initially we don't care about args
    private int minInvocations = 1;
    private int maxInvocations = 1;
    private int invocations;
    private Expectation after;
    private String id;
    private InvocationHandler invoker = NULL_INVOKER;

    private InvocationHandler[] invokers;

    private boolean inOrder;

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
        
        if (inOrder) {
            return invokers[invocations++].invoke(proxy, method, args);
        } else {
            invocations++;
            return invoker.invoke(proxy, method, args);
        }
    }
    
    public boolean matches(String actualName, Object[] args) {
        // avoid NPEs
        if (args == null) {
			args = new Object[0];
        }
        if (!methodName.equals(actualName)) return false;
        if (matchers == null) return true; // allow any args 
        if (matchers.length != args.length) return false;

        for (int i = 0; i < args.length; i++) {
            if (!matchers[i].matches(args[i])) return false;
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
			+ ( matchers != null ? Arrays.asList(matchers).toString() : "[anything]");
	}
    
    // Return value
    
    public Expectation will(InvocationHandler result) {
        this.invoker = result;
        return this;
    } 
    
    public Expectation inOrder() {
        inOrder = true;
        return this;
    }

    public Expectation will(InvocationHandler handler1, InvocationHandler handler2) {
        return will(new InvocationHandler[] {handler1, handler2});
    }
    
    public Expectation will(InvocationHandler handler1, InvocationHandler handler2, InvocationHandler handler3) {
        return will(new InvocationHandler[] {handler1, handler2, handler3});
    }

    private Expectation will(InvocationHandler[] invokers) {
        times(invokers.length);
        this.invokers = invokers;
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

    public Expectation with(Matcher matcher) {
        return with(new Matcher[] {matcher});
    }

    public Expectation with(Matcher matcher1, Matcher matcher2) {
        return with(new Matcher[] {matcher1, matcher2});
    }
    
    public Expectation with(Matcher matcher1, Matcher matcher2, Matcher matcher3) {
        return with(new Matcher[] {matcher1, matcher2, matcher3});
    }

    public Expectation with(Matcher[] matchers) {
        this.matchers = matchers;
        return this;
    }
    
    public Expectation withNoArguments() {
        this.matchers = new Matcher[0];
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
        return methodName + Arrays.asList(matchers);
    }
    
    public String methodName() {
        return methodName;
    }
    
    public boolean matches(String actualName) {
        return (methodName.equals(actualName));
    }

   
}
