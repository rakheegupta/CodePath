package com.example.rakhee.codepathtodo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakhee on 12/26/2015.
 */
public class PriorityListViewAdapter extends ArrayAdapter<String> {
     public PriorityListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.priority_list_item, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.priority_list_item, null);
        }
        ImageView ivPriority = (ImageView) view.findViewById(R.id.ivPriority);
        if (position == 0) {
            ivPriority.setImageResource(R.drawable.priority_4);
        } else if (position == 1) {
            ivPriority.setImageResource(R.drawable.priority_3);
        } else if (position == 2) {
            ivPriority.setImageResource(R.drawable.priority_2);
        } else if (position == 3) {
            ivPriority.setImageResource(R.drawable.priority_1);
        }


        TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        tvPriority.setText(getItem(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditActivity)getContext()).onSetPriority(position);
            }
        });
        return view;
    }
}
