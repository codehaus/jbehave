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
public class BehaviourResult {
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    public static final int EXCEPTION_THROWN = 2;
    
    private final String name;
    private final String className;
    private final int status;
    private final Object executedInstance;
    private final Throwable targetException;

    public BehaviourResult(String name, String className, Object executedInstance, Throwable targetException) {
        this.name = name;
        this.className = className;
		this.executedInstance = executedInstance;
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
     * Convenience constructor for successful behaviours
     */
    public BehaviourResult(String name, String className, Object executedInstance) {
        this(name, className, executedInstance, null);
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
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

    public boolean exceptionThrown() {
        return status == EXCEPTION_THROWN;
    }
    
    public String toString() {
        return "Name: " + name + ", class:" + className + ", status: " + status + ", targetException: " + targetException;
    }

	public Object getExecutedInstance() {
		return executedInstance;
	}
}
