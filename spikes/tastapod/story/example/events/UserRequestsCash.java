/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package events;

import com.thoughtworks.jbehave.extensions.story.base.EventBase;
import com.thoughtworks.jbehave.extensions.story.domain.Environment;



public class UserRequestsCash extends EventBase {
    public String getDescription() {
        return "user asks for 20 pounds";
    }
    
    public void occurIn(Environment environment) {
    }
}