/*
 * Created on 23-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.stories;

import com.thoughtworks.jbehave.extensions.story.domain.AcceptanceCriteria;
import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.Narrative;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.ScenarioUsingMiniMock;
import com.thoughtworks.jbehave.extensions.story.domain.Story;

import example.events.UserRequestsCash;
import example.expectations.ATMShouldDispenseCash;
import example.expectations.ATMShouldRefuseCash;
import example.expectations.ATMShouldRetainBankCard;
import example.expectations.ATMShouldReturnBankCardToCustomer;
import example.expectations.AccountBalanceShouldBeReduced;
import example.givens.AccountHasNegativeBalanceWithoutPermission;
import example.givens.AccountHasOverdraftPermission;
import example.givens.AccountHasPositiveBalance;
import example.givens.AccountIsOverOverdraftLimit;

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
                new Context(
                    new AccountHasPositiveBalance()
                ),
                new UserRequestsCash(),
                new Outcome(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            )
        );
        
        addScenario(new ScenarioUsingMiniMock(
                "Happy scenario with overdraft", this,
                new Context(
                    new AccountHasOverdraftPermission()
                ),
                new UserRequestsCash(),
                new Outcome(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            )
        );
        
        addScenario(new ScenarioUsingMiniMock(
                "Overdrawn without permission", this,
                new Context(
                        scenario("Happy scenario with overdraft"),
                    new AccountHasNegativeBalanceWithoutPermission()
                ),
                new UserRequestsCash(),
                new Outcome(
                    new ATMShouldRefuseCash(),
                    new ATMShouldReturnBankCardToCustomer()
                )
            )                
        );
        
        addScenario(new ScenarioUsingMiniMock(
                "In lots of trouble", this,
                new Context(
                    new AccountIsOverOverdraftLimit()
                ),
                new UserRequestsCash(),
                new Outcome(
                    new ATMShouldRefuseCash(),
                    new ATMShouldRetainBankCard()
                )
            )
        );
    }
}
