package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.util.Queue;

public interface GuiQueue extends Queue, GameListener, GlyphListener {

	void setGameListenerDelegate(GameListener listener);
    void setGlyphListenerDelegate(GlyphListener listener);
	
}
