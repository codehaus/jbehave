/*
 * Created on 23-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */

package example.atm.stories;

import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.ScenarioDrivenStory;
import example.atm.scenarios.HappyScenario;
import example.atm.scenarios.HappyScenarioWithOverdraft;
import example.atm.scenarios.InLotsOfTrouble;
import example.atm.scenarios.OverdrawnWithoutPermission;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class UserWithdrawsCash extends ScenarioDrivenStory {

    public UserWithdrawsCash() {
        super(new Narrative(
            "Bank card holder",
            "to be able to withdraw cash from an ATM",
            "I don't have to visit the bank"
        ));
    }

    public void specify() {
        addScenario(new HappyScenario());
        addScenario(new HappyScenarioWithOverdraft());
        addScenario(new OverdrawnWithoutPermission());
        addScenario(new InLotsOfTrouble());
    }
}
