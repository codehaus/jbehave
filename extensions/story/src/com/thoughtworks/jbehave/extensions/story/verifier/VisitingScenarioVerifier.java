/*
 * Created on 16-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.verifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitingScenarioVerifier implements ScenarioVerifier, Visitor {
    private final List listeners = new ArrayList();
    private final Environment environment;
    private boolean beforeEvent = true;

    public VisitingScenarioVerifier(Environment environment) {
        this.environment = environment;
    }
    
    public void visit(Visitable visitable) {
        try {
            if (visitable instanceof Given) {
                visitGiven((Given) visitable);
            }
            else if (visitable instanceof Event) {
                visitEvent((Event) visitable);
                beforeEvent = false;
            }
            else if (visitable instanceof Expectation) {
                visitExpectation((Expectation) visitable);
            }
        }
        catch (VerificationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new NestedVerificationException("Execution failed for " + visitable, e);
        }
    }
    
    private void visitGiven(Given given) throws Exception {
        given.setUp(environment);
    }
    
    private void visitEvent(Event event) throws Exception {
        event.occurIn(environment);
    }
    
    private void visitExpectation(Expectation expectation) {
        if (beforeEvent) {
            expectation.setExpectationIn(environment);
        }
        else {
            expectation.verify(environment);
        }
    }

    public void verifyScenario(Scenario scenario) {
        scenario.accept(this);
        Result result = new Result(scenario.getDescription(), Result.SUCCEEDED);
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ResultListener listener = (ResultListener) i.next();
            listener.gotResult(result);
        }
    }

    public void addListener(ResultListener listener) {
        listeners.add(listener);
    }
}
