package com.example.rakhee.codepathtodo;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
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

    private int mPositonPendingEdit;

    private ArrayList<String> mTodoItems;
    private ArrayAdapter<String> mTodoAdapter;
    private EditText mEtAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        populateArrayItems();

        mEtAddItem = (EditText) findViewById(R.id.etAdd);

        ListView lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(mTodoAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mTodoItems.remove(position);
                mTodoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPositonPendingEdit = position;

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EXTRA_EDIT_MESSAGE, mTodoItems.get(mPositonPendingEdit));
                startActivityForResult(intent, EDIT_MESSAGE_REQUEST_CODE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_MESSAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mTodoItems.set(mPositonPendingEdit, data.getStringExtra(EXTRA_EDIT_RESULT));
                mTodoAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    private void populateArrayItems() {
        readItems();
        mTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTodoItems);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        mTodoAdapter.add(mEtAddItem.getText().toString());
        mEtAddItem.setText("");
        writeItems();
    }
}
