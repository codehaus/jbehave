package com.sirenian.hellbound.domain.game;

import com.sirenian.hellbound.util.Listener;


public interface GameListener extends Listener {
	GameListener NULL = new GameListener() {
        public void reportGameStateChanged(GameState state) {}};

    void reportGameStateChanged(GameState state);
}