package com.lunivore.noughtsandcrosses;

import com.lunivore.noughtsandcrosses.util.NoughtsAndCrossesScenario;

public class TheGridStartsEmpty extends NoughtsAndCrossesScenario {
    
    public TheGridStartsEmpty() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public TheGridStartsEmpty(ClassLoader classLoader) {
        super(classLoader);
    }

}
