package test.jmock.core.mixin;

import junit.framework.TestCase;

import org.jmock.core.Constraint;
import org.jmock.core.mixin.Is;
import org.jmock.util.Dummy;

import test.jmock.core.testsupport.AlwaysFalse;
import test.jmock.core.testsupport.AlwaysTrue;


public class MixinsTest extends TestCase {
    Constraint trueConstraint = AlwaysTrue.INSTANCE;
    Constraint falseConstraint = AlwaysFalse.INSTANCE;
    
    public void testHasMixinMethodForCreatingIsEqualConstraints() {
        String stringValue = new String("STRING VALUE");
		assertConstraintTrue( Is.equal(stringValue), stringValue );
    }
    
    public void testMixinMethodForCreatingIsEqualConstraintsIsOverloadedForPrimitiveTypes() {
        assertConstraintTrue( Is.equal(true), new Boolean(true) );
        assertConstraintTrue( Is.equal(false), new Boolean(false) );
        assertConstraintTrue( Is.equal((byte)1), new Byte((byte)1) );
        assertConstraintTrue( Is.equal((short)1), new Short((short)1) );
        assertConstraintTrue( Is.equal('a'), new Character('a') );
        assertConstraintTrue( Is.equal(1), new Integer(1) );
        assertConstraintTrue( Is.equal(1L), new Long(1L) );
        assertConstraintTrue( Is.equal(1.0F), new Float(1.0F) );
        assertConstraintTrue( Is.equal(1.0), new Double(1.0) );
        
        assertConstraintFalse( Is.equal(true), new Boolean(false) );
        assertConstraintFalse( Is.equal(false), new Boolean(true) );
        assertConstraintFalse( Is.equal((byte)1), new Byte((byte)2) );
        assertConstraintFalse( Is.equal((short)1), new Short((short)2) );
        assertConstraintFalse( Is.equal('a'), new Character('b') );
        assertConstraintFalse( Is.equal(1), new Integer(2) );
        assertConstraintFalse( Is.equal(1L), new Long(2L) );
        assertConstraintFalse( Is.equal(1.0F), new Float(2.0F) );
        assertConstraintFalse( Is.equal(1.0), new Double(2.0) );

    }
    
    public void testHasMixinMethodForCreatingIsSameConstraints() {
        Object o1 = Dummy.newDummy("o1");
        Object o2 = Dummy.newDummy("o2");
        
        assertConstraintTrue( Is.same(o1), o1 );
        assertConstraintFalse( Is.same(o1), o2 );
    }
    
    public void testHasMixinMethodForCreatingIsAConstraints() {
    	String aString = "a string";
        
        assertConstraintTrue( Is.instanceOf(String.class), aString );
        assertConstraintTrue( Is.instanceOf(Object.class), aString );
        assertConstraintFalse( Is.instanceOf(Integer.class), aString );
        
        assertConstraintTrue( Is.a(String.class), aString );
        assertConstraintTrue( Is.a(Object.class), aString );
        assertConstraintFalse( Is.a(Integer.class), aString );
    }
    
    public void testHasMixinMethodForCreatingStringContainsConstraints() {
        assertConstraintTrue( Is.substring("fruit"), "fruitcake" );
        assertConstraintFalse( Is.substring("chocolate chips"), "fruitcake" );
    }
    
    public void testHasMixinMethodForLogicalNegationOfConstraints() {
        assertConstraintTrue( Is.not(Is.equal("hello")), "world" );
        assertConstraintFalse( Is.not(Is.equal("hello")), "hello" );
    }
    
    public void testHasMixinMethodForLogicalConjunctionOfConstraints() {
        Object ignored = new Object();
        
        assertConstraintTrue(  Is.both( trueConstraint,  trueConstraint  ), ignored );
        assertConstraintFalse( Is.both( trueConstraint,  falseConstraint ), ignored );
        assertConstraintFalse( Is.both( falseConstraint, trueConstraint  ), ignored );
        assertConstraintFalse( Is.both( falseConstraint, falseConstraint ), ignored );
    }
    
    public void testHasMixinMethodForLogicalDisjunctionOfConstraints() {
        Object ignored = new Object();
        
        assertConstraintTrue(  Is.either( trueConstraint,  trueConstraint  ), ignored );
        assertConstraintTrue(  Is.either( trueConstraint,  falseConstraint ), ignored );
        assertConstraintTrue(  Is.either( falseConstraint, trueConstraint  ), ignored );
        assertConstraintFalse( Is.either( falseConstraint, falseConstraint ), ignored );
    }
    
    public void testHasMixinMethodForAnythingConstraint() throws Exception {
    	Object ignored = new Object();
		assertConstraintTrue(Is.anything(), ignored);
	}
    
    private void assertConstraintTrue( Constraint constraint, Object argument ) {
    	assertTrue( "expected <"+constraint+"> to return true when passed <"+argument+">", 
                    constraint.eval(argument) );
    }
    
    private void assertConstraintFalse( Constraint constraint, Object argument ) {
        assertFalse( "expected <"+constraint+"> to return false when passed <"+argument+">", 
                     constraint.eval(argument) );
    }
}
