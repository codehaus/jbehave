/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.responsibility;

import com.thoughtworks.jbehave.core.exception.PendingException;
import com.thoughtworks.jbehave.core.exception.VerificationException;


/**
 * Represents the result of verifying an individual responsibility.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class Result {
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    public static final int EXCEPTION_THROWN = 2;
    public static final int PENDING = 3;

    private final String name;
    private final String behaviourClassName;
    private final int status;
    private final Throwable cause;

    /**
     * Constructor for result that contains an exception
     */
    public Result(String behaviourClassName, String responsibilityMethodName, Throwable cause) {
        this.name = responsibilityMethodName;
        this.behaviourClassName = behaviourClassName;
        this.cause = cause;
        if (cause == null) {
            status = SUCCESS;
        }
        else if (cause instanceof PendingException) {
            status = PENDING;
        }
        else if (cause instanceof VerificationException) {
            status = FAILURE;
        }
        else {
            status = EXCEPTION_THROWN;
        }
    }

    /**
     * Convenience constructor for successful verification
     */
    public Result(String behaviourClassName, String methodName) {
        this(behaviourClassName, methodName, null);
    }

    public String getName() {
        return name;
    }

    public String getBehaviourClassName() {
        return behaviourClassName;
    }

    public int getStatus() {
        return status;
    }

    public Throwable getCause() {
        return cause;
    }

    public boolean succeeded() {
        return status == SUCCESS;
    }

    public boolean failed() {
        return status == FAILURE;
    }

    public boolean threwException() {
        return status == EXCEPTION_THROWN;
    }

    public boolean isPending() {
        return status == PENDING;
    }

	public String toString() {
        return "Name: " + name + ", behaviour class:" + behaviourClassName + ", status: " + status + ", targetException: " + cause;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        Result other = (Result) o;
        return ((name == null ? other.name == null : name.equals(other.name))
            && (behaviourClassName == null ? other.behaviourClassName == null : behaviourClassName.equals(other.behaviourClassName))
            && (status == other.status)
            && (cause == null ? other.cause == null : cause.equals(other.cause)));
    }

    /**
     * Override hashCode because we implemented {@link #equals(Object)}
     */
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
        hashCode = 31 * hashCode + (behaviourClassName == null ? 0 : behaviourClassName.hashCode());
        hashCode = 31 * hashCode + status;
        hashCode = 31 * hashCode + (cause == null ? 0 : cause.hashCode());
        return hashCode;
    }
}
