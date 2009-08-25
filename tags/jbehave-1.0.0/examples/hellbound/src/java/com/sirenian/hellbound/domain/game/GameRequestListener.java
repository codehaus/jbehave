package com.sirenian.hellbound.domain.game;

import com.sirenian.hellbound.domain.glyph.GlyphMovement;

public interface GameRequestListener {

	GameRequestListener NULL = new GameRequestListener() {
        public void requestStartGame() {}
        public void requestGlyphMovement(GlyphMovement movement) {}
    };

    void requestStartGame();
    
    void requestGlyphMovement(GlyphMovement movement);
}
