package example.atm.scenarios;

import org.jbehave.core.story.domain.MultiStepScenario;

import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountIsOverOverdraftLimit;
import example.atm.outcomes.ATMShouldRefuseCash;
import example.atm.outcomes.ATMShouldRetainBankCard;

public class InLotsOfTrouble extends MultiStepScenario {

    public void specifySteps() {
        given(new AccountIsOverOverdraftLimit());
        when(new UserRequestsCash());
        then(new ATMShouldRefuseCash());
        then(new ATMShouldRetainBankCard());
    }

}
