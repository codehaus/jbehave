/**
 * 
 */
package com.thoughtworks.jbehave.extensions.story.codegen;

import com.thoughtworks.jbehave.extensions.story.codegen.domain.StoryDetails;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.analysis.DepthFirstAdapter;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.ABenefit;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.AFeature;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.APhrase;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.ARole;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.ASpaceWordOrSpace;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.AStory;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.ATitle;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.AWordWordOrSpace;

class StoryDetailsBuilder extends DepthFirstAdapter {
	StoryDetails storyDetails;
	private String title = "";
	private String role = "";
	private String feature = "";
	private String benefit = "";
	private StringBuffer phrase;
	
	public void inAPhrase(APhrase node) {
		phrase = new StringBuffer();
	}
	
	public void outAStory(AStory node) {
		storyDetails = new StoryDetails(title, role, feature, benefit);
	}

	public void outARole(ARole node) {
		role = phrase.toString();
	}

	public void outAFeature(AFeature node) {
		feature = phrase.toString();
	}

	public void outABenefit(ABenefit node) {
		benefit = phrase.toString();
	}

	public void outATitle(ATitle node) {
		title = phrase.toString();
	}

	public void outASpaceWordOrSpace(ASpaceWordOrSpace node) {
		phrase.append(' ');
	}

	public void outAWordWordOrSpace(AWordWordOrSpace node) {
		phrase.append(node.getWord().getText());
	}

	public StoryDetails getStoryDetails() {
		return storyDetails;
	}
	
}