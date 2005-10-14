/*
 * Created on 23-Dec-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.verifier;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.Ensure;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Outcome;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.result.ScenarioResult;
import com.thoughtworks.jbehave.story.visitor.Visitor;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class VisitingScenarioVerifierBehaviour extends UsingMiniMock {
    private VisitingScenarioVerifier verifier;
    private Mock scenario;
    private World worldStub;
    
    public void setUp() {
        worldStub = (World)stub(World.class);
        verifier = new VisitingScenarioVerifier("story", worldStub);
        scenario = mock(Scenario.class);
    }
    
    public void shouldDispatchItselfAsVisitorToScenario() throws Exception {
        // given...
        // expect...
        scenario.expects("accept").with(verifier);
        
        // when...
        verifier.verify((Scenario)scenario);
    }
    
    public void shouldVerifyExpectationInEnvironment() throws Exception {
        // given...
        Mock outcome = mock(Outcome.class);

        // expect...
        outcome.expects("verify").with(sameInstanceAs(worldStub));
        
        // when...
        verifier.visitOutcome((Outcome)outcome);
    }
        
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButExpectationUsesMocks() throws Exception {
        // expect...
        Mock outcome = mock(Outcome.class);
        outcome.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitExpectation((Outcome) outcome));
        
        // when...
        ScenarioResult result = verifier.verify((Scenario)scenario);
        
        // verify...
        Ensure.that("should have used mocks", result.usedMocks());
    }
    
    /** Custom invocation handler so a Scenario can pass a component to the visitor */
    private InvocationHandler visitExpectation(final Outcome outcome) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("accept")) {
                    Visitor visitor = (Visitor) args[0];
                    visitor.visitOutcome(outcome);
                }
                return null;
            }
        };
    }
}
