/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.atm.givens;

import jbehave.core.Ensure;
import jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import jbehave.core.story.domain.World;



/** @given overdraft limit = 100 */
public class AccountHasOverdraftPermission extends GivenUsingMiniMock {
    public void setUp(World world) {
        Ensure.pending();
    }

    public String getDescription() {
        return "overdraft limit = 100";
    }
}
