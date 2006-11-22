/* This file was generated by SableCC (http://www.sablecc.org/). */

package jbehave.core.story.codegen.sablecc.node;

import java.util.*;
import jbehave.core.story.codegen.sablecc.analysis.*;

public final class ABenefit extends PBenefit
{
    private TSoThat _soThat_;
    private TSpace _space_;
    private PPhrase _phrase_;
    private TEndl _endl_;

    public ABenefit()
    {
    }

    public ABenefit(
        TSoThat _soThat_,
        TSpace _space_,
        PPhrase _phrase_,
        TEndl _endl_)
    {
        setSoThat(_soThat_);

        setSpace(_space_);

        setPhrase(_phrase_);

        setEndl(_endl_);

    }
    public Object clone()
    {
        return new ABenefit(
            (TSoThat) cloneNode(_soThat_),
            (TSpace) cloneNode(_space_),
            (PPhrase) cloneNode(_phrase_),
            (TEndl) cloneNode(_endl_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABenefit(this);
    }

    public TSoThat getSoThat()
    {
        return _soThat_;
    }

    public void setSoThat(TSoThat node)
    {
        if(_soThat_ != null)
        {
            _soThat_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _soThat_ = node;
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

    public PPhrase getPhrase()
    {
        return _phrase_;
    }

    public void setPhrase(PPhrase node)
    {
        if(_phrase_ != null)
        {
            _phrase_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _phrase_ = node;
    }

    public TEndl getEndl()
    {
        return _endl_;
    }

    public void setEndl(TEndl node)
    {
        if(_endl_ != null)
        {
            _endl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _endl_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_soThat_)
            + toString(_space_)
            + toString(_phrase_)
            + toString(_endl_);
    }

    void removeChild(Node child)
    {
        if(_soThat_ == child)
        {
            _soThat_ = null;
            return;
        }

        if(_space_ == child)
        {
            _space_ = null;
            return;
        }

        if(_phrase_ == child)
        {
            _phrase_ = null;
            return;
        }

        if(_endl_ == child)
        {
            _endl_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_soThat_ == oldChild)
        {
            setSoThat((TSoThat) newChild);
            return;
        }

        if(_space_ == oldChild)
        {
            setSpace((TSpace) newChild);
            return;
        }

        if(_phrase_ == oldChild)
        {
            setPhrase((PPhrase) newChild);
            return;
        }

        if(_endl_ == oldChild)
        {
            setEndl((TEndl) newChild);
            return;
        }

    }
}