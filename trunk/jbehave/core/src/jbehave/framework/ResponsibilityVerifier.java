/*
 * Created on 12-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface ResponsibilityVerifier {
    /** Null Object */
    ResponsibilityVerifier NULL = new ResponsibilityVerifier() {
        public ResponsibilityVerification verifyResponsibility(Listener listener, Method method) {
            return new ResponsibilityVerification(method.getDeclaringClass().getName(), method.getName());
        }
    };
    
    /**
     * Verify an individual responsibility.
     */
    ResponsibilityVerification verifyResponsibility(Listener listener, Method method);
}