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

    protected IsEqual eq( Object operand ) {
        return new IsEqual(operand);
    }
    
    protected IsEqual eq( boolean operand ) {
        return eq( new Boolean(operand) );
    }

    protected IsEqual eq( long operand ) {
        return eq( new Long(operand) );
    }
    
    protected IsEqual eq( double operand ) {
        return eq( new Double(operand) );
    }
    
    protected IsCloseTo closeTo( double operand, double error ) {
        return new IsCloseTo( operand, error );
    }
    
    protected IsSame same( Object operand ) {
        return new IsSame(operand);
    }
    
    protected IsInstanceOf instanceOf( Class type ) {
        return new IsInstanceOf(type);
    }
    
    protected IsInstanceOf a( Class type ) {
        return instanceOf(type);
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
    
    protected Stub returnValue( long result ) {
        return returnValue(new Long(result));
    }
    
    protected Stub returnValue( double result ) {
        return returnValue(new Double(result));
    }
    
    protected Stub throwException( Throwable throwable ) {
        return new ThrowStub(throwable);
    }
}
