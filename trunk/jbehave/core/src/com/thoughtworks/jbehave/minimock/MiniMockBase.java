/*
 * Created on 24-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.minimock;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MiniMockBase {
    public Constraint eq(final Object expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return arg == null ? expectedArg == null : arg.equals(expectedArg);
            }
            public String toString() {
                return "eq(" + expectedArg + ")";
            }
        };
    }

    public Constraint same(final Object expectedArg) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return expectedArg == arg;
            }
            public String toString() {
                return "same(" + expectedArg + ")";
            }
        };
    }

    public Constraint anything() {
        return new Constraint() {
            public boolean matches(Object arg) {
                return true;
            }
            public String toString() {
                return "anything";
            }
        };
    }

    public Constraint instanceOf(final Class type) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return type.isInstance(arg);
            }
            public String toString() {
                return "instance of " + type.getName();
            }
        };
    }
}
