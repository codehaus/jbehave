/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.BehaviourListener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @deprecated use {@link BehaviourVerifier} instead
 */
public interface MethodVerifier {
    /**
     * Verify an individual behaviour.
     */
    Result verifyMethod(BehaviourListener listener, Method method, Object instance);
}