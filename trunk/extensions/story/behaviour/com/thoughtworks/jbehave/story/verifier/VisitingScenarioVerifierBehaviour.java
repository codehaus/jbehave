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

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Environment;
import com.thoughtworks.jbehave.story.domain.Expectation;
import com.thoughtworks.jbehave.story.domain.Scenario;
import com.thoughtworks.jbehave.story.result.ScenarioResult;
import com.thoughtworks.jbehave.story.visitor.Visitor;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class VisitingScenarioVerifierBehaviour extends UsingMiniMock {
    private VisitingScenarioVerifier verifier;
    private Mock scenario;
    private Environment environmentStub;
    
    public void setUp() {
        environmentStub = (Environment)stub(Environment.class);
        verifier = new VisitingScenarioVerifier("story", environmentStub);
        scenario = mock(Scenario.class);
    }
    
    public void shouldDispatchItselfAsVisitorToScenario() throws Exception {
        // given...
        // expect...
        scenario.expects("accept").with(verifier);
        
        // when...
        verifier.verify((Scenario)scenario);
        
        // verify...
        verifyMocks();
    }
    
    public void shouldVerifyExpectationInEnvironment() throws Exception {
        // given...
        Mock expectation = mock(Expectation.class);

        // expect...
        expectation.expects("verify").with(same(environmentStub));
        
        // when...
        verifier.visitExpectation((Expectation)expectation);
        
        // verify...
        verifyMocks();
    }
        
    public void shouldReturnResultUsingMocksWhenScenarioSucceedsButExpectationUsesMocks() throws Exception {
        // expect...
        Mock expectation = mock(Expectation.class);
        expectation.expects("containsMocks").will(returnValue(true));
        scenario.expects("accept").will(visitExpectation((Expectation) expectation));
        
        // when...
        ScenarioResult result = verifier.verify((Scenario)scenario);
        
        // verify...
        Verify.that("should have used mocks", result.usedMocks());
    }
    
    /** Custom invocation handler so a Scenario can pass a component to the visitor */
    private InvocationHandler visitExpectation(final Expectation expectation) {
        return new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("accept")) {
                    Visitor visitor = (Visitor) args[0];
                    visitor.visitExpectation(expectation);
                }
                return null;
            }
        };
    }
}
