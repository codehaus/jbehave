/*
 * Created on 18-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.minimock;

import com.thoughtworks.jbehave.core.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class UsingMiniMockBehaviour {
    
    UsingMiniMock miniMock;
    
    public void setUp() {
        miniMock = new UsingMiniMock();
    }
    
    interface BehaviourInterface2 {
        
    }
    
    public void shouldStoreMock() throws Exception {
        // can't think of a better way of doing it without
        // exposing the internals!
        Verify.that(!miniMock.containsMocks());
        
        miniMock.mock(BehaviourInterface2.class);
        
        Verify.that(miniMock.containsMocks());
      
    }
    
    public void shouldCreateConstraintForPrimitiveDoubleType() throws Exception {
        Constraint c = miniMock.eq(1.0);
        Verify.that(c.matches(new Double(1.0)));
    }
    
    public void shouldCreateConstraintForPrimitiveIntType() throws Exception {
        Constraint c = miniMock.eq(1);
        Verify.that(c.matches(new Integer(1)));
    }
    
    public void shouldCreateConstraintForPrimitiveCharType() throws Exception {
        Constraint c = miniMock.eq('c');
        Verify.that(c.matches(new Character('c')));
    }
    
    public void shouldCreateConstraintForPrimitiveLongType() throws Exception {
        Constraint c = miniMock.eq(1l);
        Verify.that(c.matches(new Long(1)));
    }
    
    public void shouldCreateConstraintForPrimitiveBooleanType() throws Exception {
        Constraint c = miniMock.eq(true);
        Verify.that(c.matches(Boolean.TRUE)); 
    }
    
    public void shouldCreateConstraintForPrimitiveFloatType() throws Exception {
        float f = 1;
        Constraint c = miniMock.eq(f);
        Verify.that(c.matches(new Float(1)));
    }
    
    public void shouldCreateConstraintForPrimitiveByteType() throws Exception {
        byte b = 1;
        Constraint c = miniMock.eq(b);
        Verify.that(c.matches(new Byte(b)));
    }
    
    public void shouldCreateConstraintForPrimitiveShortType() throws Exception {
        short s = 1;
        Constraint c = miniMock.eq(s);
        Verify.that(c.matches(new Short(s)));
    }
    
    interface BehaviourInterface1 {
        int getInt();
        long getLong();
        byte getByte();
        short getShort();
        double getDouble();
        float getFloat();
        char getChar();
        boolean getBoolean();
    }
    
    public void shouldCreateCorrectReturnValueForPrimitiveInt() throws Exception {
      Mock mock = miniMock.mock(BehaviourInterface1.class); 
      mock.stubs("getInt").will(miniMock.returnValue(1));
   
      int i = ((BehaviourInterface1)mock).getInt();
      Verify.equal(1, i);
    }
    
    public void shouldCreateCorrectReturnValueForPrimitiveLong() throws Exception {
        Mock mock = miniMock.mock(BehaviourInterface1.class); 
        mock.stubs("getLong").will(miniMock.returnValue(1l));
     
        long i = ((BehaviourInterface1)mock).getLong();
        Verify.equal(1, i);
    }
    
    public void shouldCreateCorrectReturnValueForPrimitiveShort() throws Exception {
        short s = 2;
        Mock mock = miniMock.mock(BehaviourInterface1.class); 
        mock.stubs("getShort").will(miniMock.returnValue(s));
     
        short i = ((BehaviourInterface1)mock).getShort();
        Verify.equal(s, i);
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveByte() throws Exception {
        byte b = 3;
        Mock mock = miniMock.mock(BehaviourInterface1.class); 
        mock.stubs("getByte").will(miniMock.returnValue(b));
     
        byte i = ((BehaviourInterface1)mock).getByte();
        Verify.equal(b, i);
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveDouble() throws Exception {
        double d = 4;
        Mock mock = miniMock.mock(BehaviourInterface1.class); 
        mock.stubs("getDouble").will(miniMock.returnValue(d));
     
        double i = ((BehaviourInterface1)mock).getDouble();
        Verify.equal(d, i, 0);
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveFloat() throws Exception {
        float f = 4;
        Mock mock = miniMock.mock(BehaviourInterface1.class); 
        mock.stubs("getFloat").will(miniMock.returnValue(f));
     
        float i = ((BehaviourInterface1)mock).getFloat();
        Verify.equal(f, i, 0);
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveChar() throws Exception {
        char c = 4;
        Mock mock = miniMock.mock(BehaviourInterface1.class); 
        mock.stubs("getChar").will(miniMock.returnValue(c));
     
        char i = ((BehaviourInterface1)mock).getChar();
        Verify.equal(c, i);
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveBoolean() throws Exception {
        boolean b = true;
        Mock mock = miniMock.mock(BehaviourInterface1.class); 
        mock.stubs("getBoolean").will(miniMock.returnValue(b));
     
        boolean i = ((BehaviourInterface1)mock).getBoolean();
        Verify.equal(b, i);
     }
    
    
 
}
