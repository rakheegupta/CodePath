package com.codepath.apps.simpleTweeter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.simpleTweeter.R;

/**
 * Created by rakhe on 2/26/2016.
 */
public class EmptyFragment  extends Fragment{
    public static EmptyFragment newInstance() {

        Bundle args = new Bundle();

        EmptyFragment fragment = new EmptyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
        return view;
    }
}
