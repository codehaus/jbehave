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

public class Expectation extends MiniMockBase {
    private final Mock mock;
    private final String methodName;

    private Constraint[] constraints;
    private int minCalls;
    private int maxCalls;
    private int calls;
    private Expectation after;
    private String id;
    private Throwable throwable;
    private Object returnValue;

    public Expectation(Mock mock, String methodName) {
        this.mock = mock;
        this.id = this.methodName = methodName;
        once();
    }

    public void methodCalled() {
        if (after != null) {
            after.verify();
        }
        Verify.that("Unexpected call to " + methodName + " (Expected " + maxCalls + " calls)", calls < maxCalls);
        calls++;
    }

    public boolean matches(String actualName, Object[] args) {
        if (!methodName.equals(actualName)) return false;
        
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
        Verify.that("Expected method not called: " + methodName + Arrays.asList(constraints), calls >= minCalls);
    }
    
    // Return value
    
    public Expectation willReturn(Object value) {
        returnValue = value;
        return this;
    }

    public final Object returnValue() throws Throwable {
        if (throwable != null) throw throwable;
        return returnValue;
    }
    
    public void willThrow(Throwable throwable) {
        this.throwable = throwable;
    }

    // Counts (only used by MockObject class)
    
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
    
    public Expectation zeroOrMoreTimes() {
        minCalls = 0;
        maxCalls = Integer.MAX_VALUE;
        return this;
    }
    
    public Expectation times(int calls) {
        minCalls = maxCalls = calls;
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

    // after
    
    public Expectation after(Mock otherMock, String otherId) {
        after = otherMock.expectation(otherId);
        return this;
    }

    public Expectation after(String otherId) {
        return after(mock, otherId);
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