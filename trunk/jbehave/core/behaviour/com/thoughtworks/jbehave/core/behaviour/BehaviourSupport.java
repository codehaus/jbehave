/*
 * Created on 25-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.behaviour;

import com.thoughtworks.jbehave.core.minimock.Constraint;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
class BehaviourSupport extends UsingMiniMock {
    public static class HasTwoMethods {
        public void shouldDoSomething() {
        }
        public void shouldDoSomethingElse() {
        }
    }
    
    protected Constraint matchesBehaviourClassName(final Class classToMatch) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return arg instanceof BehaviourClass && classToMatch == ((BehaviourClass)arg).classToVerify();
            }
            public String toString() {
                String name = classToMatch.getName();
                return "match " + name.substring(name.lastIndexOf('$') + 1);
            }
        };
    }
    
    protected Constraint matchesBehaviourMethodName(final String methodName) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return arg instanceof BehaviourMethod && ((BehaviourMethod)arg).method().getName().equals(methodName);
            }
        };
    }
}
