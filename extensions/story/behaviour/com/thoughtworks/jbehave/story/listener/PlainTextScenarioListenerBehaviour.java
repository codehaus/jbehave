/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.listener;

import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.listener.PlainTextListener;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.story.result.ScenarioResult;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextScenarioListenerBehaviour extends PlainTextListenerBehaviour {

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
        ensure(writer.toString(), eq(ScenarioResult.USED_MOCKS.symbol()));
    }
    
    public void shouldSummarizeScenarioThatUsesMocks() throws Exception {
        // given...
        Result usesMocks = new ScenarioResult("scenario", "Container", ScenarioResult.USED_MOCKS);
        
        // when...
        listener.gotResult(usesMocks);
        listener.printReport();
        
        // then...
        verifyOutputContains("\nUsed mocks: 1");
    }
}
