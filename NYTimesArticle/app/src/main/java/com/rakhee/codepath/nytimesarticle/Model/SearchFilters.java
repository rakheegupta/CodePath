package com.rakhee.codepath.nytimesarticle.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rakhe on 2/12/2016.
 */
public class SearchFilters {
    public Date beginDate;
    public String sortOrder;
    public ArrayList<String> newsDeskValues;

    public SearchFilters(Date date, String sortOrder, ArrayList<String> newsDeskValues) {
        this.beginDate = date;
        this.sortOrder = sortOrder;
        this.newsDeskValues = newsDeskValues;
    }
}
