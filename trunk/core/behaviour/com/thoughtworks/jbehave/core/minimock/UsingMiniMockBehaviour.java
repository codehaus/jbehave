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
 */
public class UsingMiniMockBehaviour {
    public void shouldStoreMock() throws Exception {
        // given...
        
        Verify.pending("Should Store a mock");
        // expect...
        // when...
        // then...
    }
    
    public void shouldCreateConstraintForPrimitiveDoubleType() throws Exception {
        UsingMiniMock miniMock = new UsingMiniMock();
        Constraint c = miniMock.eq(1.0);
        Verify.that(c.matches(new Double(1.0)));
    }
    
    public void shouldCreateConstraintForPrimitiveIntType() throws Exception {
        UsingMiniMock miniMock = new UsingMiniMock();
        Constraint c = miniMock.eq(1);
        Verify.that(c.matches(new Integer(1)));
    }
    
    public void shouldCreateConstraintForPrimitiveCharType() throws Exception {
        UsingMiniMock miniMock = new UsingMiniMock();
        Constraint c = miniMock.eq('c');
        Verify.that(c.matches(new Character('c')));
    }
    
    public void shouldCreateConstraintForPrimitiveLongType() throws Exception {
        UsingMiniMock miniMock = new UsingMiniMock();
        Constraint c = miniMock.eq(1l);
        Verify.that(c.matches(new Long(1)));
    }
    
    public void shouldCreateConstraintForPrimitiveBooleanType() throws Exception {
        UsingMiniMock miniMock = new UsingMiniMock();
        Constraint c = miniMock.eq(true);
        Verify.that(c.matches(Boolean.TRUE)); 
    }
}
