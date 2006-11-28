package example.atm.scenarios;

import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountIsInCredit;
import example.atm.outcomes.ATMShouldDispenseCash;
import example.atm.outcomes.ATMShouldReturnBankCardToCustomer;
import example.atm.outcomes.AccountBalanceShouldBeReduced;
import jbehave.core.story.domain.Givens;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.ScenarioUsingMiniMock;


public class HappyScenario extends ScenarioUsingMiniMock {

    public HappyScenario() {
        super(new Givens(
            new AccountIsInCredit()
        ),
                new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldDispenseCash(),
                    new ATMShouldReturnBankCardToCustomer(),
                    new AccountBalanceShouldBeReduced()
                )
            );
    }
    

}
