/*
 * Created on 28-Dec-2003
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for license details
 */
package jbehave.framework;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class CriteriaVerification {
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    public static final int EXCEPTION_THROWN = 2;

    private final String name;
    private final String spec;
    private final int status;
    private final Object specInstance;
    private final Throwable targetException;

    public CriteriaVerification(String name, String spec, Object specInstance, Throwable targetException) {
        this.name = name;
        this.spec = spec;
		this.specInstance = specInstance;
        this.targetException = targetException;
        if (targetException == null) {
            status = SUCCESS;
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
    public CriteriaVerification(String name, String className, Object specInstance) {
        this(name, className, specInstance, null);
    }

    public String getName() {
        return name;
    }

    public String getSpec() {
        return spec;
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

    public String toString() {
        return "Name: " + name + ", spec:" + spec + ", status: " + status + ", targetException: " + targetException;
    }

	public Object getSpecInstance() {
		return specInstance;
	}
}
