/*
 * Created on 28-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.jmock.UsingJMock;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class AcceptanceCriteriaBehaviour extends UsingJMock {
    
    public void shouldTellComponentsToAcceptVisitorInCorrectOrder() throws Exception {
        // given...
        AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria();
        Mock scenario1 = new Mock(MockableScenario.class, "scenario1");
        Mock scenario2 = new Mock(MockableScenario.class, "scenario2");
        Visitor visitor = (Visitor) stub(Visitor.class);

        // expect...
        scenario1.expects(once()).method("accept").with(same(visitor));
        scenario2.expects(once()).method("accept").with(same(visitor)).after(scenario1, "accept");
        
        // when...
        acceptanceCriteria.addScenario((Scenario) scenario1.proxy());
        acceptanceCriteria.addScenario((Scenario) scenario2.proxy());
        acceptanceCriteria.accept(visitor);
    }

}
