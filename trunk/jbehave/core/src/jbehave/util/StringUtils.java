/*
 * Created on 13-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.util;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class StringUtils {

    public static String unCamelCase(String message) {
        StringBuffer buf = new StringBuffer();
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (Character.isUpperCase(ch)) {
                buf.append(' ').append(Character.toLowerCase(ch));
            }
            else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }
}
