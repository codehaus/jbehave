/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.listener.ScenarioListener;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SimpleScenarioBehaviour extends UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Event dummyEvent = (Event) stub(Event.class);
        Outcome dummyOutcome = (Outcome) stub(Outcome.class);
        Scenario scenario = new SimpleScenario("", null, dummyEvent, dummyOutcome);
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expects(once()).method("visitScenario").with(same(scenario));

        // when...
        scenario.accept((Visitor)visitor.proxy());
    }
    
    public void shouldPassVisitorToComponentsInCorrectOrder() throws Exception {
        // given...
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        Mock context = new Mock(Context.class);
        Mock outcome = new Mock(Outcome.class);
        Mock event = new Mock(Event.class);

        // expect...
        context.expectsOnce("accept", visitor);
        outcome.expectsOnce("accept", visitor).after(context, "accept").id("acceptBeforeEvent");
        event.expectsOnce("accept", visitor).after(outcome, "acceptBeforeEvent");
        outcome.expectsOnce("accept", visitor).after(event, "accept");
        
        Scenario scenario = new SimpleScenario("", null, (Context)context.proxy(), (Event)event.proxy(), (Outcome) outcome.proxy());
        
        // when...
        scenario.accept(visitor);
    }
    
    public void shouldInformScenarioListenerAfterSuccessfulVisit() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Outcome outcomeStub = (Outcome) stub(Outcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        
        Mock listener = new Mock(ScenarioListener.class);
        SimpleScenario scenario = new SimpleScenario("", null, eventStub, outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        listener.expectsOnce("scenarioSucceeded", scenario);

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenContextContainsUnimplementedGiven() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Outcome outcomeStub = (Outcome)stub(Outcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new UnimplementedException();
        
        Mock context = new Mock(Context.class);
        Mock listener = new Mock(ScenarioListener.class);

        SimpleScenario scenario = new SimpleScenario("", null, (Context) context.proxy(), eventStub, outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        context.expectsOnce("accept", visitorStub).will(throwException(cause));
        listener.expectsOnce("scenarioUnimplemented", scenario, cause);

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenOutcomeContainsUnimplementedExpectation() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new UnimplementedException();
        
        Mock outcome = new Mock(Outcome.class);
        Mock listener = new Mock(ScenarioListener.class);

        SimpleScenario scenario = new SimpleScenario("", null, eventStub, (Outcome) outcome.proxy());
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        outcome.expects(atLeastOnce()).method("accept").with(anything()).will(throwException(cause));
        listener.expectsOnce("scenarioUnimplemented", scenario, cause);

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenEventIsUnimplemented() throws Exception {
        // given...
        Outcome outcomeStub = (Outcome)stub(Outcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new UnimplementedException();
        
        Mock event = new Mock(Event.class);
        Mock listener = new Mock(ScenarioListener.class);

        SimpleScenario scenario = new SimpleScenario("", null, (Event) event.proxy(), outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        event.expectsOnce("accept", visitorStub).will(throwException(cause));
        listener.expectsOnce("scenarioUnimplemented", scenario, cause);

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenContextPropagatesAnException() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Outcome outcomeStub = (Outcome)stub(Outcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new Exception();
        
        Mock context = new Mock(Context.class);
        Mock listener = new Mock(ScenarioListener.class);

        SimpleScenario scenario = new SimpleScenario("", null, (Context) context.proxy(), eventStub, outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        context.expectsOnce("accept", visitorStub).will(throwException(cause));
        listener.expectsOnce("scenarioFailed", scenario, cause);

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenOutcomePropagatesAnExpectation() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new Exception();
        
        Mock outcome = new Mock(Outcome.class);
        Mock listener = new Mock(ScenarioListener.class);

        SimpleScenario scenario = new SimpleScenario("", null, eventStub, (Outcome) outcome.proxy());
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        outcome.expectsOnce("accept", visitorStub).will(throwException(cause));
        listener.expectsOnce("scenarioFailed", scenario, cause);
        
        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenEventThrowsAnExpectation() throws Exception {
        // given...
        Outcome outcomeStub = (Outcome)stub(Outcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new Exception();
        
        Mock event = new Mock(Event.class);
        Mock listener = new Mock(ScenarioListener.class);

        SimpleScenario scenario = new SimpleScenario("", null, (Event) event.proxy(), outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        event.expectsOnce("accept", visitorStub).will(throwException(cause));
        listener.expectsOnce("scenarioFailed", scenario, cause);

        // when...
        scenario.accept(visitorStub);
    }
}
