/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;


import jbehave.core.minimock.Mock;
import jbehave.story.domain.GivenUsingMiniMock;
import jbehave.story.domain.World;


import example.domain.Account;

/** set balance = 50 */
public class AccountIsInCredit extends GivenUsingMiniMock {
    
    public void setUp(World world) {
        Mock account = (Mock) world.get("account", mock(Account.class));
        account.stubs("getBalance").withNoArguments().will(returnValue(50));
    }
}