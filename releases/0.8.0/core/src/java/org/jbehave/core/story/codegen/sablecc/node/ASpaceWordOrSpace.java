/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jbehave.core.story.codegen.sablecc.node;

import org.jbehave.core.story.codegen.sablecc.analysis.Analysis;

public final class ASpaceWordOrSpace extends PWordOrSpace
{
    private TSpace _space_;

    public ASpaceWordOrSpace()
    {
    }

    public ASpaceWordOrSpace(
        TSpace _space_)
    {
        setSpace(_space_);

    }
    public Object clone()
    {
        return new ASpaceWordOrSpace(
            (TSpace) cloneNode(_space_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASpaceWordOrSpace(this);
    }

    public TSpace getSpace()
    {
        return _space_;
    }

    public void setSpace(TSpace node)
    {
        if(_space_ != null)
        {
            _space_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _space_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_space_);
    }

    void removeChild(Node child)
    {
        if(_space_ == child)
        {
            _space_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_space_ == oldChild)
        {
            setSpace((TSpace) newChild);
            return;
        }

    }
}
