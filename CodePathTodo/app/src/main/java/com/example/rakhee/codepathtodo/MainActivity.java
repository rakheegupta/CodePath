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
import android.widget.TextView;
import android.widget.Toolbar;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_EDIT_MESSAGE = "com.example.rakhee.codepathtodo.MainActivity.EXTRA_EDIT_MESSAGE";
    public final static String EXTRA_EDIT_RESULT = "com.example.rakhee.codepathtodo.MainActivity.EXTRA_EDIT_RESULT";
    public final static int EDIT_MESSAGE_REQUEST_CODE = 27;
    public final static int Add_MESSAGE_REQUEST_CODE = 37;

    private int mPositonPendingEdit;

    private ArrayList<Item> mTodoItems;
    private ListViewAdapter mTodoAdapter;

    public boolean mInEditMode;

    MenuItem mMenuEdit;
    MenuItem mMenuDelete;

    TextView tvNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        getWindow().setStatusBarColor(Color.parseColor("#43B3C4"));

        mInEditMode = false;

        populateArrayItems();

        ListView lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(mTodoAdapter);

        tvNoItems = (TextView) findViewById(R.id.tvNoItems);
        if (mTodoItems.size() > 0) {
            tvNoItems.setVisibility(View.GONE);
        }

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
        Item itemToDelete = mTodoItems.get(position);
        mTodoItems.remove(position);
        mTodoAdapter.notifyDataSetChanged();
        itemToDelete.delete();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Item newItem = (Item) data.getSerializableExtra(EXTRA_EDIT_RESULT);
            if (requestCode == EDIT_MESSAGE_REQUEST_CODE) {
                mTodoItems.set(mPositonPendingEdit, newItem);
                mTodoAdapter.notifyDataSetChanged();
            }
            else if (requestCode == Add_MESSAGE_REQUEST_CODE) {
                mTodoAdapter.add(newItem);
            }
            newItem.save();
            tvNoItems.setVisibility(View.GONE);
        }
    }

    private void populateArrayItems() {
        mTodoItems = new ArrayList<>();
        readItems();
        mTodoAdapter = new ListViewAdapter(this, mTodoItems);
    }

    private void readItems() {
        try{
            List<Item> items = Item.getAll();
            mTodoItems.addAll(items);
        }
        catch (Exception ex){

        }
    }

    private void writeItems() {
//        File fileDir = getFilesDir();
//        File file = new File(fileDir, "todo.txt");
//        try{
//            ArrayList<String> items = new ArrayList<>();
//            for (Item item: mTodoItems) {
//                items.add(item.mText);
//            }
//            FileUtils.writeLines(file, items);
//        }
//        catch (Exception ex){
//
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("onCreateOptionsMenu");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mMenuEdit = menu.findItem(R.id.edit);
        mMenuDelete = menu.findItem(R.id.delete);

        boolean somethingSelected = false;
        for (Item item: mTodoItems) {
            if (item.mIsSelected) {
                somethingSelected = true;
                break;
            }
        }

        mMenuEdit.setVisible(!somethingSelected);
        mMenuDelete.setVisible(somethingSelected);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.edit) {
            mInEditMode = !mInEditMode;
            mTodoAdapter.notifyDataSetChanged();
        }
        else if (id == R.id.delete) {
            for (int i = mTodoItems.size() - 1; i >= 0; i--) {
                Item item = mTodoItems.get(i);
                if(item.mIsSelected) {
                    Item itemToDelete = mTodoItems.get(i);
                    mTodoItems.remove(i);
                    itemToDelete.delete();
                }
            }
            mTodoAdapter.notifyDataSetChanged();
            mInEditMode = false;
            mMenuEdit.setVisible(true);
            mMenuDelete.setVisible(false);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public void onAddClicked(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivityForResult(intent, Add_MESSAGE_REQUEST_CODE);
        overridePendingTransition(R.anim.enter_from_bottom, R.anim.stay_in_place);
    }

    public void onRefreshMenu() {
        System.out.println("Calling invalidateOptionsMenu");
        invalidateOptionsMenu();

        boolean somethingSelected = false;
        for (Item item: mTodoItems) {
            if (item.mIsSelected) {
                somethingSelected = true;
                break;
            }
        }

        mMenuEdit.setVisible(!somethingSelected);
        mMenuDelete.setVisible(somethingSelected);
    }

}
