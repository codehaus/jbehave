/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.expectations;

import com.thoughtworks.jbehave.extensions.story.base.Expectation;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;



public class ATMShouldRefuseCash extends Expectation {

    public void setExpectationIn(Environment environment) throws Exception {
    }
    
    public void verify(Environment context) {
        // refuse cash
    }
}