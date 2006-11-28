package example.atm.scenarios;

import jbehave.core.story.domain.Givens;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.ScenarioUsingMiniMock;
import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountHasOverdraftPermission;
import example.atm.outcomes.ATMShouldDispenseCash;
import example.atm.outcomes.ATMShouldReturnBankCardToCustomer;
import example.atm.outcomes.AccountBalanceShouldBeReduced;

public class HappyScenarioWithOverdraft extends ScenarioUsingMiniMock {

    public HappyScenarioWithOverdraft() {
        super(new Givens(
            new AccountHasOverdraftPermission()
        ), new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                ));
    }

}
