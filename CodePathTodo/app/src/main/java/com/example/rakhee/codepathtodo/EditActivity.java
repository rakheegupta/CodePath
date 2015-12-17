package com.example.rakhee.codepathtodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class EditActivity extends ActionBarActivity {

    private EditText etEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        String message = getIntent().getStringExtra(MainActivity.EXTRA_EDIT_MESSAGE);
        if (message != null) {
            etEdit.setText(message);
        }
        else {
            // This is add activity
            getActionBar().setTitle(getString(R.string.add_item_title));

            TextView tvHeader = (TextView) findViewById(R.id.textView);
            tvHeader.setText(getString(R.string.add_item_header));
        }
    }

    public void onSaveClicked(View view) {
        // Close activity and send back new text
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.EXTRA_EDIT_RESULT, etEdit.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
