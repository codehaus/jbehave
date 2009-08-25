package com.sirenian.hellbound.engine;

import org.jbehave.core.mock.UsingMatchers;

public class CenterCalculatorBehaviour extends UsingMatchers {

    public void shouldCalculateCenterExactlyForOddWidths() {
        ensureThat(CenterCalculator.forWidth(5), eq(2));
        ensureThat(CenterCalculator.forWidth(7), eq(3));
        ensureThat(CenterCalculator.forWidth(9), eq(4));
    }
    
    public void shouldCalculateCenterAsLeftOfTrueCenterForEvenWidths() {

        ensureThat(CenterCalculator.forWidth(6), eq(2));
        ensureThat(CenterCalculator.forWidth(8), eq(3));
        ensureThat(CenterCalculator.forWidth(10), eq(4));
    }
}
