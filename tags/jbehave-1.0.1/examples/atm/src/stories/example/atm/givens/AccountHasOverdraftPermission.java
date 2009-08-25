/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.atm.givens;

import org.jbehave.core.Ensure;
import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import org.jbehave.core.story.domain.World;



/** @given overdraft limit = 100 */
public class AccountHasOverdraftPermission extends GivenUsingMiniMock {
    public void setUp(World world) {
        Ensure.pending();
    }

    public String getDescription() {
        return "overdraft limit = 100";
    }
}
