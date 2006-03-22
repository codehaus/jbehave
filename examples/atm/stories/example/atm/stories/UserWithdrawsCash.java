/*
 * Created on 23-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */

package example.atm.stories;

import jbehave.core.story.domain.GivenScenario;
import jbehave.core.story.domain.Givens;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.ScenarioDrivenStory;
import jbehave.core.story.domain.ScenarioUsingMiniMock;

import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountHasNegativeBalanceWithoutPermission;
import example.atm.givens.AccountHasOverdraftPermission;
import example.atm.givens.AccountIsInCredit;
import example.atm.givens.AccountIsOverOverdraftLimit;
import example.atm.outcomes.ATMShouldDispenseCash;
import example.atm.outcomes.ATMShouldRefuseCash;
import example.atm.outcomes.ATMShouldRetainBankCard;
import example.atm.outcomes.ATMShouldReturnBankCardToCustomer;
import example.atm.outcomes.AccountBalanceShouldBeReduced;

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

        addScenario(new ScenarioUsingMiniMock(
                "Happy scenario",
                getClass().getName(),
                new Givens(
                    new AccountIsInCredit()
                ),
                new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            )
        );
        
        addScenario(new ScenarioUsingMiniMock(
                "Happy scenario with overdraft", getClass().getName(),
                new Givens(
                    new AccountHasOverdraftPermission()
                ),
                new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            )
        );
        
        addScenario(new ScenarioUsingMiniMock(
                "Overdrawn without permission",
                getClass().getName(),
                new Givens(
                    new GivenScenario(scenario("Happy scenario with overdraft")),
                    new AccountHasNegativeBalanceWithoutPermission()
                ),
                new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldRefuseCash(),
                    new ATMShouldReturnBankCardToCustomer()
                )
            )                
        );
        
        addScenario(new ScenarioUsingMiniMock(
                "In lots of trouble",
                getClass().getName(),
                new Givens(
                    new AccountIsOverOverdraftLimit()
                ),
                new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldRefuseCash(),
                    new ATMShouldRetainBankCard()
                )
            )
        );
    }
}
