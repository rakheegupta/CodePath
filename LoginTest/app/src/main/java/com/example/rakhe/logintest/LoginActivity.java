package com.example.rakhe.logintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tvHeader = (TextView) findViewById(R.id.tvHeader);
        tvHeader.setText(Html.fromHtml(getString(R.string.formattedHeader)));
    }
}
