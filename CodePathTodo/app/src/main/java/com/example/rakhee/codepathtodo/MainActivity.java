package com.example.rakhee.codepathtodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayList<String> mTodoItems;
    private ArrayAdapter<String> mTodoAdapter;
    private EditText mEtAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
