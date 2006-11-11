package com.sirenian.hellbound.domain.game;

public interface GameRequestListener {

	GameRequestListener NULL = new GameRequestListener() {
        public void requestStartGame() {}
        public void requestDropGlyph() {}
    };

    void requestStartGame();
    
    void requestDropGlyph();

}
