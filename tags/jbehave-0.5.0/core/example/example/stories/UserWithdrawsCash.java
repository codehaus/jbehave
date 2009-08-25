/*
 * Created on 23-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */

package example.stories;

import jbehave.story.domain.AcceptanceCriteria;
import jbehave.story.domain.GivenScenario;
import jbehave.story.domain.Givens;
import jbehave.story.domain.Narrative;
import jbehave.story.domain.Outcomes;
import jbehave.story.domain.ScenarioDrivenStory;
import jbehave.story.domain.ScenarioUsingMiniMock;

import example.events.UserRequestsCash;
import example.givens.AccountHasNegativeBalanceWithoutPermission;
import example.givens.AccountHasOverdraftPermission;
import example.givens.AccountIsInCredit;
import example.givens.AccountIsOverOverdraftLimit;
import example.outcomes.ATMShouldDispenseCash;
import example.outcomes.ATMShouldRefuseCash;
import example.outcomes.ATMShouldRetainBankCard;
import example.outcomes.ATMShouldReturnBankCardToCustomer;
import example.outcomes.AccountBalanceShouldBeReduced;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class UserWithdrawsCash extends ScenarioDrivenStory {

    public UserWithdrawsCash() {
        super(
                new Narrative(
                    "Bank card holder",
                    "to be able to withdraw cash from an ATM",
                    "I don't have to visit the bank"
                ),
                new AcceptanceCriteria()
        );

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
