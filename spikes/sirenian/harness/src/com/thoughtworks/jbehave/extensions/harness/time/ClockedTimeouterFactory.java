package com.thoughtworks.jbehave.extensions.harness.time;

public class ClockedTimeouterFactory implements TimeouterFactory {
	
	private Clock clock;

	public ClockedTimeouterFactory() {
		this(new SystemClock());
	}
	
	public ClockedTimeouterFactory(Clock clock) {
		this.clock = clock;
	}

	public Timeouter createTimeouter() {
		return new ClockedTimeouter(clock);
	}
}
