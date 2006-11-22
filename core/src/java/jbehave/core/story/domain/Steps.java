package jbehave.core.story.domain;

public class Steps extends ScenarioComponents implements Step {

    private final Step[] steps;

    public Steps(Step step) {
        this(new Step[] {step});
    }
    
    public Steps(Step stepA, Step stepB) {
        this(new Step[] { stepA, stepB });
    }
    
    public Steps(Step stepA, Step stepB, Step stepC) {
        this(new Step[] { stepA, stepB, stepC });
    }
    
    public Steps(Step[] steps) {
        super(steps);
        this.steps = steps;
    }

    public void perform(World world) {
        for (int i = 0; i < steps.length; i++) {
            steps[i].perform(world);
        }
    }

    public void undoIn(World world) {
        for (int i = steps.length - 1; i > -1; i--) {
            steps[i].undoIn(world);
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Steps ");
        for ( int i = 0; i < steps.length; i++ ){
            buffer.append(steps[i].getClass().getName());
            if ( i < steps.length - 1 ){
                buffer.append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
    
}
