package jbehave.core.story.domain;

import jbehave.core.UsingMocks;
import jbehave.core.story.visitor.Visitable;

public interface Step extends Visitable, UsingMocks {

    void perform(World world);
    void undoIn(World world);
}
