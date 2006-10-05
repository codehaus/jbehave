package com.sirenian.hellbound.util;

import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

public class ListenerSetBehaviour extends UsingMiniMock {

	public void shouldPerformCommandOnAddedListeners() throws Exception {

		ListenerSet list = new ListenerSet();

		Mock listener = mock(WibblableListener.class);
		listener.expects("wibble");

		list.addListener((Listener) listener);

		list.notifyListeners(new WibblyNotifier());

		verifyMocks();
	}
	
	public void shouldNotNotifyRemovedListeners() throws Exception {
		ListenerSet list = new ListenerSet();

		Mock listener = mock(WibblableListener.class);
		listener.expects("wibble").never();
		
		list.addListener((Listener) listener);
		list.removeListener((Listener) listener);

		list.notifyListeners(new WibblyNotifier());

		verifyMocks();		
	}

	public interface WibblableListener extends Listener {
		public void wibble();
	}

	public class WibblyNotifier implements ListenerNotifier {
		public void notify(Listener listener) {
			((WibblableListener) listener).wibble();
		}
	}
}
