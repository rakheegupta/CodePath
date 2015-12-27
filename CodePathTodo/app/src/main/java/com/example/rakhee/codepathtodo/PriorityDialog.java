package com.example.rakhee.codepathtodo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Rakhee on 12/26/2015.
 */
public class PriorityDialog extends DialogFragment {
    ArrayList<String> mPriorityItems;
    PriorityListViewAdapter mPriorityListViewAdapter;
    ListView lvPriorityItems;
    public PriorityDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_priority, container);
        mPriorityItems = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mPriorityItems.add("Priority " + (4 - i));
        }
        mPriorityListViewAdapter = new PriorityListViewAdapter(this.getActivity(), mPriorityItems);
        lvPriorityItems = (ListView)view.findViewById(R.id.lvPriorityItems);
        lvPriorityItems.setAdapter(mPriorityListViewAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
