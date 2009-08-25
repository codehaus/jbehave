/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.atm.outcomes;

import org.jbehave.core.Ensure;
import org.jbehave.core.minimock.story.domain.OutcomeUsingMiniMock;
import org.jbehave.core.story.domain.World;



/** balance = balance - 20 */
public class AccountBalanceShouldBeReduced extends OutcomeUsingMiniMock {

    public void verify(World world) {
        Ensure.pending();
    }
}