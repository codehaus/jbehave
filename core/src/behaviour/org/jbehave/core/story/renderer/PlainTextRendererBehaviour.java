/*
 * Created on 06-Jan-2005
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.renderer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jbehave.core.mock.UsingMatchers;
import org.jbehave.core.story.SimpleStory;
import org.jbehave.core.story.domain.ScenarioDrivenStory;
import org.jbehave.core.util.CamelCaseConverter;



/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class PlainTextRendererBehaviour extends UsingMatchers {

	private static final String NL = System.getProperty("line.separator");

    public void shouldRenderStoryWhenNarratingStory() {
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteStream);
		
		PlainTextRenderer renderer = new PlainTextRenderer(printStream);
		
		ScenarioDrivenStory story = new SimpleStory();
        story.specify();
		story.narrateTo(renderer);
		
		String result = byteStream.toString();
		
		StringBuffer expectedResult = new StringBuffer();
		expectedResult.append("Story: ").append(textOf(story)).append(NL + NL);
		expectedResult.append("As a ").append(SimpleStory.ROLE).append(NL);
		expectedResult.append("I want ").append(SimpleStory.FEATURE).append(NL);
		expectedResult.append("So that ").append(SimpleStory.BENEFIT).append(NL + NL);
        expectedResult.append("Scenario: ").append(textOf(new SimpleStory.PlainTextRendererWorks())).append(NL + NL);
        expectedResult.append("Given ").append(textOf(new SimpleStory.EverythingCompiles())).append(NL);
		expectedResult.append("When ").append(textOf(new SimpleStory.ICrossMyFingers())).append(NL);
		expectedResult.append("Then ").append(textOf(new SimpleStory.PlainTextRendererShouldWork())).append(NL + NL);
		
		expectedResult.append("Scenario: ").append(textOf(new SimpleStory.PlainTextRendererStillWorks())).append(NL + NL);
		expectedResult.append("Given ").append(textOf(new SimpleStory.PlainTextRendererWorks())).append(NL);;
		expectedResult.append("and ").append(textOf(new SimpleStory.FirstScenarioRanWithoutFallingOver())).append(NL);
		expectedResult.append("When ").append(textOf(new SimpleStory.IDoNothing())).append(NL);
		expectedResult.append("Then ").append(textOf(new SimpleStory.PlainTextRendererShouldStillWork())).append(NL);
		expectedResult.append("and ").append(textOf(new SimpleStory.BehaviourClassShouldNotFail())).append(NL);
		
		ensureThat(result, eq(expectedResult.toString()));
	}
    
    public void shouldProvideComponentsWithCustomRenderingUsingStrings() {
        StringBuffer expectedResult = new StringBuffer();
        expectedResult.append("Custom renderable" + NL);
        
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);
        
        PlainTextRenderer renderer = new PlainTextRenderer(printStream);
        
        renderer.render("Custom renderable");
        
        String result = byteStream.toString();
        
        ensureThat(result, eq(expectedResult.toString()));
    }

    private String textOf(Object obj) {
        return new CamelCaseConverter(obj).toPhrase();
    }
}