/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 * 
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.thoughtworks.jbehave.core.Verify;

public class Expectation extends MiniMockSugar {
    interface Finder {
        Expectation findExpectation(String id);
    }
    
    private static final InvocationHandler NULL_INVOKER = new InvocationHandler() {
        public Object invoke(Object proxy, Method method, Object[] args) {
            return null;
        }
    };
    
    private final Finder finder;
    private final String methodName;
    private Constraint[] constraints = new Constraint[0];
    private int minInvocations = 1;
    private int maxInvocations;
    private int invocations;
    private Expectation after;
    private String id;
    private InvocationHandler invoker = NULL_INVOKER;

    /**
     *  Construct an expectation in a default state.
     * 
     * It initially expects to be called exactly once and will use a null invoker.
     */
    public Expectation(Finder finder, String methodName) {
        this.finder = finder;
        this.id = this.methodName = methodName;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (after != null) after.verify();
        Verify.that("Unexpected call to " + methodName + " (Expected " + maxInvocations + " calls)", invocations < maxInvocations);
        invocations++;
        return invoker.invoke(proxy, method, args);
    }
    
    public boolean matches(String actualName, Object[] args) {
        // avoid NPEs
        if (args == null) args = new Object[0];
        
        if (invocations >= maxInvocations)  return false; // no calls left

        if (!methodName.equals(actualName)) return false;
        
        if (constraints.length != args.length) return false;

        for (int i = 0; i < args.length; i++) {
            if (!constraints[i].matches(args[i])) return false;
        }
        return true;
    }
    
    public void verify() {
        Verify.that("Expected method not called: " + methodName + Arrays.asList(constraints), invocations >= minInvocations);
    }
    
    // Return value
    public Expectation will(InvocationHandler result) {
        this.invoker = result;
        return this;
    }

    public Expectation isVoid() {
        invoker = NULL_INVOKER;
        return this;
    }
    
    public Expectation willReturn(Object value) {
        return will(returnValue(value));
    }
    
    public void willThrow(Throwable cause) {
        invoker = throwException(cause);
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

    public Expectation with(Object object) {
        return with(same(object));
    }

    public Expectation with(Constraint constraint) {
        return with(new Constraint[] {constraint});
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
        after = ((Finder)otherMock).findExpectation(otherId);
        return this;
    }

    public Expectation after(String otherId) {
        after = finder.findExpectation(otherId);
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
}