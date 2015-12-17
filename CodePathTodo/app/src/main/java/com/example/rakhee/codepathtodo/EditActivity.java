package com.example.rakhee.codepathtodo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

public class EditActivity extends ActionBarActivity {

    private EditText etEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String message = getIntent().getStringExtra(MainActivity.EXTRA_EDIT_MESSAGE);

        etEdit = (EditText) findViewById(R.id.etEdit);
        etEdit.setText(message);
    }

    public void onSaveClicked(View view) {
        // Close activity and send back new text
        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.EXTRA_EDIT_RESULT, etEdit.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
