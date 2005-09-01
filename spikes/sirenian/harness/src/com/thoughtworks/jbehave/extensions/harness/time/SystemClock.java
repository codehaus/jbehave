package com.thoughtworks.jbehave.extensions.harness.time;

public class SystemClock implements Clock {

	public long getTimeInMillis() {
		return System.currentTimeMillis();
	}

}
