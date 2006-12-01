package com.sirenian.hellbound.domain.game;

public interface GameRequestListener {

	GameRequestListener NULL = new GameRequestListener() {
        public void requestStartGame() {}
        public void requestDropGlyph() {}
        public void requestMoveGlyphDown() {}
        public void requestMoveGlyphLeft() {}
        public void requestMoveGlyphRight() {}
        public void requestRotateGlyphLeft() {}
        public void requestRotateGlyphRight() {}
    };

    void requestStartGame();
    
    void requestDropGlyph();

    void requestMoveGlyphRight();

    void requestMoveGlyphLeft();

    void requestMoveGlyphDown();

    void requestRotateGlyphLeft();

    void requestRotateGlyphRight();

}
