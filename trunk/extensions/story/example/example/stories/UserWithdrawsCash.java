/*
 * Created on 23-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */

package example.stories;

import com.thoughtworks.jbehave.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.story.domain.Givens;
import com.thoughtworks.jbehave.story.domain.Narrative;
import com.thoughtworks.jbehave.story.domain.Outcomes;
import com.thoughtworks.jbehave.story.domain.ScenarioUsingMiniMock;
import com.thoughtworks.jbehave.story.domain.Story;

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
public class UserWithdrawsCash extends Story {

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
                "Happy scenario", this,
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
                "Happy scenario with overdraft", this,
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
                "Overdrawn without permission", this,
                new Givens(
                        scenario("Happy scenario with overdraft"),
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
                "In lots of trouble", this,
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
