package com.sirenian.hellbound.engine;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;

public class GameBehaviour extends UsingMiniMock {

	public void shouldRunOnRequestStartAndInformListeners() throws Exception {
		
		Mock gameListener = mock(GameListener.class);
		gameListener.expects("reportGameStateChanged").once().with(GameState.READY);
		gameListener.expects("reportGameStateChanged").once().with(GameState.RUNNING);
		
		Game game = new Game();
		
		game.addListener((GameListener)gameListener);
		game.requestStart();
		
		verifyMocks();
	}
}
