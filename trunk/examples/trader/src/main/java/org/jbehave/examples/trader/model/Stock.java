package org.jbehave.examples.trader.model;

import static org.jbehave.examples.trader.model.Stock.AlertStatus.OFF;
import static org.jbehave.examples.trader.model.Stock.AlertStatus.ON;

public class Stock {

    public enum AlertStatus {
        ON, OFF
    };

    private double price;
    private double alertPrice;
    private AlertStatus status = OFF;

    public Stock(double price, double alertPrice) {
        this.price = price;
        this.alertPrice = alertPrice;
    }

    public double getPrice() {
        return price;
    }

    public void tradeAt(double price) {
        this.price = price;
        if (price > alertPrice) {
            status = ON;
        }
    }

    public void resetAlert() {
        status = OFF;
    }

    public AlertStatus getStatus() {
        return status;
    }

}
