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
import org.jmock.core.stub.StubSequence;
import org.jmock.core.stub.ThrowStub;

/**
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 *
 */
public abstract class JMockSugar {
    protected InvocationMatcher once() {
        return new InvokeOnceMatcher();
    }
    
    protected InvocationMatcher atLeastOnce() {
        return new InvokeAtLeastOnceMatcher();
    }
    
    protected InvocationMatcher never() {
        return new TestFailureMatcher("expect not called");
    }
    
    protected Stub onConsecutiveCalls( Stub stub1, Stub stub2 ) {
        return new StubSequence(new Stub[]{stub1, stub2});
    }

    protected Stub onConsecutiveCalls( Stub stub1, Stub stub2, Stub stub3 ) {
        return new StubSequence(new Stub[]{stub1, stub2, stub3});
    }

    protected Stub onConsecutiveCalls( Stub stub1, Stub stub2, Stub stub3, Stub stub4 ) {
        return new StubSequence(new Stub[]{stub1, stub2, stub3, stub4});
    }

    
    protected IsEqual equal( Object operand ) {
        return new IsEqual(operand);
    }
    
    protected IsEqual equal( boolean operand ) {
        return equal( new Boolean(operand) );
    }
    
    protected IsEqual equal( byte operand ) {
        return equal( new Byte(operand) );
    }
    
    protected IsEqual equal( short operand ) {
        return equal( new Short(operand) );
    }
    
    protected IsEqual equal( char operand ) {
        return equal( new Character(operand) );
    }
    
    protected IsEqual equal( int operand ) {
        return equal( new Integer(operand) );
    }
    
    protected IsEqual equal( long operand ) {
        return equal( new Long(operand) );
    }
    
    protected IsEqual equal( float operand ) {
        return equal( new Float(operand) );
    }
    
    protected IsEqual equal( double operand ) {
        return equal( new Double(operand) );
    }
    
    protected IsCloseTo closeTo( double operand, double error ) {
        return new IsCloseTo( operand, error );
    }
    
    protected IsSame same( Object operand ) {
        return new IsSame(operand);
    }
    
    protected IsInstanceOf instanceOf( Class operandClass ) {
        return new IsInstanceOf(operandClass);
    }
    
    protected IsInstanceOf a( Class operandClass ) {
        return instanceOf(operandClass);
    }
    
    protected StringContains substring( String substring ) {
        return new StringContains(substring);
    }
    
    protected IsNot not( Constraint c ) {
        return new IsNot(c);
    }
    
    protected And both( Constraint left, Constraint right ) {
        return new And(left,right);
    }
    
    protected Or either( Constraint left, Constraint right ) {
        return new Or(left,right);
    }
    
    protected IsAnything anything() {
        return new IsAnything();
    }
    
    protected Stub returnValue( Object o ) {
        return new ReturnStub(o);
    }
    
    protected Stub returnValue( boolean result ) {
        return returnValue(new Boolean(result));
    }
    
    protected Stub returnValue( byte result ) {
        return returnValue(new Byte(result));
    }
    
    protected Stub returnValue( char result ) {
        return returnValue(new Character(result));
    }
    
    protected Stub returnValue( short result ) {
        return returnValue(new Short(result));
    }
    
    protected Stub returnValue( int result ) {
        return returnValue(new Integer(result));
    }
    
    protected Stub returnValue( long result ) {
        return returnValue(new Long(result));
    }
    
    protected Stub returnValue( float result ) {
        return returnValue(new Float(result));
    }
    
    protected Stub returnValue( double result ) {
        return returnValue(new Double(result));
    }
    
    protected Stub throwException( Throwable throwable ) {
        return new ThrowStub(throwable);
    }

}
