package org.jbehave.core.story.domain;


public class GivenStep extends AbstractStep {
    
    public GivenStep(Given given) {
        super(given);
    }

    public void perform(World world) {
        given().setUp(world);
    }
    
    private Given given() {
        return (Given)component;
    }
}
