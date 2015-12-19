package com.example.rakhee.codepathtodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rakhee on 12/18/2015.
 */
public class ListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> mTodoItems;

    public ListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.list_item, items);
        mTodoItems = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }

        SwipeLayout slItem = (SwipeLayout) view.findViewById(R.id.slItem);
        slItem.setShowMode(SwipeLayout.ShowMode.LayDown);
        slItem.setDragEdge(SwipeLayout.DragEdge.Right);
        slItem.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xVal, float yVal) {

            }
        });

        LinearLayout llDelete = (LinearLayout) view.findViewById(R.id.llDelete);
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).onDeleteItem(position);
            }
        });

        String item = getItem(position);
        TextView tvItem = (TextView) view.findViewById(R.id.tvItem);
        tvItem.setText(item);

        return view;
    }
}
