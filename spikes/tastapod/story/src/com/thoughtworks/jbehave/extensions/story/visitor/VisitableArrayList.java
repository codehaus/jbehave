/*
 * Created on 04-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitableArrayList extends ArrayList implements Visitable {

    public VisitableArrayList() {
        super();
    }
    
    public VisitableArrayList(int initialCapacity) {
        super(initialCapacity);
    }
    
    public VisitableArrayList(Collection c) {
        super(c);
    }

    public void accept(Visitor visitor) {
        for (Iterator i = iterator(); i.hasNext();) {
            ((Visitable) i.next()).accept(visitor);
        }
    }
}
