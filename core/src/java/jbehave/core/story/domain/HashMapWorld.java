/*
 * Created on 21-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class HashMapWorld implements World {
    private final Map map = new HashMap();

    public Object get(String key, Object defaultValue) {
        if (!map.containsKey(key)) {
            put(key, defaultValue);
        }
        return map.get(key);
    }

    public Object get(String key) {
        return map.get(key);
    }
    
    public void put(String key, Object value) {
        map.put(key, value);
    }

    public void clear() {
        map.clear();
    }
    
    public String toString() {
        return "World containing " + map.keySet();
    }
}
