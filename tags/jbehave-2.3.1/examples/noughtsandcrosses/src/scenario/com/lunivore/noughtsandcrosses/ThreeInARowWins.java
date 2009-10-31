package com.lunivore.noughtsandcrosses;

import com.lunivore.noughtsandcrosses.util.NoughtsAndCrossesScenario;

public class ThreeInARowWins extends NoughtsAndCrossesScenario {

    public ThreeInARowWins() {
        this(Thread.currentThread().getContextClassLoader());
    }
    
    public ThreeInARowWins(ClassLoader classLoader) {
        super(classLoader);
    }

}
