package example.atm.scenarios;

import jbehave.core.story.domain.MultiStepScenario;
import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountIsInCredit;
import example.atm.outcomes.ATMShouldDispenseCash;
import example.atm.outcomes.ATMShouldReturnBankCardToCustomer;
import example.atm.outcomes.AccountBalanceShouldBeReduced;

public class HappyScenario extends MultiStepScenario {

    public void assemble() {
        given(new AccountIsInCredit());
        when(new UserRequestsCash());
        then(new ATMShouldDispenseCash());
        then(new ATMShouldReturnBankCardToCustomer());
        then(new AccountBalanceShouldBeReduced());
    }
}
