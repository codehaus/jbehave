package org.jbehave.it;

public class SampleBehaviourTest extends junit.framework.TestCase {

    public void testNothing(){
        // keep junit happy
    }
    
    public void shouldDoSomethingInTestScope() {
        System.out.println("Done something in test scope");
    }
}
