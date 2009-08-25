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


/** set balance = -150 */
public class AccountIsOverOverdraftLimit extends GivenUsingMiniMock {
    public void setUp(World world) {
        new AccountHasOverdraftPermission().setUp(world);
    }

    public String getDescription() {
        return "set balance = -150";
    }
}