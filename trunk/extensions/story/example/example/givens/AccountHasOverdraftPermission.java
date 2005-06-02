/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.domain.GivenUsingMiniMock;


/** @given overdraft limit = 100 */
public class AccountHasOverdraftPermission extends GivenUsingMiniMock {
    public void setUp(World world) {
        Verify.pending();
    }

    public String getDescription() {
        return "overdraft limit = 100";
    }
}
