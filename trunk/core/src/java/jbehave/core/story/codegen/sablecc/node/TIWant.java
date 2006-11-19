/* This file was generated by SableCC (http://www.sablecc.org/). */

package jbehave.core.story.codegen.sablecc.node;

import jbehave.core.story.codegen.sablecc.analysis.*;

public final class TIWant extends Token
{
    public TIWant()
    {
        super.setText("I want");
    }

    public TIWant(int line, int pos)
    {
        super.setText("I want");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TIWant(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTIWant(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TIWant text.");
    }
}
