package com.thoughtworks.jbehave.extensions.harness.time;
import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.extensions.harness.time.ClockedTimeouter;
import com.thoughtworks.jbehave.extensions.harness.time.TimeoutException;

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
				Verify.that(i <= 20);
			} catch (TimeoutException e) {
				Verify.that(i > 20);
			}
			
		}
	}
}
