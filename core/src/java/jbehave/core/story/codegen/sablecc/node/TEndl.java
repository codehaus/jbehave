/* This file was generated by SableCC (http://www.sablecc.org/). */

package jbehave.core.story.codegen.sablecc.node;

import jbehave.core.story.codegen.sablecc.analysis.*;

public final class TEndl extends Token
{
    public TEndl(String text)
    {
        setText(text);
    }

    public TEndl(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TEndl(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTEndl(this);
    }
}