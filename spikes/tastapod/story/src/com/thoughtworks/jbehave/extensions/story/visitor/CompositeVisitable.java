/*
 * Created on 04-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public abstract class CompositeVisitable implements Visitable {

    protected final List visitables;

    public CompositeVisitable(List visitables) {
        this.visitables = visitables;
    }

    public CompositeVisitable() {
        this(new ArrayList());
    }
    
    /**
     * Pass self into the visitor.
     * 
     * This has to be abstract because the static type determines
     * which <tt>visitXxx</tt> method to call.
     */ 
    protected abstract void visitSelf(Visitor visitor);

    public void accept(Visitor visitor) {
        visitSelf(visitor);
        for (Iterator i = visitables.iterator(); i.hasNext();) {
            ((Visitable) i.next()).accept(visitor);
        }
    }

    public void add(Visitable visitable) {
        visitables.add(visitable);
    }
}
