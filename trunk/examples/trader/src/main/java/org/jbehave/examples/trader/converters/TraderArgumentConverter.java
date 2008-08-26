package org.jbehave.examples.trader.converters;

import java.lang.reflect.Type;

import org.jbehave.examples.trader.model.Trader;
import org.jbehave.examples.trader.persistence.TraderPersister;
import org.jbehave.scenario.steps.ArgumentConversion.ArgumentConverter;
import org.jbehave.scenario.steps.ArgumentConversion.InvalidArgumentException;

public class TraderArgumentConverter implements ArgumentConverter {
    private TraderPersister persister;

    public TraderArgumentConverter(TraderPersister persister) {
        this.persister = persister;
    }

    public boolean accept(Type type) {
        if (type instanceof Class) {
            return Trader.class.isAssignableFrom((Class<?>) type);
        }
        return false;
    }

    public Object convertValue(String value, Type type) {
        Trader trader = persister.retrieveTrader(value);
        if ( trader == null ){
            throw new InvalidArgumentException("Trader not found for name "+value, null);
        }
        return trader;
    }

}
