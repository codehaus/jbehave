/*
 * Created on 13-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.verify.Result;

/**
 * Good old Composite pattern. No framework is complete without one. I still
 * need to find an excuse to use the Simpleton pattern.<br>
 * <br>
 * &lt;plug&gt;This would be so much cleaner in Ruby.&lt;/plug&gt;
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class MethodListeners implements MethodListener {
    private final List listeners = new ArrayList();
    
    /**
     * The composition method for the composite.
     */
    public void add(MethodListener listener) {
        listeners.add(listener);
    }
    
    // Listener methods

    public void methodVerificationStarting(Method method) {
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((MethodListener)i.next()).methodVerificationStarting(method);
        }
    }

    public Result methodVerificationEnding(Result result, Object behaviourClassInstance) {
		for (Iterator i = listeners.iterator(); i.hasNext();) {
		    result = ((MethodListener)i.next()).methodVerificationEnding(result, behaviourClassInstance);
        }
		return result;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[CompositeListener:").append(" listeners: ").append(listeners).append("]");
        return buffer.toString();
    }
}
