/*
 * Created on 09-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.Stub;
import org.jmock.core.constraint.And;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsCloseTo;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsInstanceOf;
import org.jmock.core.constraint.IsNot;
import org.jmock.core.constraint.IsSame;
import org.jmock.core.constraint.Or;
import org.jmock.core.constraint.StringContains;
import org.jmock.core.matcher.InvokeAtLeastOnceMatcher;
import org.jmock.core.matcher.InvokeOnceMatcher;
import org.jmock.core.matcher.TestFailureMatcher;
import org.jmock.core.stub.ReturnStub;
import org.jmock.core.stub.ThrowStub;

/**
 * Convenience methods for writing JMock expectations.
 * 
 * Use like this:
 * <pre>
 * mock.expect(Invoked.once()).method("say")
 *     .with(Is.equal("hello"))
 *     .will(Return.value(null));
 * </pre>
 * 
 * TODO write behaviour class for these
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface JMockMixins {
    /**
     * Convenience methods for how often a method is invoked
     * 
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
    
    /**
     * Convenience methods for verifications
     * 
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
    
    /**
     * Convenience methods for types of return
     * 
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
    
    /**
     * Convenience methods for exception handling
     * 
     * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
     */
    public class Throw {
        public static Stub exception( Throwable throwable ) {
            return new ThrowStub(throwable);
        }
    }
}
