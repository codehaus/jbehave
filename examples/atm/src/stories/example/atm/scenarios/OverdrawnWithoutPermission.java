package example.atm.scenarios;

import jbehave.core.story.domain.GivenScenario;
import jbehave.core.story.domain.Givens;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.ScenarioUsingMiniMock;
import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountHasNegativeBalanceWithoutPermission;
import example.atm.outcomes.ATMShouldRefuseCash;
import example.atm.outcomes.ATMShouldReturnBankCardToCustomer;

public class OverdrawnWithoutPermission extends ScenarioUsingMiniMock {

    public OverdrawnWithoutPermission() {
        super(new Givens(
            new GivenScenario(new HappyScenarioWithOverdraft()),
            new AccountHasNegativeBalanceWithoutPermission()
        ),
                new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldRefuseCash(),
                    new ATMShouldReturnBankCardToCustomer()
                )
            ); 
    }

}
