/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package jbehave.framework;

import jbehave.framework.exception.PendingException;
import jbehave.framework.exception.VerificationException;


/**
 * Represents the result of verifying an individual responsibility.
 * 
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class ResponsibilityVerification {
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    public static final int EXCEPTION_THROWN = 2;
    public static final int PENDING = 3;

    private final String name;
    private final String behaviourClassName;
    private final int status;
    private final Throwable targetException;

    public ResponsibilityVerification(String behaviourClassName, String responsibilityMethodName, Throwable targetException) {
        this.name = responsibilityMethodName;
        this.behaviourClassName = behaviourClassName;
        this.targetException = targetException;
        if (targetException == null) {
            status = SUCCESS;
        }
        else if (targetException instanceof PendingException) {
            status = PENDING;
        }
        else if (targetException instanceof VerificationException) {
            status = FAILURE;
        }
        else {
            status = EXCEPTION_THROWN;
        }
    }

    /**
     * Convenience constructor for successful verification
     */
    public ResponsibilityVerification(String behaviourClassName, String methodName) {
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

    public Throwable getTargetException() {
        return targetException;
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

    public boolean pending() {
        return status == PENDING;
    }

	public String toString() {
        return "Name: " + name + ", behaviour class:" + behaviourClassName + ", status: " + status + ", targetException: " + targetException;
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
        ResponsibilityVerification other = (ResponsibilityVerification) o;
        return ((name == null ? other.name == null : name.equals(other.name))
            && (behaviourClassName == null ? other.behaviourClassName == null : behaviourClassName.equals(other.behaviourClassName))
            && (status == other.status)
            && (targetException == null ? other.targetException == null : targetException.equals(other.targetException)));
    }

    /**
     * Override hashCode because we implemented {@link #equals(Object)}
     */
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
        hashCode = 31 * hashCode + (behaviourClassName == null ? 0 : behaviourClassName.hashCode());
        hashCode = 31 * hashCode + status;
        hashCode = 31 * hashCode + (targetException == null ? 0 : targetException.hashCode());
        return hashCode;
    }
}
