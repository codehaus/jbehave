/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.expectations;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.Expectation;



public class ATMShouldRetainBankCard extends Expectation {
    public void setExpectationIn(Environment environment) throws Exception {
        throw new RuntimeException("oops");
    }

    public void verify(Environment context) {
        // keep bank card
    }
}