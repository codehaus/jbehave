/**
 * 
 */
package jbehave.story.codegen.parser;

import jbehave.story.codegen.domain.ScenarioDetails;
import jbehave.story.codegen.domain.StoryDetails;
import jbehave.story.codegen.sablecc.analysis.DepthFirstAdapter;
import jbehave.story.codegen.sablecc.node.ABenefit;
import jbehave.story.codegen.sablecc.node.AFeature;
import jbehave.story.codegen.sablecc.node.APhrase;
import jbehave.story.codegen.sablecc.node.ARole;
import jbehave.story.codegen.sablecc.node.AScenario;
import jbehave.story.codegen.sablecc.node.AScenarioTitle;
import jbehave.story.codegen.sablecc.node.ASpaceWordOrSpace;
import jbehave.story.codegen.sablecc.node.ATitle;
import jbehave.story.codegen.sablecc.node.AWordWordOrSpace;


class StoryDetailsBuilder extends DepthFirstAdapter {
	StoryDetails story = new StoryDetails();
	private StringBuffer phrase;
//	private StringBuffer scenarioTitle;
	private ScenarioDetails scenario;
	
	// the story 
	
	// summary stuff

	public void outATitle(ATitle node) {
		story.name = phrase.toString();
	}

	public void outARole(ARole node) {
		story.role = phrase.toString();
	}

	public void outAFeature(AFeature node) {
		story.feature = phrase.toString();
	}

	public void outABenefit(ABenefit node) {
		story.benefit = phrase.toString();
	}
	
	// scenarios

	public void inAScenario(AScenario node) {
		scenario = new ScenarioDetails();
		story.addScenario(scenario);
	}
	
	public void outAScenarioTitle(AScenarioTitle node) {
		scenario.name = phrase.toString();
	}
	
	// phrase processing

	public void inAPhrase(APhrase node) {
		phrase = new StringBuffer();
	}
	
	public void outASpaceWordOrSpace(ASpaceWordOrSpace node) {
		phrase.append(' ');
	}

	public void outAWordWordOrSpace(AWordWordOrSpace node) {
		phrase.append(node.getWord().getText());
	}

	public StoryDetails getStoryDetails() {
		return story;
	}
}