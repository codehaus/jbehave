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


/** set balance = -150 */
public class AccountIsOverOverdraftLimit extends Given {
    public void setUp(Environment context) {
        new AccountHasOverdraftPermission().setUp(context);
    }

    public String getDescription() {
        return "set balance = -150";
    }
}