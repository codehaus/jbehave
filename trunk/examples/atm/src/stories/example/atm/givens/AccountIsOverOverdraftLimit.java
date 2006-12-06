/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.atm.givens;

import jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import jbehave.core.story.domain.World;


/** set balance = -150 */
public class AccountIsOverOverdraftLimit extends GivenUsingMiniMock {
    public void setUp(World world) {
        new AccountHasOverdraftPermission().setUp(world);
    }

    public String getDescription() {
        return "set balance = -150";
    }
}