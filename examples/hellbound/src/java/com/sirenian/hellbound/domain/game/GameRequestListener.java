package com.sirenian.hellbound.domain.game;

public interface GameRequestListener {

	GameRequestListener NULL = new GameRequestListener() {
        public void requestStartGame() {}
        public void requestDropGlyph() {}
        public void requestMoveGlyphDown() {}
        public void requestMoveGlyphLeft() {}
        public void requestMoveGlyphRight() {}
    };

    void requestStartGame();
    
    void requestDropGlyph();

    void requestMoveGlyphRight();

    void requestMoveGlyphLeft();

    void requestMoveGlyphDown();

}
