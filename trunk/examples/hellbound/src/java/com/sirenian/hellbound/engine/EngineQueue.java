package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public interface EngineQueue extends GameRequestListener {

    void setGameRequestDelegate(GameRequestListener listener);

    void invokeAndWait(Runnable empty_runnable);

}
