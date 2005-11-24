package jbehave.core;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class RunBehaviour {

	private static int runCount;
	
	public static class RunnableBehaviour {
		public void shouldBeRunByRunClass() {
			runCount++;
		}
	}
	
	private OutputStream nullOutputStream = new OutputStream() {
		public void write(int b) throws IOException {
			// do nothing
		}
	};
	
	public void shouldRunClassFromArgumentSuccessfully() {
		runCount = 0;
		Run.runBehaviourToStream(new String[] {RunnableBehaviour.class.getName()},
				new PrintStream(nullOutputStream));
		Ensure.that(runCount == 1);
	}
	
	public void shouldRunTwoClassesFromArgumentsSuccessfully() {
		runCount = 0;
		Run.runBehaviourToStream(new String[] {
				RunnableBehaviour.class.getName(),
				RunnableBehaviour.class.getName()},
				new PrintStream(nullOutputStream));
		Ensure.that(runCount == 2);
	}
}
