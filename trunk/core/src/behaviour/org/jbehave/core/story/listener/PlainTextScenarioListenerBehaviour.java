/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.listener;

import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.listener.PlainTextListener;
import org.jbehave.core.listener.PlainTextListenerBehaviourSupport;
import org.jbehave.core.result.Result;
import org.jbehave.core.story.listener.PlainTextScenarioListener;
import org.jbehave.core.story.result.ScenarioResult;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextScenarioListenerBehaviour extends PlainTextListenerBehaviourSupport {

    public void setUp() throws Exception {
        super.setUp();
    }

    protected PlainTextListener newPlainTextListener() {
        return new PlainTextScenarioListener(writer, timer);
    }

    protected Result newSuccessResult() {
        return new ScenarioResult("scenario", "container", ScenarioResult.SUCCEEDED);
    }

    protected Result newFailureResult() {
        return new ScenarioResult("Scenario", "container", new VerificationException("oops"));
    }

    protected Result newExceptionResult() {
        return new ScenarioResult("Scenario", "container", new Exception("oops"));
    }

    protected Result newPendingResult() {
        return new ScenarioResult("Scenario", "container", new PendingException());
    }

    public void shouldRenderMockSymbolForScenarioThatUsedMocks() throws Exception {
        // given...
        
        // when...
        listener.gotResult(new ScenarioResult("scenario", "container", ScenarioResult.USED_MOCKS));
        
        // then...
        ensureThat(writer.toString(), eq(ScenarioResult.USED_MOCKS.symbol()));
    }
    
    public void shouldSummarizeScenarioThatUsesMocks() throws Exception {
        // given...
        Result usesMocks = new ScenarioResult("scenario", "Container", ScenarioResult.USED_MOCKS);
        
        // when...
        listener.gotResult(usesMocks);
        listener.printReport();
        
        // then...
        ensureThat(writer, contains("\nUsed mocks: 1"));
    }
}
