/*
 * Created on 06-Jan-2005
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.renderer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jbehave.core.mock.UsingConstraints;
import jbehave.core.story.SimpleStory;
import jbehave.core.story.domain.ScenarioDrivenStory;
import jbehave.core.util.ConvertCase;


/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class PlainTextRendererBehaviour extends UsingConstraints {

	public void shouldRenderStoryWhenNarratingStory() {
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteStream);
		
		PlainTextRenderer renderer = new PlainTextRenderer(printStream);
		
		ScenarioDrivenStory story = new SimpleStory();
		story.accept(renderer);
		
		String result = byteStream.toString();
		
		String NL = System.getProperty("line.separator");
		
		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Story: ").append(story.title()).append(NL + NL);
		expectedResult.append("As a ").append(SimpleStory.ROLE).append(NL);
		expectedResult.append("I want ").append(SimpleStory.FEATURE).append(NL);
		expectedResult.append("So that ").append(SimpleStory.BENEFIT).append(NL + NL);
		expectedResult.append("Scenario: ").append(SimpleStory.FIRST_SCENARIO_NAME).append(NL + NL);
		expectedResult.append("Given ").append(new ConvertCase(new SimpleStory.EverythingCompiles()).toSeparateWords()).append(NL);
		expectedResult.append("When ").append(new ConvertCase(new SimpleStory.ICrossMyFingers()).toSeparateWords()).append(NL);
		expectedResult.append("Then ").append(new ConvertCase(new SimpleStory.PlainTextRendererShouldWork()).toSeparateWords()).append(NL + NL);
		
		expectedResult.append("Scenario: ").append(SimpleStory.SECOND_SCENARIO_NAME).append(NL + NL);
		expectedResult.append("Given \"").append(new ConvertCase(SimpleStory.FIRST_SCENARIO_NAME).toSeparateWords());
		expectedResult.append("\" from \"").append(new ConvertCase(story).toSeparateWords()).append("\"" + NL);
		expectedResult.append("and ").append(new ConvertCase(new SimpleStory.FirstScenarioRanWithoutFallingOver()).toSeparateWords()).append(NL);
		expectedResult.append("When ").append(new ConvertCase(new SimpleStory.IDoNothing()).toSeparateWords()).append(NL);
		expectedResult.append("Then ").append(new ConvertCase(new SimpleStory.PlainTextRendererShouldStillWork()).toSeparateWords()).append(NL);
		expectedResult.append("and ").append(new ConvertCase(new SimpleStory.BehaviourClassShouldNotFail()).toSeparateWords()).append(NL);
		
		ensureThat(result, eq(expectedResult.toString()));
	}
}