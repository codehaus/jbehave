/*
 * Created on 30-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.Visitable;
import com.thoughtworks.jbehave.core.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class CompositeVisitable implements Visitable {

    protected final List components = new ArrayList();

    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Iterator i = components.iterator(); i.hasNext();) {
            ((Visitable) i.next()).accept(visitor);
        }
    }

    public void add(Visitable visitable) {
        components.add(visitable);
    }

    public void addAll(Collection visitables) {
        components.addAll(visitables);
    }
    
    public List components() {
        return Collections.unmodifiableList(components);
    }
    
    public Iterator iterator() {
        return components().iterator();
    }
}
