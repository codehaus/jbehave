package org.jbehave.threaded.time;

import org.jbehave.threaded.time.Clock;

public class PseudoClock implements Clock {

	private long timeInMillis;

	public long getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis(long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

}
