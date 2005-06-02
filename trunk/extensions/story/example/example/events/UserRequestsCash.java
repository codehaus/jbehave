/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.events;

import com.thoughtworks.jbehave.story.domain.World;
import com.thoughtworks.jbehave.story.domain.EventUsingMiniMock;


public class UserRequestsCash extends EventUsingMiniMock {
    
    public void occurIn(World world) {

    }

    public String toString() {
        return "user asks for 20 pounds";
    }
}