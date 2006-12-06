package org.jbehave.core.story;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jbehave.core.Ensure;
import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.story.StoryRunner;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.domain.World;
import org.jbehave.core.story.listener.PlainTextScenarioListener;
import org.jbehave.core.story.renderer.Renderer;
import org.jbehave.core.story.result.ScenarioResult;


public class StoryRunnerBehaviour extends UsingMiniMock {

	public void shouldRunStoryAndOutputResults() throws Exception {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		PrintStream stream = new PrintStream(buffer);
		
		new StoryRunner().run(SimpleStory.class.getName(), stream);

        Ensure.that(buffer.toString(), contains(".."));
		Ensure.that(buffer.toString(), contains("Total: 2."));
		
		verifyMocks();
	}
    
    public static class MyStory extends UsingMiniMock implements Story  {
		private Mock mock;
		
		public MyStory() {
            ScenarioResult resultA = new ScenarioResult("scenarioA", "MyStory", ScenarioResult.SUCCEEDED);
            ScenarioResult resultB = new ScenarioResult("scenarioB", "MyStory", ScenarioResult.SUCCEEDED);
            
			mock = mock(Story.class);
            mock.expects("addListener").with(isA(PlainTextScenarioListener.class));
			mock.expects("run").with(a(World.class)).will(returnValue(new ScenarioResult[] {resultA, resultB}));
		}

		public Narrative narrative() {
			return ((Story)mock).narrative();
		}

		public void run() {
			((Story)mock).run();
		}

        public void addListener(BehaviourListener listener) {
            ((Story)mock).addListener(listener);
        }

        public void narrateTo(Renderer renderer) {
            throw new UnsupportedOperationException("Should not be called");
        }
		
        public void specify() {
        }
	}
}
