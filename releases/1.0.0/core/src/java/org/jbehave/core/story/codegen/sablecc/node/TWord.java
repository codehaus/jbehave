/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jbehave.core.story.codegen.sablecc.node;

import org.jbehave.core.story.codegen.sablecc.analysis.Analysis;

public final class TWord extends Token
{
    public TWord(String text)
    {
        setText(text);
    }

    public TWord(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TWord(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTWord(this);
    }
}
