/*
 * Created on 17-Mar-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jmock.core.mixin;

import org.jmock.core.Constraint;
import org.jmock.core.constraint.And;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsCloseTo;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsInstanceOf;
import org.jmock.core.constraint.IsNot;
import org.jmock.core.constraint.IsSame;
import org.jmock.core.constraint.Or;
import org.jmock.core.constraint.StringContains;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Is {
    public static IsEqual equal( Object operand ) {
        return new IsEqual(operand);
    }
    
    public static IsEqual equal( boolean operand ) {
        return equal( new Boolean(operand) );
    }
    
    public static IsEqual equal( byte operand ) {
        return equal( new Byte(operand) );
    }
    
    public static IsEqual equal( short operand ) {
        return equal( new Short(operand) );
    }
    
    public static IsEqual equal( char operand ) {
        return equal( new Character(operand) );
    }
    
    public static IsEqual equal( int operand ) {
        return equal( new Integer(operand) );
    }
    
    public static IsEqual equal( long operand ) {
        return equal( new Long(operand) );
    }
    
    public static IsEqual equal( float operand ) {
        return equal( new Float(operand) );
    }
    
    public static IsEqual equal( double operand ) {
        return equal( new Double(operand) );
    }
    
    public static IsCloseTo closeTo( double operand, double error ) {
        return new IsCloseTo( operand, error );
    }
    
    public static IsSame same( Object operand ) {
        return new IsSame(operand);
    }
    
    public static IsInstanceOf instanceOf( Class operandClass ) {
        return new IsInstanceOf(operandClass);
    }
    
    public static IsInstanceOf a( Class operandClass ) {
        return instanceOf(operandClass);
    }
    
    public static StringContains substring( String substring ) {
        return new StringContains(substring);
    }
    
    public static IsNot not( Constraint c ) {
        return new IsNot(c);
    }
    
    public static And both( Constraint left, Constraint right ) {
        return new And(left,right);
    }
    
    public static Or either( Constraint left, Constraint right ) {
        return new Or(left,right);
    }
    
    public static IsAnything anything() {
        return new IsAnything();
    }
}
