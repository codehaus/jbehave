/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.listener;

import java.io.StringWriter;

import com.thoughtworks.jbehave.core.verify.Verify;
import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.domain.MockableScenario;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.UnimplementedException;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TextScenarioListenerBehaviour extends UsingJMock {
    private StringWriter writer;
    private ScenarioListener listener;

    public void setUp() throws Exception {
        writer = new StringWriter();
        listener = new TextScenarioListener(writer);
    }

    public void shouldRenderSuccessSymbolForSuccessfulScenario() throws Exception {
        // given...
        Scenario scenario = (Scenario) stub(MockableScenario.class);
        
        // when...
        listener.scenarioSucceeded(scenario);
        
        // verify...
        Verify.equal(TextScenarioListener.SUCCESS, writer.toString());
    }

    public void shouldRenderFailureSymbolForFailedScenario() throws Exception {
        // given...
        Scenario scenario = (Scenario) stub(MockableScenario.class);
        
        // when...
        listener.scenarioFailed(scenario, new Exception());
        
        // verify...
        Verify.equal(TextScenarioListener.FAILURE, writer.toString());
    }

    public void shouldRenderUnimplementedSymbolForUnimplementedScenario() throws Exception {
        // given...
        Scenario scenario = (Scenario)stub(MockableScenario.class);
        
        // when...
        listener.scenarioUnimplemented(scenario, new UnimplementedException());
        
        // verify...
        Verify.equal(TextScenarioListener.UNIMPLEMENTED, writer.toString());
    }
}
