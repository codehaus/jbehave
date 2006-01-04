package jbehave.extensions.harness.time;

public interface Timeouter {

	void start(long timeout);
	
    void checkTime() throws TimeoutException;

    long getTimeLeftIfAny();

}
