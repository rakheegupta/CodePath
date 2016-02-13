package com.rakhee.codepath.nytimesarticle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TimeZone;

/**
 * Created by rakhe on 2/11/2016.
 */
public class SettingsDialog extends DialogFragment {

    Date selectedDate;
    EditText etBeginDate;

    public SettingsDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        etBeginDate = (EditText) view.findViewById(R.id.etBeginDate);
        etBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                                selectedDate = new Date(year - 1900, monthOfYear, dayOfMonth);

                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                etBeginDate.setText(sdf.format(selectedDate));
                            }
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(true);
                datePicker.show();
            }
        });

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerSortOrder);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_order, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final CheckBox cbArts = (CheckBox) view.findViewById(R.id.cbArts);
        final CheckBox cbFashion = (CheckBox) view.findViewById(R.id.cbFashion);
        final CheckBox cbSports = (CheckBox) view.findViewById(R.id.cbSports);

        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> newsDeskValues = new ArrayList<String>();
                if (cbFashion.isChecked()) {
                    newsDeskValues.add("Fashion & Style");
                }
                if (cbSports.isChecked()) {
                    newsDeskValues.add("Sports");
                }
                if (cbArts.isChecked()) {
                    newsDeskValues.add("Arts");
                }

                SearchFilters filters = new SearchFilters(selectedDate, spinner.getSelectedItem().toString(), newsDeskValues);
                SharedPreferences pref = getActivity().getSharedPreferences("NYTimesArticleSearch", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("SearchFilters", (new Gson()).toJson(filters));
                editor.commit();

                dismiss();
            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public static SettingsDialog newInstance() {
        SettingsDialog frag = new SettingsDialog();
        return frag;
    }

}
