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

import events.UserRequestsCash;
import expectations.ATMShouldDispenseCash;
import expectations.ATMShouldRetainBankCard;
import expectations.AccountBalanceShouldBeReduced;
import expectations.ATMShouldRefuseCash;
import expectations.ATMShouldReturnBankCard;
import givens.AccountHasOverdraftFacility;
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
                "Happy story", this,
                new SimpleContext(
                    new AccountIsInCredit()
                ),
                new UserRequestsCash(),
                new SimpleOutcome(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCard(),
                    new AccountBalanceShouldBeReduced()
                )
            )
        );
        
        addScenario(new SimpleScenario(
                "Happy story with overdraft", this,
                new SimpleContext(
                    new AccountHasOverdraftFacility()
                ),
                new UserRequestsCash(),
                new SimpleOutcome(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCard(),
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
                new UserRequestsCash(),
                new SimpleOutcome(
                    new ATMShouldRefuseCash(),
                    new ATMShouldReturnBankCard()
                )
            )                
        );
        
        addScenario(new SimpleScenario(
                "In lots of trouble", this,
                new SimpleContext(
                    new AccountIsOverOverdraftLimit()
                ),
                new UserRequestsCash(),
                new SimpleOutcome(
                    new ATMShouldRefuseCash(),
                    new ATMShouldRetainBankCard()
                )
            )
        );
    }
}
