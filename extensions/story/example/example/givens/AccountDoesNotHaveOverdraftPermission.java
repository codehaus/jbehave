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


/** set overdraft limit = 0 */
public class AccountDoesNotHaveOverdraftPermission extends GivenUsingMiniMock {

    public void setUp(World world) {
        Mock account = (Mock) world.get("account", mock(Account.class));
        account.stubs("getOverdraftLimit").withNoArguments().will(returnValue(0));
    }

    public String getDescription() {
        return "set overdraft limit = 0";
    }
}