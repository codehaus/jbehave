/*
 * Created on 23-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.stories;

import com.thoughtworks.jbehave.extensions.story.base.Story;
import com.thoughtworks.jbehave.extensions.story.domain.Context;
import com.thoughtworks.jbehave.extensions.story.domain.Outcome;
import com.thoughtworks.jbehave.extensions.story.domain.Scenario;

import example.events.UserRequestsCash;
import example.expectations.ATMShouldDispenseCash;
import example.expectations.ATMShouldRefuseCash;
import example.expectations.ATMShouldRetainBankCard;
import example.expectations.ATMShouldReturnBankCardToCustomer;
import example.expectations.AccountBalanceShouldBeReduced;
import example.givens.AccountHasNegativeBalanceWithoutPermission;
import example.givens.AccountHasOverdraftPermission;
import example.givens.AccountHasEnoughCash;
import example.givens.AccountIsOverOverdraftLimit;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class UserWithdrawsCash extends Story {

    public UserWithdrawsCash() {
        super(
                "Bank card holder",
                "to be able to withdraw cash from an ATM",
                "I don't have to visit the bank"
        );

        addScenario(new Scenario(
                "Happy scenario", this,
                new Context(
                    new AccountHasEnoughCash()
                ),
                new UserRequestsCash(),
                new Outcome(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            )
        );
        
        addScenario(new Scenario(
                "Happy story with overdraft", this,
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
        
        addScenario(new Scenario(
                "Overdrawn without permission", this,
                new Context(
                        getScenario("Happy story with overdraft"),
                    new AccountHasNegativeBalanceWithoutPermission()
                ),
                new UserRequestsCash(),
                new Outcome(
                    new ATMShouldRefuseCash(),
                    new ATMShouldReturnBankCardToCustomer()
                )
            )                
        );
        
        addScenario(new Scenario(
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
