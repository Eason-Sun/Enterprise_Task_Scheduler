package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class TaskListView extends AppCompatActivity {
    private DatabaseHelper db;
    private ListView listView;
    ArrayList<Task> tasks;
    TaskListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.task_list_header, listView, false);
        listView.addHeaderView(header);
        tasks = new ArrayList<>();
        Cursor data = db.getDataFromTable(db.TASK_TABLE_NAME);
        if (data.getCount() == 0) {
            Toast.makeText(this, "Database is empty!", Toast.LENGTH_LONG).show();
        } else {
            while(data.moveToNext()) {
                Task task = new Task(data.getString(1), data.getString(2), data.getString(3), data.getInt(4));
                task.setId(data.getInt(0));
                //Get Employee Name from Employee Table
                Cursor employeeData = db.getEmployeeInformation(task.getEmpId());
                employeeData.moveToNext();
                String employeeName = employeeData.getString(1)+" "+employeeData.getString(2);
                task.setEmpName(employeeName);

                tasks.add(task);
                adapter = new TaskListAdapter(this, R.layout.task_list_adapter, tasks);
                listView.setAdapter(adapter);
                //data.moveToNext();
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Get the Cursor
                Task item = (Task) parent.getItemAtPosition(position);
                String taskID = String.valueOf(item.getId());
                Intent intent = new Intent(getBaseContext(), DetailTaskView.class);
                intent.putExtra("taskID", taskID);
                startActivity(intent);
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
}
