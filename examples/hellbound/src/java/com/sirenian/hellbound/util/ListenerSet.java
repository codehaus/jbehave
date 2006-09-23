package com.sirenian.hellbound.util;

import java.util.Iterator;
import java.util.WeakHashMap;


public class ListenerSet {

	private WeakHashMap listeners = new WeakHashMap();

	public void addListener(Listener listener) {
		listeners.put(listener, null);
	}
	
	public void notifyListeners(ListenerNotifier notifier) {
		for (Iterator iter = listeners.keySet().iterator(); iter.hasNext(); ) {
			Object listener = iter.next();			
			notifier.notify((Listener)listener);
		}
	}

	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}
}
