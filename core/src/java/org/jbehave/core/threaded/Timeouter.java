package org.jbehave.core.threaded;

public interface Timeouter {

	void start(long timeout);
	
    void checkTime() throws TimeoutException;

    long getTimeLeftIfAny();

}
