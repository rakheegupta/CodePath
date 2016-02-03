package com.example.rakhee.codepathtodo;

import android.widget.ListView;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Rakhee on 12/23/2015.
 */
@Table(name = "Items")
public class Item extends Model implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;

    @Column(name = "mText", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String mText;

    @Column(name = "mIsSelected")
    public boolean mIsSelected;

    @Column(name = "mCompletionDate")
    public Date mCompletionDate;

    public int getmPriority() {
        return mPriority;
    }

    @Column(name = "mPriority")
    public int mPriority;

    @Column(name = "mNotificationShown")
    public boolean mNotificationShown;

    public static List<Item> getAll() {
        return new Select().all().from(Item.class).execute();
    }
}
