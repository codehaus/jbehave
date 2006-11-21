/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import java.util.Iterator;

import jbehave.core.story.visitor.CompositeVisitableUsingMiniMock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Givens extends CompositeVisitableUsingMiniMock implements Given {
    public static final Givens NULL = new Givens(new Given[0]);
    private final Given[] givens;
    
    /** Constructor with a bunch of givens */
    public Givens(Given[] givens) {
		super(givens);
        this.givens = givens;
    }

    public Givens(Given given) {
        this(new Given[] {given});
    }

    public Givens(Given given1, Given given2) {
        this(new Given[] {given1, given2});
    }

    public Givens(Given given1, Given given2, Given given3) {
        this(new Given[] {given1, given2, given3});
    }

	public void setUp(final World world) {
        for(int i = 0; i < givens.length; i++) {
            givens[i].setUp(world);
        }
	}

    public void tidyUp(World world) {
        for(int i = givens.length - 1; i > -1; i--) {
            givens[i].tidyUp(world);
        }
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Givens ");
        for ( int i = 0; i < givens.length; i++ ){
            buffer.append(givens[i].getClass().getName());
            if ( i < givens.length - 1 ){
                buffer.append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
