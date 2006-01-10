package jbehave.core.story;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jbehave.core.Ensure;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Story;
import jbehave.core.story.verifier.StoryVerifier;
import jbehave.core.story.visitor.Visitor;

public class RunBehaviour extends UsingMiniMock {

	public void shouldInvokeAndVerifyStory() throws Exception {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		PrintStream stream = new PrintStream(buffer);
		
		Run.story(MyStory.class.getName(), stream);

		Ensure.that(buffer.toString().indexOf("Total: 0.") != -1); // TODO put the real thing in here
		
		verifyMocks();
	}

    public static class MyStory extends UsingMiniMock implements Story  {
		private Mock mock;
		
		public MyStory() {
			mock = mock(Story.class);
			mock.expects("accept").with(isA(StoryVerifier.class));
		}
		
		public String title() {
			return ((Story)mock).title();
		}

		public Narrative narrative() {
			return ((Story)mock).narrative();
		}

		public void accept(Visitor visitor) {
			((Story)mock).accept(visitor);
		}
		
	}
}
