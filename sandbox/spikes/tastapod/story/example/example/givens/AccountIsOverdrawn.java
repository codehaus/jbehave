/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;

import com.thoughtworks.jbehave.extensions.story.base.GivenBase;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;

import example.domain.Account;


/**
 * set balance = -50
 */
public class AccountIsOverdrawn extends GivenBase {
    public void setUp(Environment environment) {
        Mock accountMock = (Mock)environment.get("accountMock", new Mock(Account.class));
        accountMock.stubs().method("getBalance").will(returnValue(-50));
    }

    public String getDescription() {
        return "set balance = -50";
    }
}