/*
 * Created on 20-Jun-2005
 * 
 * (c) 2003-2005 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 */
public class RunBehaviour {

	private static int runCount;
	
	public void shouldRunClassFromArgumentSuccessfully() {
		runCount = 0;
		Run.runBehaviourToStream(new String[] {RunnableBehaviour.class.getName()},
				new PrintStream(new NullOutputStream()));
		Ensure.that(runCount == 1);
	}
	
	public void shouldRunTwoClassesFromArgumentsSuccessfully() {
		runCount = 0;
		Run.runBehaviourToStream(new String[] {
				RunnableBehaviour.class.getName(),
				RunnableBehaviour.class.getName()},
				new PrintStream(new NullOutputStream()));
		Ensure.that(runCount == 2);
	}
	
	public static class RunnableBehaviour {
		public void shouldBeRunByRunClass() {
			runCount++;
		}
	}
	
	public static class NullOutputStream extends OutputStream {
		public void write(int b) throws IOException {
			// do nothing
		}
	}
}
