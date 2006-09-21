package jbehave.extensions.threaded;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.extensions.threaded.QueuedMiniMap;
import jbehave.extensions.threaded.WindowGrabber;
import jbehave.extensions.threaded.time.TimeoutException;


public class WindowGrabberBehaviour extends UsingMiniMock {
    
    public void shouldAddNewWindowsToAndRemoveNewWindowsFromMiniMap() {
        Mock miniMapMock = mock(QueuedMiniMap.class);
        WindowGrabber grabber = new WindowGrabber((QueuedMiniMap)miniMapMock);
        
        JFrame frame = new JFrame();
        frame.setName("frame.name");
        miniMapMock.expects("put").with("frame.name", frame);
        frame.setVisible(true);
                
        miniMapMock.expects("remove").with("frame.name");
        frame.dispose();
        waitForIdle();
        verifyMocks();
        grabber.dispose();
    }
    
    private void waitForIdle() {
        try {
            // Ensures that window grabber gets close event before mocks verified
            SwingUtilities.invokeAndWait(new Runnable() { public void run() {}});
        } catch (Exception e) {
            ensureThat(thisDoesntHappen());
        }
    }

	public void shouldGetAnyWindowFromMiniMap() throws TimeoutException {
        Mock miniMapMock = mock(QueuedMiniMap.class);
        
        WindowGrabber grabber = new WindowGrabber((QueuedMiniMap)miniMapMock);
        
        miniMapMock.expects("get").with(eq("frame.name"), eq(WindowGrabber.DEFAULT_WINDOW_TIMEOUT));
        grabber.getWindow("frame.name");
        
        verifyMocks();
        grabber.dispose();
    }
	
    private boolean thisDoesntHappen() {
		return false;
	}	
}
