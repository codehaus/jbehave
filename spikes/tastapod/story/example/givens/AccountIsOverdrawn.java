/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package givens;

import com.thoughtworks.jbehave.extensions.story.base.GivenBase;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;


/**
 * set balance = -50
 */
public class AccountIsOverdrawn extends GivenBase {
    public void setUp(Environment context) {
    }

    public String getDescription() {
        return "set balance = -50";
    }
}