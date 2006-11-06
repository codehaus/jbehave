package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.game.GameRequestListener;
import com.sirenian.hellbound.util.Queue;

public interface EngineQueue extends Queue, GameRequestListener {

    void setGameRequestDelegate(GameRequestListener listener);

}
