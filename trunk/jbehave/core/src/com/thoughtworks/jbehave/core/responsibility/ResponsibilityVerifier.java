/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.ResponsibilityListener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface ResponsibilityVerifier {
    /**
     * Verify an individual responsibility.
     */
    Result verifyResponsibility(ResponsibilityListener listener, Method method, Object instance);
}