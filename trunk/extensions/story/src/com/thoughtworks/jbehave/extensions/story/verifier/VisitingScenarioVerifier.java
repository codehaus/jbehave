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

import com.thoughtworks.jbehave.core.UsingMocks;
import com.thoughtworks.jbehave.core.exception.NestedVerificationException;
import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.minilog.Log;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Event;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class VisitingScenarioVerifier implements ScenarioVerifier, Visitor {
    protected final Log log = Log.getLog(this);
    private final List listeners = new ArrayList();
    private final Environment environment;
    private boolean beforeEvent = true;
    private boolean usesMocks = false;

    public VisitingScenarioVerifier(Environment environment) {
        this.environment = environment;
    }
    
    public void visit(Visitable visitable) {
        try {
            // accept the visitor
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
        catch (Exception e) {
            throw new NestedVerificationException("Execution failed for " + visitable, e);
        }
    }
    
    private void visitGiven(Given given) throws Exception {
        given.setUp(environment);
        checkForMocks(given);
    }
    
    private void visitEvent(Event event) throws Exception {
        event.occurIn(environment);
        checkForMocks(event);
    }
    
    private void visitExpectation(Expectation expectation) {
        if (beforeEvent) {
            expectation.setExpectationIn(environment);
        }
        else {
            expectation.verify(environment);
        }
        checkForMocks(expectation);
    }
    
    private void checkForMocks(UsingMocks component) {
        if (component.containsMocks()) {
            log.debug(component + " uses mocks!");
            usesMocks = true;
        }
    }

    public void verifyScenario(Scenario scenario) {
        Throwable cause = null;
        try {
            scenario.accept(this);
        }
        catch (NestedVerificationException e) {
            cause = e.getCause();
        }
        
        final Result result;
        if (cause == null && usesMocks) {
            result = new ScenarioResult(scenario.getDescription(), ScenarioResult.USES_MOCKS);
        }
        else {
            result = new ScenarioResult(scenario.getDescription(), cause);
        }
        for (Iterator i = listeners.iterator(); i.hasNext();) {
            ResultListener listener = (ResultListener) i.next();
            listener.gotResult(result);
        }
    }

    public void addListener(ResultListener listener) {
        listeners.add(listener);
    }
}
