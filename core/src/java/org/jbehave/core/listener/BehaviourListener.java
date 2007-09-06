/*
 * Created on 24-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.listener;

import org.jbehave.core.behaviour.Behaviour;
import org.jbehave.core.result.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author Mauro Talevi
 */
public interface BehaviourListener {
    void before(Behaviour behaviour);
    void gotResult(Result result);
    void after(Behaviour behaviour);
    void printReport();
    boolean hasBehaviourFailures();
    
    /** Null object implementation for {@link BehaviourListener} */
    BehaviourListener NULL = new BehaviourListener() {
        public void before(Behaviour behaviour) {}
        public void gotResult(Result result) {}
        public void after(Behaviour behaviour) {}
        public void printReport() {}
        public boolean hasBehaviourFailures() {
            return false;
        }        
    };
}
