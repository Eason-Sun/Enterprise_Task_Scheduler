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

public class EmployeeListView extends AppCompatActivity {

    private EmployeeTableHelper db;
    private ListView listView;
    private String headerIndex;
    ArrayList<Employee> employees;
    EmployeeListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        db = new EmployeeTableHelper(this);
        listView = findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.emp_list_header, listView, false);
        listView.addHeaderView(header);
        employees = new ArrayList<>();
        Cursor data = db.getData();
        //Get HeaderIndex for Display
        if (getIntent().getStringExtra("headerIndex") != null) {
            headerIndex = getIntent().getStringExtra("headerIndex");
        }else {
            headerIndex = "110110";
        }
        //End
        if (data.getCount() == 0) {
            Toast.makeText(this, "Database is empty!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                Employee e = new Employee(data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5));
                e.setId(data.getInt(0));
                employees.add(e);
                adapter = new EmployeeListAdapter(this, R.layout.emp_list_adapter, employees, headerIndex);
                listView.setAdapter(adapter);
                employeeHeaderUpdate(headerIndex);
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position > 0) {
                    // Get the Cursor
                    Employee item = (Employee) parent.getItemAtPosition(position);
                    String empId = String.valueOf(item.getId());
                    Intent intent = new Intent(getBaseContext(), DetailEmployeeView.class);
                    intent.putExtra("empId", empId);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch(item.getItemId()) {
            case R.id.adjustColumn:
                intent = new Intent(this, AdjustEmployeeListView.class);
                intent.putExtra("headerIndex", headerIndex);
                this.startActivity(intent);
                break;
            case R.id.goMainActivity:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("lastActivty", "EmployeeListView");
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }



    public void employeeHeaderUpdate(String headerIndex){
        ArrayList<TextView> textViewArray;
        textViewArray = new ArrayList<>();
        char[] charsHeaderArray = headerIndex.toCharArray();
        //TextView For Header
        TextView headerEmpIDText = findViewById(R.id.headerEmpIDText);
        TextView headerEmpNameText = findViewById(R.id.headerEmpNameText);
        TextView headerEmpLevelText = findViewById(R.id.headerEmpLevelText);
        TextView headerEmpDeptText = findViewById(R.id.headerEmpDeptText);
        TextView headerEmpStartDateText = findViewById(R.id.headerEmpStartDateText);
        TextView headerEmpEmailText = findViewById(R.id.headerEmpEmailText);
        textViewArray.add(headerEmpIDText);
        textViewArray.add(headerEmpNameText);
        textViewArray.add(headerEmpLevelText);
        textViewArray.add(headerEmpDeptText);
        textViewArray.add(headerEmpStartDateText);
        textViewArray.add(headerEmpEmailText);
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
                if(textView != headerEmpIDText){
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
                    int width = screenWidth/count;
                    params.width = width;
                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                }
            }
        }else{
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) headerEmpIDText.getLayoutParams();
            params.width= 1100;
            headerEmpIDText.setLayoutParams(params);
        }

    }
}
