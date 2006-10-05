/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.atm.givens;

import jbehave.core.mock.Mock;
import jbehave.core.story.domain.GivenUsingMiniMock;
import jbehave.core.story.domain.World;


import example.domain.Account;


/**
 * set balance = -50
 */
public class AccountIsOverdrawn extends GivenUsingMiniMock {
    public void setUp(World world) {
        Mock accountMock = (Mock)world.get("account", mock(Account.class));
        accountMock.stubs("getBalance").will(returnValue(-50));
    }

    public String getDescription() {
        return "set balance = -50";
    }
}