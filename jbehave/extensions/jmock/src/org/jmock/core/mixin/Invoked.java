/*
 * Created on 16-Mar-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jmock.core.mixin;

import org.jmock.core.InvocationMatcher;
import org.jmock.core.matcher.InvokeAtLeastOnceMatcher;
import org.jmock.core.matcher.InvokeOnceMatcher;
import org.jmock.core.matcher.TestFailureMatcher;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Invoked {
    public static InvocationMatcher once() {
        return new InvokeOnceMatcher();
    }
    
    public static InvocationMatcher atLeastOnce() {
        return new InvokeAtLeastOnceMatcher();
    }
    
    public static InvocationMatcher never() {
        return new TestFailureMatcher("expect not called");
    }
}
