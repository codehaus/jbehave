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
		
		ensureThat(result, eq(SimpleStory.expectedDescription()));
	}
    
    public void shouldProvideComponentsWithCustomRenderingUsingStrings() {
        StringBuffer expectedResult = new StringBuffer();
        expectedResult.append("Custom renderable" + NL);
        
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);
        
        PlainTextRenderer renderer = new PlainTextRenderer(printStream);
        
        renderer.renderAny("Custom renderable");
        
        String result = byteStream.toString();
        
        ensureThat(result, eq(expectedResult.toString()));
    }
}