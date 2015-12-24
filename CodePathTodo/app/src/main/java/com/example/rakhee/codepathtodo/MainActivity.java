package com.example.rakhee.codepathtodo;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_EDIT_MESSAGE = "com.example.rakhee.codepathtodo.MainActivity.EXTRA_EDIT_MESSAGE";
    public final static String EXTRA_EDIT_RESULT = "com.example.rakhee.codepathtodo.MainActivity.EXTRA_EDIT_RESULT";
    public final static int EDIT_MESSAGE_REQUEST_CODE = 27;
    public final static int Add_MESSAGE_REQUEST_CODE = 37;

    private int mPositonPendingEdit;

    private ArrayList<String> mTodoItems;
    private ListViewAdapter mTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        getWindow().setStatusBarColor(Color.parseColor("#43B3C4"));

        populateArrayItems();

        ListView lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(mTodoAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                onDeleteItem(position);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked(position);
            }
        });
    }

    public void onItemClicked(int position) {
        mPositonPendingEdit = position;

        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra(EXTRA_EDIT_MESSAGE, mTodoItems.get(mPositonPendingEdit));
        startActivityForResult(intent, EDIT_MESSAGE_REQUEST_CODE);

        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    public void onDeleteItem(int position) {
        mTodoItems.remove(position);
        mTodoAdapter.notifyDataSetChanged();
        writeItems();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == EDIT_MESSAGE_REQUEST_CODE) {
                mTodoItems.set(mPositonPendingEdit, data.getStringExtra(EXTRA_EDIT_RESULT));
                mTodoAdapter.notifyDataSetChanged();
            }
            else if (requestCode == Add_MESSAGE_REQUEST_CODE) {
                mTodoAdapter.add(data.getStringExtra(EXTRA_EDIT_RESULT));
            }
            writeItems();
        }
    }

    private void populateArrayItems() {
        readItems();
        mTodoAdapter = new ListViewAdapter(this, mTodoItems);
    }

    private void readItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try{
            mTodoItems = new ArrayList<>(FileUtils.readLines(file));
        }
        catch (Exception ex){
            mTodoItems = new ArrayList<>();
        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try{
            FileUtils.writeLines(file, mTodoItems);
        }
        catch (Exception ex){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAddClicked(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivityForResult(intent, Add_MESSAGE_REQUEST_CODE);
        overridePendingTransition(R.anim.enter_from_bottom, R.anim.stay_in_place);
    }

}
