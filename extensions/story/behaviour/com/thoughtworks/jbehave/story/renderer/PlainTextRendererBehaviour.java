/*
 * Created on 06-Jan-2005
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.story.renderer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.util.ConvertCase;
import com.thoughtworks.jbehave.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.story.domain.Context;
import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.domain.EventUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.ExpectationUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.GivenUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Outcomes;
import com.thoughtworks.jbehave.story.domain.ScenarioUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Story;
import com.thoughtworks.jbehave.story.renderer.PlainTextRenderer;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class PlainTextRendererBehaviour {

	public void shouldRenderStoryWhenVisitingStory() {
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteStream);
		
		PlainTextRenderer renderer = new PlainTextRenderer(printStream);
		
		Story story = new SimpleStory();
		story.accept(renderer);
		
		String result = byteStream.toString();
		
		String NL = System.getProperty("line.separator");
		
		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Story: ").append(story.title()).append(NL + NL);
		expectedResult.append("As a ").append(SimpleStory.ROLE).append(NL);
		expectedResult.append("I want ").append(SimpleStory.FEATURE).append(NL);
		expectedResult.append("So that ").append(SimpleStory.BENEFIT).append(NL + NL);
		expectedResult.append("Scenario: ").append(SimpleStory.FIRST_SCENARIO_NAME).append(NL + NL);
		expectedResult.append("Given ").append(new ConvertCase(new EverythingCompiles()).toSeparateWords()).append(NL);
		expectedResult.append("When ").append(new ConvertCase(new ICrossMyFingers()).toSeparateWords()).append(NL);
		expectedResult.append("Then ").append(new ConvertCase(new PlainTextRendererShouldWork()).toSeparateWords()).append(NL + NL);
		
		expectedResult.append("Scenario: ").append(SimpleStory.SECOND_SCENARIO_NAME).append(NL + NL);
		expectedResult.append("Given \"").append(new ConvertCase(SimpleStory.FIRST_SCENARIO_NAME).toSeparateWords());
		expectedResult.append("\" from \"").append(new ConvertCase(story).toSeparateWords()).append("\"" + NL);
		expectedResult.append("and ").append(new ConvertCase(new FirstScenarioRanWithoutFallingOver()).toSeparateWords()).append(NL);
		expectedResult.append("When ").append(new ConvertCase(new IDoNothing()).toSeparateWords()).append(NL);
		expectedResult.append("Then ").append(new ConvertCase(new PlainTextRendererShouldStillWork()).toSeparateWords()).append(NL);
		expectedResult.append("and ").append(new ConvertCase(new BehaviourClassShouldNotFail()).toSeparateWords()).append(NL);
		
		Verify.equal(expectedResult.toString(), result);
	}

	private static class SimpleStory extends Story {
		
		private static String ROLE = "behaviour analyst";
		private static String FEATURE = "to see the behaviour of PlainTextRenderer";
		private static String BENEFIT = "I can be sure that it works";
		private static String FIRST_SCENARIO_NAME = "PlainTextRenderer works";
		private static String SECOND_SCENARIO_NAME = "PlainTextRenderer still works";
		
		public SimpleStory() {
			super(new Narrative(ROLE, FEATURE, BENEFIT), 
					new AcceptanceCriteria());
			
			ScenarioUsingMiniMock firstScenario = new ScenarioUsingMiniMock(
					FIRST_SCENARIO_NAME,
					this,
					new Context(new EverythingCompiles()),
					new ICrossMyFingers(),
					new Outcomes(new PlainTextRendererShouldWork()));
			addScenario(firstScenario);
			
			ScenarioUsingMiniMock secondScenario = new ScenarioUsingMiniMock(
					SECOND_SCENARIO_NAME,
					this,
					new Context(firstScenario, new FirstScenarioRanWithoutFallingOver()),
					new IDoNothing(),
					new Outcomes(new PlainTextRendererShouldStillWork(),
							new BehaviourClassShouldNotFail()));
			addScenario(secondScenario);
		}
	}
	
	private static class EverythingCompiles extends GivenUsingMiniMock {
		public void setUp(World world) throws Exception {}
	}
	
	private static class FirstScenarioRanWithoutFallingOver extends GivenUsingMiniMock {
		public void setUp(World world) throws Exception {}
	}
	
	private static class ICrossMyFingers extends EventUsingMiniMock {
		public void occurIn(World world) throws Exception {}
	}
	
	private static class IDoNothing extends EventUsingMiniMock {
		public void occurIn(World world) throws Exception {}
	}
	
	public static class PlainTextRendererShouldWork extends ExpectationUsingMiniMock {
		public void setExpectationIn(World world) {}
		public void verify(World world) {}
	}
	
	public static class PlainTextRendererShouldStillWork extends ExpectationUsingMiniMock {
		public void setExpectationIn(World world) {}
		public void verify(World world) {}
	}
	public static class BehaviourClassShouldNotFail extends ExpectationUsingMiniMock {
		public void setExpectationIn(World world) {}
		public void verify(World world) {}
	}
}