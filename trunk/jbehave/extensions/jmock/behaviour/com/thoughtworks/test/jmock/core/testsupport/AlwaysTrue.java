package com.thoughtworks.test.jmock.core.testsupport;

import org.jmock.core.Constraint;

public class AlwaysTrue implements Constraint {
    public static AlwaysTrue INSTANCE = new AlwaysTrue();
    
    public boolean eval( Object o ) { 
        return true; 
    }
    
    public StringBuffer describeTo( StringBuffer buffer ) { 
        return buffer.append("always true");
    }
}
