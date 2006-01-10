package jbehave.core.story;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jbehave.core.Ensure;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Story;
import jbehave.core.story.invoker.ScenarioInvoker;
import jbehave.core.story.listener.PlainTextScenarioListener;
import jbehave.core.story.verifier.ScenarioVerifier;
import jbehave.core.story.visitor.Visitor;

public class RunBehaviour extends UsingMiniMock {

	public void shouldInvokeVerifyAndListenToStory() throws Exception {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		PrintStream stream = new PrintStream(buffer);
		
		Run.story(MyStory.class.getName(), stream);

		Ensure.that(buffer.toString(), contains("Total: 0."));
		
		verifyMocks();
	}

    public static class MyStory extends UsingMiniMock implements Story  {
		private Mock mock;
		
		public MyStory() {
			mock = mock(Story.class);
            mock.expects("addListener").with(isA(PlainTextScenarioListener.class));
			mock.expects("run").with(isA(ScenarioInvoker.class), isA(ScenarioVerifier.class));
		}
		
		public String title() {
			return ((Story)mock).title();
		}

		public Narrative narrative() {
			return ((Story)mock).narrative();
		}

		public void run(Visitor invoker, Visitor verifier) {
			((Story)mock).run(invoker, verifier);
		}

        public void addListener(BehaviourListener listener) {
            ((Story)mock).addListener(listener);
        }

        public void narrate(Visitor renderer) {
            throw new UnsupportedOperationException("Should not be called");
        }
		
	}
}
