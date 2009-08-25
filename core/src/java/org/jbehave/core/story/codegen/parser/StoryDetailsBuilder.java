/**
 * 
 */
package org.jbehave.core.story.codegen.parser;

import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.story.codegen.sablecc.analysis.DepthFirstAdapter;
import org.jbehave.core.story.codegen.sablecc.node.ABenefit;
import org.jbehave.core.story.codegen.sablecc.node.AContext;
import org.jbehave.core.story.codegen.sablecc.node.AEvent;
import org.jbehave.core.story.codegen.sablecc.node.AFeature;
import org.jbehave.core.story.codegen.sablecc.node.AOutcome;
import org.jbehave.core.story.codegen.sablecc.node.APackage;
import org.jbehave.core.story.codegen.sablecc.node.APhrase;
import org.jbehave.core.story.codegen.sablecc.node.ARole;
import org.jbehave.core.story.codegen.sablecc.node.AScenario;
import org.jbehave.core.story.codegen.sablecc.node.AScenarioTitle;
import org.jbehave.core.story.codegen.sablecc.node.ASpaceWordOrSpace;
import org.jbehave.core.story.codegen.sablecc.node.ATitle;
import org.jbehave.core.story.codegen.sablecc.node.AWordWordOrSpace;


class StoryDetailsBuilder extends DepthFirstAdapter {
	StoryDetails story = new StoryDetails();
	private StringBuffer phrase;
	private ScenarioDetails scenario;
	
	// the story 	

	// summary stuff
	public void outATitle(ATitle node) {
		story.name = phrase.toString();
	}

    public void outAPackage(APackage node) {
        story.rootPackage = node.getPackageName().getText();
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

    public void outAContext(AContext node) {
        scenario.context.givens.add(phrase.toString());
    }

    public void outAEvent(AEvent node) {
        scenario.event.name = phrase.toString();
    }

    public void outAOutcome(AOutcome node) {
        scenario.outcome.outcomes.add(phrase.toString());
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