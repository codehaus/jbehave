package org.jbehave.threaded.time;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Matcher;


public class ClockedTimeouterBehaviour extends UsingMiniMock {
	
	private PseudoClock clock = new PseudoClock();
	
	public void shouldTimeoutIffTimeoutHasBeenEqualledOrExceeded() {
		ClockedTimeouter timeouter = new ClockedTimeouter(clock);
		clock.setTimeInMillis(0);
		timeouter.start(20);
		
		for (int i = 1; i < 20; i++) {
			try {
				clock.setTimeInMillis(i);
				timeouter.checkTime();
				ensureThat(i, isLessThan(20));
			} catch (TimeoutException e) {
				ensureThat(i, isGreaterThanOrEq(20));
			}
		}
	}

	private Matcher isLessThan(final int time) {
		return new Matcher() {
			public boolean matches(Object arg) {
				return arg instanceof Long && ((Long)arg).longValue() < time;
			}
				
			public String toString() {
				return "is Less Than " + time;
			}
		};
	}
	
	private Matcher isGreaterThanOrEq(final int time) {
		return new Matcher() {
			public boolean matches(Object arg) {
				return arg instanceof Long && ((Long)arg).longValue() >= time;
			}	
			
			public String toString() {
				return "is Greater Than " + time;
			}		
		};
	}
}
