/*
 * Created on 24-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.behaviour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.invoker.MethodInvoker;
import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodVerifier implements Visitor {
    
    private final MethodInvoker invoker;
    private final List listeners = new ArrayList();

    public BehaviourMethodVerifier(MethodInvoker invoker) {
        this.invoker = invoker;
    }
    
    public void addListener(ResultListener listener) {
        listeners.add(listener);
    }
    
    public void visit(Visitable visitable) {
        if (visitable instanceof BehaviourMethod) {
            Result result = invoker.invoke((BehaviourMethod) visitable);
            for (Iterator i = listeners.iterator(); i.hasNext();) {
                ((ResultListener) i.next()).gotResult(result);
            }
        }
    }
}
