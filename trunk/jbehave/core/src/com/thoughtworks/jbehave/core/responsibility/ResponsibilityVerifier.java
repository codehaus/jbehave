/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Listener;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface ResponsibilityVerifier {
    /** Null Object */
    ResponsibilityVerifier NULL = new ResponsibilityVerifier() {
        public Result verifyResponsibility(Listener listener, Method method, Object instance) {
            return new Result(method.getDeclaringClass().getName(), method.getName());
        }
    };
    
    /**
     * Verify an individual responsibility.
     */
    Result verifyResponsibility(Listener listener, Method method, Object instance);
}