package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameRequestListener;

public interface RequestQueue extends GameRequestListener {

    void setGameRequestListener(GameRequestListener listener);

    void invokeAndWait(Runnable empty_runnable);

}
