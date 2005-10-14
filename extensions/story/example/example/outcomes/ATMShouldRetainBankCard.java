/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.outcomes;

import com.thoughtworks.jbehave.story.domain.OutcomeUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.World;



public class ATMShouldRetainBankCard extends OutcomeUsingMiniMock {
    public void setExpectationIn(World world) {
        throw new RuntimeException("oops");
    }

    public void verify(World world) {
        // keep bank card
    }
}