/*
 * Created on 16-Mar-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jmock.core.mixin;

import org.jmock.core.Stub;
import org.jmock.core.stub.ReturnStub;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Return {
    public static Stub value( Object o ) {
        return new ReturnStub(o);
    }
    
    public static Stub value( boolean result ) {
        return value(new Boolean(result));
    }
    
    public static Stub value( byte result ) {
        return value(new Byte(result));
    }
    
    public static Stub value( char result ) {
        return value(new Character(result));
    }
    
    public static Stub value( short result ) {
        return value(new Short(result));
    }
    
    public static Stub value( int result ) {
        return value(new Integer(result));
    }
    
    public static Stub value( long result ) {
        return value(new Long(result));
    }
    
    public static Stub value( float result ) {
        return value(new Float(result));
    }
    
    public static Stub value( double result ) {
        return value(new Double(result));
    }
}
