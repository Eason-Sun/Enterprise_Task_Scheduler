package com.example.enterprisetaskscheduler;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class EmployeeListView extends AppCompatActivity {

    private DatabaseHelper db;
    private ListView listView;
    ArrayList<Employee> employees;
    EmployeeListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.emp_list_header, listView, false);
        listView.addHeaderView(header);
        employees = new ArrayList<>();
        Cursor data = db.getDataFromTable(db.EMPLOYEE_TABLE_NAME);
        if (data.getCount() == 0) {
            Toast.makeText(this, "Database is empty!", Toast.LENGTH_LONG).show();
        } else {
            while(data.moveToNext()) {
                Employee e = new Employee(data.getString(1), data.getString(2), data.getString(3), data.getString(4));
                e.setId(data.getInt(0));
                employees.add(e);
                adapter = new EmployeeListAdapter(this, R.layout.emp_list_adapter, employees);
                listView.setAdapter(adapter);
            }
        }
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
                ArrayList<Employee> filteredEmployees = new ArrayList<>();
                for (Employee e : employees) {
                    if (e.contains(newText))
                        filteredEmployees.add(e);
                }
                adapter.update(filteredEmployees); // Refresh the list view
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
}
