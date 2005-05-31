/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.domain.GivenUsingMiniMock;

import example.domain.Account;


/**
 * set balance = -50
 */
public class AccountIsOverdrawn extends GivenUsingMiniMock {
    public void setUp(World world) throws Exception {
        Mock accountMock = (Mock)world.get("account", mock(Account.class));
        accountMock.stubs("getBalance").will(returnValue(-50));
    }

    public String getDescription() {
        return "set balance = -50";
    }
}