package org.jbehave.examples.trader;

import junit.framework.Assert;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;

public class OrderMatchingSteps {

    private String param;

    @Given("a step that has $param")
    public void has(String param){
        this.param = param;
    }
    
    @Given("a step that has exactly one $param")
    public void hasExactlyOne(String param){
        this.param = param;
    }

    @Then("the parameter value is \"$param\"")
    public void theParamValue(String param){
        Assert.assertEquals(this.param, param);
    }

}
