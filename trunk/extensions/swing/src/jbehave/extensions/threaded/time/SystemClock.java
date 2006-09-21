package jbehave.extensions.threaded.time;

public class SystemClock implements Clock {

	public long getTimeInMillis() {
		return System.currentTimeMillis();
	}

}
