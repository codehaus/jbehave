package org.jbehave.core.story;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jbehave.core.Block;
import org.jbehave.core.Ensure;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.listener.PlainTextScenarioListener;
import org.jbehave.core.story.renderer.Renderer;


public class StoryRunnerBehaviour extends UsingMiniMock {

	public void shouldSpecifyAndRunStoryAndOutputResults() throws Exception {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		PrintStream stream = new PrintStream(buffer);
		
		new StoryRunner().run(SimpleStory.class.getName(), stream);

        Ensure.that(buffer.toString(), contains(".."));
		Ensure.that(buffer.toString(), contains("Total: 2."));
		
		verifyMocks();
	}
	
	public void shouldOutputPendingExeptionsWithoutFailingTheStory() throws Exception {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		final PrintStream stream = new PrintStream(buffer);
		
		Exception exception = runAndCatch(Exception.class, new Block() {
			public void run() throws Exception {
				new StoryRunner().run(PendingStory.class.getName(), stream);
			}
		});

		Ensure.that(exception, isNull());
        Ensure.that(buffer.toString(), contains("P"));
		Ensure.that(buffer.toString(), contains("Total: 1. Pending: 1."));
		
		verifyMocks();
	}
    
    public static class PendingStory extends UsingMiniMock implements Story  {
		private Mock mock;
		
		public PendingStory() {
			mock = mock(Story.class);
            mock.expects("addListener").with(isA(PlainTextScenarioListener.class));
			mock.expects("run").will(throwException(new PendingException("TODO")));
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
