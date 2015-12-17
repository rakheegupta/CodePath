package com.example.rakhee.codepathtodo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {

    private EditText etEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

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
