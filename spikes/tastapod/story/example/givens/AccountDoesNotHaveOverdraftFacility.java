/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package givens;

import banking.Account;

import com.thoughtworks.jbehave.extensions.story.base.GivenBase;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;


/** set overdraft limit = 0 */
public class AccountDoesNotHaveOverdraftFacility extends GivenBase {

    public void setUp(Environment context) {
        Mock account = (Mock) context.get("accountMock", new Mock(Account.class));
        context.put("account", account.proxy());
        account.stubs().method("getOverdraftLimit").withNoArguments().will(returnValue(0));
    }

    public String getDescription() {
        return "set overdraft limit = 0";
    }
}