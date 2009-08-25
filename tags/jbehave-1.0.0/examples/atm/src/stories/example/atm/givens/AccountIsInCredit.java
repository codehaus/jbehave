/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.atm.givens;


import org.jbehave.core.minimock.story.domain.GivenUsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.story.domain.World;

import example.domain.Account;

/** set balance = 50 */
public class AccountIsInCredit extends GivenUsingMiniMock {
    
    public void setUp(World world) {
        Mock account = (Mock) world.get("account", mock(Account.class));
        account.stubs("getBalance").withNoArguments().will(returnValue(50));
    }
}