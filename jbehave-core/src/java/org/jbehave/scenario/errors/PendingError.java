package org.jbehave.scenario.errors;

public class PendingError extends AssertionError {

    private static final long serialVersionUID = 9038975723473227215L;

    public PendingError(String description) {
        super(description);
    }

}
