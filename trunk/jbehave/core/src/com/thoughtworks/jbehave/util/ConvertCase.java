/*
 * Created on 13-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.util;


/**
 * Method object to convert to and from CamelCase
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ConvertCase {
    private final char[] chars;
    int pos = 0;

    public ConvertCase(String string) {
        this.chars = string.toCharArray();
    }

    public ConvertCase(Class type) {
        String name = type.getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        name = name.substring(name.lastIndexOf('$') + 1);
        chars = name.toCharArray();
    }

    public ConvertCase(Object obj) {
        this(obj.getClass());
    }
    
    public String toSeparateWords() {
        StringBuffer buf = new StringBuffer();
        while (pos < chars.length) {
            int numUppercase = countUppercase();
            switch (numUppercase) {
                case 0:
                    processRegularChar(buf);
                    break;
                case 1:
                    processSingleUppercaseChar(buf);
                    break;
                case 2:
                    processTwoUppercaseChars(buf);
                    break;
                default:
                    processUppercaseWord(buf, numUppercase);
                    break;
            }
        }
        return buf.toString();
    }

    private int countUppercase() {
        int count = 0;
        while (pos + count < chars.length && Character.isUpperCase(chars[pos + count]))
            count++;
        return count;
    }
    
    private void processRegularChar(StringBuffer buf) {
        buf.append(chars[pos++]);
    }

    private void processSingleUppercaseChar(StringBuffer buf) {
        if (buf.length() > 0) {
            buf.append(' ');
        }
        buf.append(Character.toLowerCase(chars[pos++]));
    }

    private void processTwoUppercaseChars(StringBuffer buf) {
        processSingleUppercaseChar(buf);
        processSingleUppercaseChar(buf);
    }

    private void processUppercaseWord(StringBuffer buf, int numUppercase) {
        while (pos < numUppercase - 1) {
            buf.append(chars[pos++]);
        }
        processSingleUppercaseChar(buf);
    }

    public String toCamelCase() {
        StringBuffer buf = new StringBuffer();
        while (pos < chars.length) {
            if (Character.isWhitespace(chars[pos])) {
                pos++;
                if (pos < chars.length) {
                    buf.append(Character.toUpperCase(chars[pos]));
                }
            }
            else if (pos == 0) {
                buf.append(Character.toUpperCase(chars[pos]));
            }
            else {
                buf.append(chars[pos]);
            }
            pos++;
        }
        return buf.toString();
    }
}
