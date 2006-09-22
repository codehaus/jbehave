package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;

public class Game implements GameRequestListener {

	private GameState state;
	
	private ListenerSet listeners;
	private ListenerNotifier stateNotifier;

	public Game() {
		listeners = new ListenerSet();
		state = GameState.READY;
		stateNotifier = new ListenerNotifier() {
			public void notify(Listener listener) {
				((GameListener)listener).reportGameStateChanged(state);
			}			
		};
	}
	
	public void requestStart() {
		setState(GameState.RUNNING);
	}

	private void setState(GameState newState) {
		state = newState;
		listeners.notifyListeners(stateNotifier);
		
	}

	public void addListener(GameListener listener) {
		listeners.addListener(listener);
		stateNotifier.notify(listener);
	}

}
