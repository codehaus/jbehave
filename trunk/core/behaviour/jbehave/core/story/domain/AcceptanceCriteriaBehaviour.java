package jbehave.core.story.domain;

import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.visitor.Visitor;

public class AcceptanceCriteriaBehaviour extends UsingMiniMock {

	public void shouldVisitorVisitScenariosInOrder() {
		Mock visitor = mock(Visitor.class);
		Mock scenarioA = mock(Scenario.class);
		Mock scenarioB = mock(Scenario.class);
		
		AcceptanceCriteria criteria = new AcceptanceCriteria();
		
		visitor.expects("visitScenario").with(scenarioA);
		scenarioA.expects("accept").with(visitor);
		scenarioB.expects("accept").with(visitor);
		
		criteria.addScenario((Scenario) scenarioA);
		criteria.addScenario((Scenario) scenarioB);
		criteria.accept((Visitor) visitor);
	}
}
