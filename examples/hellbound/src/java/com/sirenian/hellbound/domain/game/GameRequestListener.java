package com.sirenian.hellbound.domain.game;

public interface GameRequestListener {

	GameRequestListener NULL = new GameRequestListener() {
        public void requestStart() {}};

    void requestStart();

}
