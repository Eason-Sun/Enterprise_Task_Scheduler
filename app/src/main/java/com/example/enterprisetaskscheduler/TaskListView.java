package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class TaskListView extends AppCompatActivity {
    private TaskTableHelper taskDb;
    private EmployeeTableHelper empDb;
    private ListView listView;
    private String headerIndex;
    ArrayList<Task> tasks;
    TaskListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        taskDb = new TaskTableHelper(this);
        empDb = new EmployeeTableHelper(this);
        listView = findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.task_list_header, listView, false);
        listView.addHeaderView(header);
        tasks = new ArrayList<>();
        Cursor data = taskDb.getData();
        //Get HeaderIndex for Display
        if (getIntent().getStringExtra("headerIndex") != null) {
            headerIndex = getIntent().getStringExtra("headerIndex");
        }else {
            headerIndex = "11001001";
        }
        //End
        if (data.getCount() == 0) {
            Toast.makeText(this, "Database is empty!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                Task task = new Task(data.getString(1), data.getString(2), data.getString(3), data.getInt(4), data.getString(5));
                task.setId(data.getInt(0));
                task.setStatus(data.getString(6));
                task.setDescription(data.getString(7));
                task.setLevel(data.getString(8));
                //Get Employee Name from Employee Table
                String employeeName = empDb.getNameById(task.getEmpId());
                task.setEmpName(employeeName);
                tasks.add(task);
                adapter = new TaskListAdapter(this, R.layout.task_list_adapter, tasks, headerIndex);
                listView.setAdapter(adapter);
                //data.moveToNext();
                taskHeaderUpdate(headerIndex);
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Get the Cursor
                if (position > 0) {
                    Task item = (Task) parent.getItemAtPosition(position);
                    String taskID = String.valueOf(item.getId());
                    Intent intent = new Intent(getBaseContext(), DetailTaskView.class);
                    intent.putExtra("taskID", taskID);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu); // Inflate employee_search_menu_menu.xml and display it.
        MenuItem menuItem = menu.findItem(R.id.searchMenu);
        // Return the reference of a searchView object by returning the actionViewClass of employee_search_menu.xmlu.xml
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // It gets called for every new input string
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // It gets called for every new input string
                ArrayList<Task> filteredTasks = new ArrayList<>();
                for (Task e : tasks) {
                    if (e.contains(newText))
                        filteredTasks.add(e);
                }
                adapter.update(filteredTasks); // Refresh the list view
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch(item.getItemId()) {
            case R.id.adjustColumn:
                intent = new Intent(this, AdjustTaskListView.class);
                intent.putExtra("headerIndex", headerIndex);
                this.startActivity(intent);
                break;
            case R.id.goMainActivity:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("lastActivty", "TaskListView");
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void taskHeaderUpdate(String headerIndex){
        ArrayList<TextView> textViewArray;
        textViewArray = new ArrayList<>();
        char[] charsHeaderArray = headerIndex.toCharArray();
        //TextView For Header
        TextView headerTaskID = findViewById(R.id.headerTaskID);
        TextView headerTaskName = findViewById(R.id.headerTaskName);
        TextView headerTaskLevel = findViewById(R.id.headerTaskLevel);
        TextView headerTaskDeptName = findViewById(R.id.headerTaskDeptName);
        TextView headerTaskEmpName = findViewById(R.id.headerTaskEmpName);
        TextView headerTaskStatus = findViewById(R.id.headerTaskStatus);
        TextView headerStartDateText = findViewById(R.id.headerStartDateText);
        TextView headerDueDateText = findViewById(R.id.headerDueDateText);
        textViewArray.add(headerTaskID);
        textViewArray.add(headerTaskName);
        textViewArray.add(headerTaskLevel);
        textViewArray.add(headerTaskDeptName);
        textViewArray.add(headerTaskEmpName);
        textViewArray.add(headerTaskStatus);
        textViewArray.add(headerStartDateText);
        textViewArray.add(headerDueDateText);
        //Set Visibility for header
        int index = 0;
        int count = 0;
        int screenWidth = 1100;
        for (TextView textView : textViewArray) {
            if (Character.valueOf(charsHeaderArray[index]).compareTo('1') == 0) {
                textView.setVisibility(View.VISIBLE);
                count = count + 1;
                if (index == 0){
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
                    params.width= 125;
                    textView.setLayoutParams(params);
                    screenWidth = screenWidth -125;
                    count  = count - 1;
                }
            } else {
                textView.setVisibility(View.GONE);
            }
            index = index + 1;
        }
        if (count > 0) {
            for (TextView textView : textViewArray) {
                if(textView != headerTaskID){
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
                    int width = screenWidth/count;
                    params.width = width;
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                }
            }
        }else{
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) headerTaskID.getLayoutParams();
            params.width= 1100;
            headerTaskID.setLayoutParams(params);
        }

    }
}
