/*
 * Created on 28-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import com.thoughtworks.jbehave.util.InvokeMethodWithSetUpAndTearDown;

/**
 * Represents a verifier for a single method, which can verify
 * itself and present the results of its verification.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @deprecated
 */
public class ExecutingMethodVerifier extends NotifyingMethodVerifier {
    public ExecutingMethodVerifier() {
        super(new InvokeMethodWithSetUpAndTearDown());
    }
}
