/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.givens;

import com.thoughtworks.jbehave.extensions.story.base.Given;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;


/** balance = -50, overdraft limit = 0 */
public class AccountHasNegativeBalanceWithoutPermission extends Given {
    public void setUp(Environment environment) {
        new AccountIsOverdrawn().setUp(environment);
        new AccountDoesNotHaveOverdraftFacility().setUp(environment);
    }

    public String getDescription() {
        return "balance = -50, overdraft limit = 0";
    }
}