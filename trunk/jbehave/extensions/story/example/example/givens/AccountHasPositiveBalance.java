/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;


import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;
import com.thoughtworks.jbehave.extensions.story.domain.GivenUsingMiniMock;

import example.domain.Account;

/** set balance = 50 */
public class AccountHasPositiveBalance extends GivenUsingMiniMock {
    
    public void setUp(Environment environment) throws Exception {
        Mock account = (Mock) environment.get("account", mock(Account.class));
        account.stubs("getBalance").withNoArguments().will(returnValue(50));
    }
}