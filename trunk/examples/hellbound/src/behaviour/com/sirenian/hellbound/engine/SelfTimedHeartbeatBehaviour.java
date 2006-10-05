package com.sirenian.hellbound.engine;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.glyph.HeartbeatListener;

/**
 * Heartbeat has a behaviour which is very easy to define, but
 * hard to ensure (especially in a timely, automated manner)
 * because of the timers involved.
 * 
 * It also won't get used in any of the automated stories, as
 * it would take too long (ForcedHeartBeat will be used instead).
 */
public class SelfTimedHeartbeatBehaviour extends UsingMiniMock {

	
	public void shouldBeatAfterElapsedTime() throws Exception {
		Mock heartbeatListener = mock(HeartbeatListener.class);
		heartbeatListener.expects("beat").atLeastOnce();
		
		SelfTimedHeartbeat heartbeat = new SelfTimedHeartbeat();
		heartbeat.addListener((HeartbeatListener)heartbeatListener);
		heartbeat.start(3);
	
		synchronized (this) {
			this.wait(20);   
			// Needs to be long enough for heartbeat to start
			// and then beat once or more
		}
		
		verifyMocks();
	}
	
	public void shouldBeatMoreQuicklyWithEachBeat() {
		// No way of ensuring this with automation. 
	}
	
	public void shouldNotBeatAfterBeingStopped() throws Exception {
		Mock heartbeatListener = mock(HeartbeatListener.class);
		heartbeatListener.expects("beat").never();
		
		SelfTimedHeartbeat heartbeat = new SelfTimedHeartbeat();
		heartbeat.start(3);
	
		heartbeat.stop();
		
		heartbeat.addListener((HeartbeatListener)heartbeatListener);		

		synchronized (this) {
			this.wait(10);
		}		
		
		verifyMocks();		
	}
}
