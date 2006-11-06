package com.sirenian.hellbound.domain.game;

public interface GameRequestListener {

	GameRequestListener NULL = new GameRequestListener() {
        public void requestGameStart() {}};

    void requestGameStart();

}
