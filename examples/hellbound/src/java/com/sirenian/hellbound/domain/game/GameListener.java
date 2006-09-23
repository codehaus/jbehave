package com.sirenian.hellbound.domain.game;

import com.sirenian.hellbound.util.Listener;


public interface GameListener extends Listener {
	void reportGameStateChanged(GameState state);
}