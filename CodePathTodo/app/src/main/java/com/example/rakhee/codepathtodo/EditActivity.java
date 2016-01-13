package com.example.rakhee.codepathtodo;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Calendar;
import java.util.TimeZone;

public class EditActivity extends ActionBarActivity {

    private EditText etEdit;
    private boolean mIsAddNew;
    private TextView tvDate;
    private Item mItem;
    TextView tvPriority;
    PriorityDialog priorityDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        getWindow().setStatusBarColor(Color.parseColor("#43B3C4"));



        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etEdit = (EditText) findViewById(R.id.etEdit);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvPriority = (TextView) findViewById(R.id.tvPriority);

        mItem = (Item)getIntent().getSerializableExtra(MainActivity.EXTRA_EDIT_MESSAGE);
        mIsAddNew = (mItem == null);

        getActionBar().setTitle("");
        if (!mIsAddNew) {
            etEdit.setText(mItem.mText);
        }
        else {
            // This is add activity
            mItem = new Item();
            mItem.mCompletionDate = new Date();
            mItem.mPriority = 0;
            mItem.mNotificationShown = false;

            TextView tvHeader = (TextView) findViewById(R.id.tvEditHeader);
            tvHeader.setText(getString(R.string.add_item_header));
        }

        tvPriority.setText("Priority " + (4- mItem.mPriority));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm a");
        tvDate.setText(simpleDateFormat.format(mItem.mCompletionDate));

        etEdit.setSelection(etEdit.getText().length());
        etEdit.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onSaveClicked(View view) {
        // Close activity and send back new text
        mItem.mText = etEdit.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.EXTRA_EDIT_RESULT, mItem);
        setResult(RESULT_OK, resultIntent);
        finish();

        if (mIsAddNew) {
            overridePendingTransition(R.anim.stay_in_place, R.anim.exit_to_bottom);
        }
        else {
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
    }

    public void onDateClicked(View view) {
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int monthOfYear, final int dayOfMonth) {
                TimePickerDialog timePicker = new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mItem.mCompletionDate = new Date(year - 1900, monthOfYear, dayOfMonth, hourOfDay, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm a");
                        tvDate.setText(simpleDateFormat.format(mItem.mCompletionDate));
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
                timePicker.setCancelable(true);
                timePicker.show();
            }
        };

        DatePickerDialog datePicker = new DatePickerDialog(this,
                listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(true);
        datePicker.show();


    }

    public void onPriorityClicked(View view) {
        FragmentManager fm = getFragmentManager();
        priorityDialog = new PriorityDialog();
        priorityDialog.show(fm, "fragment_select_priority");
    }

    public void onSetPriority(int priority) {
        mItem.mPriority = priority;
        tvPriority.setText("Priority " + (4- mItem.mPriority));
        priorityDialog.dismiss();
        priorityDialog = null;
    }
}
