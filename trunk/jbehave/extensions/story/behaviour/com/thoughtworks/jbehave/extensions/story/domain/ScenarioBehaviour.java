/*
 * Created on 29-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import org.jmock.core.stub.DefaultResultStub;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.listener.ScenarioListener;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ScenarioBehaviour extends UsingJMock {

    public void shouldPassItselfIntoVisitor() throws Exception {
        // given...
        Scenario scenario = new Scenario("", null, null, null);
        Mock visitor = new Mock(Visitor.class);
        
        // expect...
        visitor.expects(once()).method("visitScenario").with(same(scenario));
        visitor.setDefaultStub(new DefaultResultStub());

        // when...
        scenario.accept((Visitor)visitor.proxy());
        
        // then...
        verifyMocks();
    }
    
    public void shouldTellComponentsToAcceptVisitorInCorrectOrder() throws Exception {
        // given...
        Visitor visitor = (Visitor) stub(Visitor.class);
        
        Mock context = new Mock(MockableContext.class);
        Mock outcome = new Mock(MockableOutcome.class);
        Mock event = new Mock(Event.class);

        context.expects(once()).method("accept").with(eq(visitor));
        outcome.expects(once()).method("accept").with(same(visitor)).after(context, "accept").id("acceptBeforeEvent");
        event.expects(once()).method("accept").with(same(visitor)).after(outcome, "acceptBeforeEvent");
        outcome.expects(once()).method("accept").with(same(visitor)).after(event, "accept");
        
        Scenario scenario = new Scenario("", null, (Context)context.proxy(), (Event)event.proxy(), (Outcome) outcome.proxy());
        
        // when...
        scenario.accept(visitor);
    }
    
    public void shouldInformScenarioListenerAfterSuccessfulVisit() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Outcome outcomeStub = (Outcome)stub(MockableOutcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        
        Mock listener = new Mock(ScenarioListener.class);
        Scenario scenario = new Scenario("", null, eventStub, outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        listener.expects(once()).method("scenarioSucceeded").with(same(scenario));

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenContextContainsUnimplementedGiven() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Outcome outcomeStub = (Outcome)stub(MockableOutcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new UnimplementedException();
        
        Mock context = new Mock(MockableContext.class);
        Mock listener = new Mock(ScenarioListener.class);

        Scenario scenario = new Scenario("", null, (Context) context.proxy(), eventStub, outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        context.expects(once()).method("accept").with(same(visitorStub)).will(throwException(cause));
        listener.expects(once()).method("scenarioUnimplemented").with(same(scenario), same(cause));

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenOutcomeContainsUnimplementedExpectation() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new UnimplementedException();
        
        Mock outcome = new Mock(MockableOutcome.class);
        Mock listener = new Mock(ScenarioListener.class);

        Scenario scenario = new Scenario("", null, eventStub, (Outcome) outcome.proxy());
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        outcome.expects(atLeastOnce()).method("accept").with(anything()).will(throwException(cause));
        listener.expects(once()).method("scenarioUnimplemented").with(same(scenario), same(cause));

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenEventIsUnimplemented() throws Exception {
        // given...
        Outcome outcomeStub = (Outcome)stub(MockableOutcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new UnimplementedException();
        
        Mock event = new Mock(Event.class);
        Mock listener = new Mock(ScenarioListener.class);

        Scenario scenario = new Scenario("", null, (Event) event.proxy(), outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        event.expects(once()).method("accept").with(same(visitorStub)).will(throwException(cause));
        listener.expects(once()).method("scenarioUnimplemented").with(same(scenario), same(cause));

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenContextPropagatesAnException() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Outcome outcomeStub = (Outcome)stub(MockableOutcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new RuntimeException();
        
        Mock context = new Mock(MockableContext.class);
        Mock listener = new Mock(ScenarioListener.class);

        Scenario scenario = new Scenario("", null, (Context) context.proxy(), eventStub, outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        context.expects(once()).method("accept").with(same(visitorStub)).will(throwException(cause));
        listener.expects(once()).method("scenarioFailed").with(same(scenario), same(cause));

        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenOutcomePropagatesAnException() throws Exception {
        // given...
        Event eventStub = (Event) stub(Event.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new RuntimeException();
        
        Mock outcome = new Mock(MockableOutcome.class);
        Mock listener = new Mock(ScenarioListener.class);

        Scenario scenario = new Scenario("", null, eventStub, (Outcome) outcome.proxy());
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        outcome.expects(once()).method("accept").with(same(visitorStub)).will(throwException(cause));
        listener.expects(once()).method("scenarioFailed").with(same(scenario), same(cause));
        
        // when...
        scenario.accept(visitorStub);
    }
    
    public void shouldInformScenarioListenerWhenEventThrowsAnException() throws Exception {
        // given...
        Outcome outcomeStub = (Outcome)stub(MockableOutcome.class);
        Visitor visitorStub = (Visitor)stub(Visitor.class);
        Exception cause = new RuntimeException();
        
        Mock event = new Mock(Event.class);
        Mock listener = new Mock(ScenarioListener.class);

        Scenario scenario = new Scenario("", null, (Event) event.proxy(), outcomeStub);
        scenario.setListener((ScenarioListener)listener.proxy());
        
        // expect...
        event.expects(once()).method("accept").with(same(visitorStub)).will(throwException(cause));
        listener.expects(once()).method("scenarioFailed").with(same(scenario), same(cause));

        // when...
        scenario.accept(visitorStub);
        
        // then...
        verifyMocks();
    }
}
