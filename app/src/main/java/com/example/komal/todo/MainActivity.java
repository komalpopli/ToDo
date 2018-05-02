package com.example.komal.todo;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
DbHelper dbHelper;
ArrayAdapter<String> arrayAdapter;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DbHelper(this);
        listView=(ListView)findViewById(R.id.list);
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList= dbHelper.getTaskList();
        if(arrayAdapter==null){
            arrayAdapter=new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            listView.setAdapter(arrayAdapter);
        }else{
            arrayAdapter.clear();
            arrayAdapter.addAll(taskList);
            arrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText editText=new EditText(this);

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Add new task")
                        .setMessage("next..")

                        .setView(editText)
                        .setPositiveButton("add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task=String.valueOf(editText.getText());

                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("cancel",null)
                        .create();
                alertDialog.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void deleteTask(View view){
        View parent=(View)view.getParent();
        TextView textView=(TextView)findViewById(R.id.task_title);
        String task=String.valueOf(textView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }

}
