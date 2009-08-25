/*
 * Created on 18-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.minimock;

import org.jbehave.core.Ensure;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.mock.UsingMatchers;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class UsingMiniMockBehaviour extends UsingMatchers {
    
    UsingMiniMock m = new UsingMiniMock();
    
    public interface SomeType {}

    private Matcher containsMocks() {
        return new Matcher() {
            public boolean matches(Object arg) {
                return ((UsingMiniMock)arg).containsMocks();
            }
            public String toString() {
                return "UsingMiniMock instance containing mocks";
            }
        };
    }
    
    public void shouldStoreMock() throws Exception {
        ensureThat(m, not(containsMocks()));
        
        m.mock(SomeType.class);
        
        ensureThat(m, containsMocks());
      
    }
    
    public void shouldCreateMatcherForPrimitiveFloatingPointTypes() throws Exception {
        Matcher matchesFloatValue = m.eq(1.0);
        ensureThat(new Float(1.0), matchesFloatValue);
        ensureThat(new Double(1.0), matchesFloatValue);
    }
    
    public void shouldCreateMatcherForPrimitiveIntegerTypes() throws Exception {
        Matcher matchesIntTypeValue = m.eq(1);
        ensureThat(new Byte((byte)1), matchesIntTypeValue);
        ensureThat(new Short((short)1), matchesIntTypeValue);
        ensureThat(new Integer(1), matchesIntTypeValue);
        ensureThat(new Long((long)1), matchesIntTypeValue);
    }
    
    public void shouldCreateMatcherForPrimitiveCharType() throws Exception {
        Matcher c = m.eq('c');
        Ensure.that("matcher should match Character 'c'", c.matches(new Character('c')));
    }
    
    public void shouldCreateMatcherForPrimitiveLongType() throws Exception {
        Matcher c = m.eq(1l);
        Ensure.that(c.matches(new Long(1)));
    }
    
    public void shouldCreateMatcherForPrimitiveBooleanType() throws Exception {
        Matcher c = m.eq(true);
        Ensure.that(c.matches(Boolean.TRUE)); 
    }
    
    public void shouldCreateMatcherForPrimitiveFloatType() throws Exception {
        float f = 1;
        Matcher c = m.eq(f);
        Ensure.that(c.matches(new Float(1)));
    }
    
    public void shouldCreateMatcherForPrimitiveByteType() throws Exception {
        byte b = 1;
        Matcher c = m.eq(b);
        Ensure.that(c.matches(new Byte(b)));
    }
    
    public void shouldCreateMatcherForPrimitiveShortType() throws Exception {
        short s = 1;
        Matcher c = m.eq(s);
        Ensure.that(c.matches(new Short(s)));
    }
    
    public interface BehaviourInterface1 {
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
      Mock mock = m.mock(BehaviourInterface1.class); 
      mock.stubs("getInt").will(m.returnValue(1));
   
      int i = ((BehaviourInterface1)mock).getInt();
      ensureThat(i, eq(1));
    }
    
    public void shouldCreateCorrectReturnValueForPrimitiveLong() throws Exception {
        Mock mock = m.mock(BehaviourInterface1.class); 
        mock.stubs("getLong").will(m.returnValue(1l));
     
        long i = ((BehaviourInterface1)mock).getLong();
        ensureThat(i, eq(1));
    }
    
    public void shouldCreateCorrectReturnValueForPrimitiveShort() throws Exception {
        short s = 2;
        Mock mock = m.mock(BehaviourInterface1.class); 
        mock.stubs("getShort").will(m.returnValue(s));
     
        short i = ((BehaviourInterface1)mock).getShort();
        ensureThat(i, eq(s));
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveByte() throws Exception {
        byte b = 3;
        Mock mock = m.mock(BehaviourInterface1.class); 
        mock.stubs("getByte").will(m.returnValue(b));
     
        byte i = ((BehaviourInterface1)mock).getByte();
        ensureThat(i, eq(b));
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveDouble() throws Exception {
        double d = 4;
        Mock mock = m.mock(BehaviourInterface1.class); 
        mock.stubs("getDouble").will(m.returnValue(d));
     
        double i = ((BehaviourInterface1)mock).getDouble();
        ensureThat(i, eq(d, 0));
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveFloat() throws Exception {
        float f = 4;
        Mock mock = m.mock(BehaviourInterface1.class); 
        mock.stubs("getFloat").will(m.returnValue(f));
     
        float i = ((BehaviourInterface1)mock).getFloat();
        ensureThat(i, eq(f, 0));
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveChar() throws Exception {
        char c = 4;
        Mock mock = m.mock(BehaviourInterface1.class); 
        mock.stubs("getChar").will(m.returnValue(c));
     
        char i = ((BehaviourInterface1)mock).getChar();
        ensureThat(i, eq(c));
     }
    
    public void shouldCreateCorrectReturnValueForPrimitiveBoolean() throws Exception {
        boolean b = true;
        Mock mock = m.mock(BehaviourInterface1.class); 
        mock.stubs("getBoolean").will(m.returnValue(b));
     
        boolean i = ((BehaviourInterface1)mock).getBoolean();
        ensureThat(i, eq(b));
     }
}
