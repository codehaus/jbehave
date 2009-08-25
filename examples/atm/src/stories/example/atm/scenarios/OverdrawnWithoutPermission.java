package example.atm.scenarios;

import org.jbehave.core.story.domain.MultiStepScenario;

import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountHasNegativeBalanceWithoutPermission;
import example.atm.outcomes.ATMShouldRefuseCash;
import example.atm.outcomes.ATMShouldReturnBankCardToCustomer;

public class OverdrawnWithoutPermission extends MultiStepScenario {

    public void specifySteps() {
        given(new HappyScenarioWithOverdraft());
        given(new AccountHasNegativeBalanceWithoutPermission());
        when(new UserRequestsCash());
        then(new ATMShouldRefuseCash());
        then(new ATMShouldReturnBankCardToCustomer());
    }
}
