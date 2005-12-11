/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;

import jbehave.story.domain.GivenUsingMiniMock;
import jbehave.story.domain.World;


/** balance = -50, overdraft limit = 100 */
public class AccountIsOverdrawnWithPermission extends GivenUsingMiniMock {
    public void setUp(World world) {
        new AccountIsOverdrawn().setUp(world);
        new AccountHasOverdraftPermission().setUp(world);
    }

    public String getDescription() {
        return "balance = -50, overdraft limit = 100";
    }
}
