/**
 * 
 */
package com.sirenian.hellbound.domain.game;

public class GameState {
	private final String description;
    private GameState(String description) {
        this.description = description;
    }
    
    public String toString() {
        return description;
    }
	
	public static final GameState READY = new GameState("READY");
	public static final GameState RUNNING = new GameState("RUNNING");
	
	
}