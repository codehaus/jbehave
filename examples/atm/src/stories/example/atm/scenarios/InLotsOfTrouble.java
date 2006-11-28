package example.atm.scenarios;

import jbehave.core.story.domain.Givens;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.ScenarioUsingMiniMock;
import example.atm.events.UserRequestsCash;
import example.atm.givens.AccountIsOverOverdraftLimit;
import example.atm.outcomes.ATMShouldRefuseCash;
import example.atm.outcomes.ATMShouldRetainBankCard;

public class InLotsOfTrouble extends ScenarioUsingMiniMock {

    public InLotsOfTrouble() {
        super(
                new Givens(
                    new AccountIsOverOverdraftLimit()
                ),
                new UserRequestsCash(),
                new Outcomes(
                    new ATMShouldRefuseCash(),
                    new ATMShouldRetainBankCard()
                )
            );
    }

}
