/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;

import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.GivenUsingMiniMock;


/** @given overdraft limit = 100 */
public class AccountHasOverdraftPermission extends GivenUsingMiniMock {
    public void setUp(Environment context) throws Exception {
    }

    public String getDescription() {
        return "overdraft limit = 100";
    }
}
