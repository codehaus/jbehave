/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package givens;

import com.thoughtworks.jbehave.extensions.story.base.GivenBase;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;


/** balance = -50, overdraft limit = 100 */
public class AccountIsOverdrawnWithPermission extends GivenBase {
    public void setUp(Environment context) {
        new AccountIsOverdrawn().setUp(context);
        new AccountHasOverdraftFacility().setUp(context);
    }

    public String getDescription() {
        return "balance = -50, overdraft limit = 100";
    }
}
