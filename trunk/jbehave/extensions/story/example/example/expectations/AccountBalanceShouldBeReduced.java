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
import com.thoughtworks.jbehave.extensions.story.domain.UnimplementedException;


/** balance = balance - 20 */
public class AccountBalanceShouldBeReduced extends Expectation {

    public void setExpectationIn(Environment environment) throws Exception {
    }
    
    public void verify(Environment context) {
        throw new UnimplementedException();
    }
}