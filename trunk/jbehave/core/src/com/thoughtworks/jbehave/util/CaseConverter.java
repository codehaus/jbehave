/*
 * Created on 13-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.util;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class CaseConverter {
    
    public String toSeparateWords(String string) {
        StringBuffer buf = new StringBuffer();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isUpperCase(ch) && i < chars.length - 1 && !Character.isUpperCase(chars[i+1])) {
                if (i > 0) {
                    buf.append(' ');
                }
                buf.append(Character.toLowerCase(ch));
            }
            else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    public String toSeparateWords(Class type) {
        String name = type.getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        name = name.substring(name.lastIndexOf('$') + 1);
        return new CaseConverter().toSeparateWords(name);
    }

    public String toSeparateWords(Object obj) {
        return toSeparateWords(obj.getClass());
    }

    public String toCamelCase(String string) {
        StringBuffer buf = new StringBuffer();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isWhitespace(ch)) {
                i++;
                if (i < chars.length) {
                    buf.append(Character.toUpperCase(chars[i]));
                }
            }
            else if (i == 0) {
                buf.append(Character.toUpperCase(ch));
            }
            else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }
}
