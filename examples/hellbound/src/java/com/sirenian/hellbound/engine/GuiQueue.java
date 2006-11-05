package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.glyph.GlyphListener;

public interface GuiQueue extends GameListener, GlyphListener {

	void setGameReportDelegate(GameListener listener);
	void setGlyphReportDelegate(GlyphListener listener);
	
}
