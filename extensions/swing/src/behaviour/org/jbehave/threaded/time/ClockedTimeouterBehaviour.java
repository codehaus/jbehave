package org.jbehave.threaded.time;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Matcher;
import org.jbehave.threaded.time.ClockedTimeouter;
import org.jbehave.threaded.time.PseudoClock;
import org.jbehave.threaded.time.TimeoutException;


public class ClockedTimeouterBehaviour extends UsingMiniMock {
	
	private PseudoClock clock = new PseudoClock();
	
	public void shouldTimeoutIffTimeoutHasBeenExceeded() {
		ClockedTimeouter timeouter = new ClockedTimeouter(clock);
		clock.setTimeInMillis(0);
		timeouter.start(20);
		
		for (int i = 1; i < 21; i++) {
			try {
				clock.setTimeInMillis(i);
				timeouter.checkTime();
				ensureThat(i, isLessThanOrEq(20));
			} catch (TimeoutException e) {
				ensureThat(i, isGreaterThan(20));
			}
		}
	}

	private Matcher isLessThanOrEq(final int time) {
		return new Matcher() {
			public boolean matches(Object arg) {
				return arg instanceof Long && ((Long)arg).longValue() <= time;
			}
				
			public String toString() {
				return "is Less Than " + time;
			}
		};
	}
	
	private Matcher isGreaterThan(final int time) {
		return new Matcher() {
			public boolean matches(Object arg) {
				return arg instanceof Long && ((Long)arg).longValue() > time;
			}	
			
			public String toString() {
				return "is Greater Than " + time;
			}		
		};
	}
}
