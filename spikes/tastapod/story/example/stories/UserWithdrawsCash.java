/*
 * Created on 23-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package stories;

import com.thoughtworks.jbehave.extensions.story.base.StoryBase;
import com.thoughtworks.jbehave.extensions.story.domain.SimpleContext;
import com.thoughtworks.jbehave.extensions.story.domain.SimpleOutcome;
import com.thoughtworks.jbehave.extensions.story.domain.SimpleScenario;

import events.UserRequestsTwentyPounds;
import expectations.ATMShouldDispenseCash;
import expectations.ATMShouldRetainBankCard;
import expectations.AccountBalanceShouldBeReduced;
import expectations.ATMShouldRefuseCash;
import expectations.ATMShouldReturnBankCardToCustomer;
import givens.AccountHasOverdraftPermission;
import givens.AccountIsInCredit;
import givens.AccountIsOverOverdraftLimit;
import givens.AccountHasNegativeBalanceWithoutPermission;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class UserWithdrawsCash extends StoryBase {

    
    public UserWithdrawsCash() {
        super(
                "Bank card holder",
                "to be able to withdraw cash from an ATM",
                "I don't have to visit the bank"
        );

        addScenario(new SimpleScenario(
                "Happy scenario", this,
                new SimpleContext(
                    new AccountIsInCredit()
                ),
                new UserRequestsTwentyPounds(),
                new SimpleOutcome(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            )
        );
        
        addScenario(new SimpleScenario(
                "Happy story with overdraft", this,
                new SimpleContext(
                    new AccountHasOverdraftPermission()
                ),
                new UserRequestsTwentyPounds(),
                new SimpleOutcome(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            )        
        );
        
        addScenario(new SimpleScenario(
                "Overdrawn without permission", this,
                new SimpleContext(
                        getScenario("Happy story with overdraft"),
                    new AccountHasNegativeBalanceWithoutPermission()
                ),
                new UserRequestsTwentyPounds(),
                new SimpleOutcome(
                    new ATMShouldRefuseCash(),
                    new ATMShouldReturnBankCardToCustomer()
                )
            )                
        );
        
        addScenario(new SimpleScenario(
                "In lots of trouble", this,
                new SimpleContext(
                    new AccountIsOverOverdraftLimit()
                ),
                new UserRequestsTwentyPounds(),
                new SimpleOutcome(
                    new ATMShouldRefuseCash(),
                    new ATMShouldRetainBankCard()
                )
            )
        );
    }
}
