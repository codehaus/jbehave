package jbehave.extensions.threaded.time;

import jbehave.core.minimock.Constraint;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.extensions.threaded.time.PseudoClock;
import jbehave.extensions.threaded.time.ClockedTimeouter;
import jbehave.extensions.threaded.time.TimeoutException;

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

	private Constraint isLessThanOrEq(final int time) {
		return new Constraint() {
			public boolean matches(Object arg) {
				return arg instanceof Long && ((Long)arg).longValue() <= time;
			}
				
			public String toString() {
				return "is Less Than " + time;
			}
		};
	}
	
	private Constraint isGreaterThan(final int time) {
		return new Constraint() {
			public boolean matches(Object arg) {
				return arg instanceof Long && ((Long)arg).longValue() > time;
			}	
			
			public String toString() {
				return "is Greater Than " + time;
			}		
		};
	}
}
