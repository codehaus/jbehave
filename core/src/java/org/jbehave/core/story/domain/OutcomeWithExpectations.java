package org.jbehave.core.story.domain;

public interface OutcomeWithExpectations extends Outcome {
    void setExpectationsIn(World world);
}
