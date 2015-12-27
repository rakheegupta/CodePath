package com.example.rakhee.codepathtodo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Rakhee on 12/23/2015.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    public String mText;
    public boolean mIsSelected;
    public Date mCompletionDate;
    public int mPriority;
}
