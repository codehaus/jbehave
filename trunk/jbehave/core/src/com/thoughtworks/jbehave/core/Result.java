/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;


/**
 * Represents the result of verifying an individual behaviour.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class Result {
    public static class ResultType {
        private final String description;
        private final char symbol;

        protected ResultType(String description, char symbol) {
            this.description = description;
            this.symbol = symbol;
        }
        public String toString() {
            return description;
        }
        public char getSymbol() {
            return symbol;
        }
    }

    public static final ResultType SUCCEEDED = new ResultType("Succeeded", '.');
    public static final ResultType FAILED = new ResultType("Failed", 'F');
    public static final ResultType THREW_EXCEPTION = new ResultType("Threw Exception", 'E');
    public static final ResultType PENDING = new ResultType("Pending", 'P');

    private final String name;
    private final ResultType status;
    private final Throwable cause;

    /**
     * Constructor for result that contains an exception
     */
    public Result(String name, Throwable cause) {
        this.name = name;
        this.cause = cause;
        this.status = getStatusFromCause(cause);
    }

    private static ResultType getStatusFromCause(Throwable cause) {
        if (cause == null) {
            return SUCCEEDED;
        }
        else if (cause instanceof PendingException) {
            return PENDING;
        }
        else if (cause instanceof VerificationException) {
            return FAILED;
        }
        else {
            return THREW_EXCEPTION;
        }
    }

    /**
     * Constructor that sets status independently of cause
     */
    public Result(String name, ResultType status) {
        this.name = name;
        this.cause = null;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public ResultType getStatus() {
        return status;
    }

    public Throwable getCause() {
        return cause;
    }

    public boolean succeeded() {
        return status == SUCCEEDED;
    }

    public boolean failed() {
        return status == FAILED;
    }

    public boolean threwException() {
        return status == THREW_EXCEPTION;
    }

    public boolean isPending() {
        return status == PENDING;
    }

	public String toString() {
        return "Name: " + name + ", status: " + status + ", targetException: " + cause;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != getClass()) return false;

        Result other = (Result) o;
        return ((name == null ? other.name == null : name.equals(other.name))
            && (status == other.status)
            && (cause == null ? other.cause == null : cause.equals(other.cause)));
    }

    /**
     * Override hashCode because we implemented {@link #equals(Object)}
     */
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
        hashCode = 31 * hashCode + status.hashCode();
        hashCode = 31 * hashCode + (cause == null ? 0 : cause.hashCode());
        return hashCode;
    }
}
