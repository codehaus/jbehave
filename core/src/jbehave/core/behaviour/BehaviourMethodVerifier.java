/*
 * Created on 24-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.core.listener.ResultListener;
import jbehave.core.result.Result;
import jbehave.core.visitor.VisitorSupport;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodVerifier extends VisitorSupport {
    
    private final List listeners = new ArrayList();

    public void addListener(ResultListener listener) {
        listeners.add(listener);
    }
    
    public void visitBehaviourMethod(BehaviourMethod behaviourMethod) {
        Result result = behaviourMethod.invoke();
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ((ResultListener) i.next()).gotResult(result);
        }
    }
}
