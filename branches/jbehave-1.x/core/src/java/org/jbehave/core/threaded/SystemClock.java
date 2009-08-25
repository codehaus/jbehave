package org.jbehave.core.threaded;

public class SystemClock implements Clock {

	public long getTimeInMillis() {
		return System.currentTimeMillis();
	}

}
