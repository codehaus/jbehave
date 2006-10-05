package jbehave.core.story;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jbehave.core.Ensure;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Story;
import jbehave.core.story.domain.World;
import jbehave.core.story.listener.PlainTextScenarioListener;
import jbehave.core.story.result.ScenarioResult;
import jbehave.core.story.visitor.Visitor;

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
			mock.expects("run").with(isA(World.class)).will(returnValue(new ScenarioResult[] {resultA, resultB}));
		}
		
		public String title() {
			return ((Story)mock).title();
		}

		public Narrative narrative() {
			return ((Story)mock).narrative();
		}

		public void run(World world) {
			((Story)mock).run(world);
		}

        public void addListener(BehaviourListener listener) {
            ((Story)mock).addListener(listener);
        }

        public void accept(Visitor renderer) {
            throw new UnsupportedOperationException("Should not be called");
        }
		
	}
}
