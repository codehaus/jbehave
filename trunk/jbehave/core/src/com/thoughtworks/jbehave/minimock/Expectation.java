/*
 * Created on 10-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 * 
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.minimock;

import java.util.Arrays;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.minimock.UsingMiniMock.Constraint;

public class Expectation {
    private final String expectedMethodName;
    private final Constraint[] constraints;

    private Object returnValue;
    private int minCalls = 1;
    private int maxCalls = 1;
    private int calls;

    public Expectation(String methodName, Constraint[] constraints) {
        this.expectedMethodName = methodName;
        this.constraints = constraints;
    }

    public Expectation(String methodName, Constraint constraint) {
        this(methodName, new Constraint[] { constraint });
    }

    public Expectation(String methodName) {
        this(methodName, new Constraint[0]);
    }

    public void methodCalled() {
        Verify.that("Unexpected call to " + expectedMethodName + " (Expected " + maxCalls + " calls)", calls < maxCalls);
        calls++;
    }

    public boolean matches(String methodName, Object[] args) {
        if (!methodName.equals(expectedMethodName)) return false;
        
        if (args == null) {
            args = new Object[0];
        }

        if (constraints.length != args.length) return false;

        for (int i = 0; i < args.length; i++) {
            if (!constraints[i].matches(args[i])) return false;
        }
        return true;
    }
    
    public void verify() {
        Verify.that("Expected method not called: " + expectedMethodName + Arrays.asList(constraints), calls >= minCalls);
    }
    
    // Return value
    
    public void willReturn(Object value) {
        returnValue = value;
    }

    public final Object returnValue() {
        return returnValue;
    }

    // Counts
    
    public Expectation stubs() {
        minCalls = 0;
        maxCalls = Integer.MAX_VALUE;
        return this;
    }
    
    public Expectation once() {
        minCalls = maxCalls = 1;
        return this;
    }
    
    public Expectation never() {
        minCalls = maxCalls = 0;
        return this;
    }
    
    public Expectation atLeastOnce() {
        minCalls = 1;
        maxCalls = Integer.MAX_VALUE;
        return this;
    }

    public void willThrow(Throwable throwable) {
        throw new UnsupportedOperationException("todo");
    }
}