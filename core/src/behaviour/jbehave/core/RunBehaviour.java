package jbehave.core;


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
	
	private PrintStream nullPrintStream = new PrintStream(new OutputStream() {
		public void write(int b) {
			// do nothing
		}
	});
	
	public void shouldRunClassFromArgumentSuccessfully() {
		runCount = 0;
		new Run(nullPrintStream).verifyBehaviour(RunnableBehaviour.class);
		Ensure.that(runCount == 1);
	}
	
	public void shouldRunTwoClassesFromArgumentsSuccessfully() {
		runCount = 0;
		Run run = new Run(nullPrintStream);
        run.verifyBehaviour(RunnableBehaviour.class);
        run.verifyBehaviour(RunnableBehaviour.class);
		Ensure.that(runCount == 2);
	}
}
