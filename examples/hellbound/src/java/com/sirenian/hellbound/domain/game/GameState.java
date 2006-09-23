/**
 * 
 */
package com.sirenian.hellbound.domain.game;

public class GameState {
	private GameState() {}
	
	public static final GameState READY = new GameState();
	public static final GameState RUNNING = new GameState();
	
	
}