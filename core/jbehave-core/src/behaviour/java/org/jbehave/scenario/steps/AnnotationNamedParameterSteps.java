package org.jbehave.scenario.steps;

import org.jbehave.scenario.annotations.Named;

class AnnotationNamedParameterSteps extends Steps {
    String ith;
    String nth;

    public void methodWithNamedParametersInNaturalOrder(@Named("ith") String ithName, @Named("nth") String nthName){
        this.ith = ithName;
        this.nth = nthName;
    }

    public void methodWithNamedParametersInInverseOrder(@Named("nth") String nthName, @Named("ith") String ithName){
        this.ith = ithName;
        this.nth = nthName;
    }

}
