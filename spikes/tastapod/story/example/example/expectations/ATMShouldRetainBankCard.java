/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.expectations;

import com.thoughtworks.jbehave.extensions.story.base.ExpectationBase;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;



public class ATMShouldRetainBankCard extends ExpectationBase {
    public void setExpectationIn(Environment environment) throws Exception {
        throw new RuntimeException("oops");
    }

    public void verify(Environment context) {
        // keep bank card
    }
}