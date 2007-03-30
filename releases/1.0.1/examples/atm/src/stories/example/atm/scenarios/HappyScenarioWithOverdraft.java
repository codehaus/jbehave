package example.atm.scenarios;

import org.jbehave.core.story.domain.MultiStepScenario;

import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountHasOverdraftPermission;
import example.atm.outcomes.ATMShouldDispenseCash;
import example.atm.outcomes.ATMShouldReturnBankCardToCustomer;
import example.atm.outcomes.AccountBalanceShouldBeReduced;

public class HappyScenarioWithOverdraft extends MultiStepScenario {

    public void specifySteps() {
        given(new AccountHasOverdraftPermission());
        when(new UserRequestsCash());
        then(new ATMShouldDispenseCash());
        then(new ATMShouldReturnBankCardToCustomer());
        then(new AccountBalanceShouldBeReduced());
    }
}
