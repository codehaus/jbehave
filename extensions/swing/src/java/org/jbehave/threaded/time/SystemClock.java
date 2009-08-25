package org.jbehave.threaded.time;

public class SystemClock implements Clock {

	public long getTimeInMillis() {
		return System.currentTimeMillis();
	}

}
