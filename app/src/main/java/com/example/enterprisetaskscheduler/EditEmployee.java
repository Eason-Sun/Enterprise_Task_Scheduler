package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditEmployee extends AppCompatActivity {
    private Calendar currDate;
    private int currDay, currMonth, currYear;
    private EmployeeTableHelper empDb;
    TextView empIDEdit, empStartDateEdit;
    AutoCompleteTextView empDeptNameEdit, empLevelEdit;
    EditText empFstNameEdit, empLstNameEdit, empEmailEdit;
    int empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        empDb = new EmployeeTableHelper(this);
        empId = Integer.parseInt(getIntent().getStringExtra("empID"));
        Cursor data = empDb.getDataById(empId);

        currDate = Calendar.getInstance();
        currDay = currDate.get(Calendar.DAY_OF_MONTH);
        currMonth = currDate.get(Calendar.MONTH);
        currYear = currDate.get(Calendar.YEAR);

        empIDEdit = findViewById(R.id.empIDEdit);
        empFstNameEdit = findViewById(R.id.empFstNameEdit);
        empLstNameEdit = findViewById(R.id.empLstNameEdit);
        empDeptNameEdit = findViewById(R.id.empDeptNameEdit);
        empLevelEdit = findViewById(R.id.empLevelEdit);
        empStartDateEdit = findViewById(R.id.empStartDateEdit);
        empEmailEdit = findViewById(R.id.empEmailEdit);

        data.moveToFirst();
        empIDEdit.setText("#" + data.getString(0));
        String name = data.getString(1);
        empFstNameEdit.setText(name.split(" ")[0]);
        empLstNameEdit.setText(name.split(" ")[1]);
        empDeptNameEdit.setText(data.getString(2));
        empStartDateEdit.setText(data.getString(3));
        empEmailEdit.setText(data.getString(4));
        empLevelEdit.setText(data.getString(5));

        //Set Adapter
        final ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Employee.DEPARTMENTS);
        empDeptNameEdit.setAdapter(deptAdapter);
        empDeptNameEdit.setThreshold(1);
        empDeptNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String deptName = empDeptNameEdit.getText().toString();
                    for(String e : Employee.DEPARTMENTS)
                        if (deptName.compareTo(e) == 0) return;
                    empDeptNameEdit.setText("");
                }
            }
        });
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Employee.LEVELS);
        empLevelEdit.setAdapter(levelAdapter);
        empLevelEdit.setThreshold(1);
        empLevelEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String level = empLevelEdit.getText().toString();
                    for(String e : Employee.LEVELS)
                        if (level.compareTo(e) == 0) return;
                    empLevelEdit.setText("");
                }
            }
        });
    }

    public void deptOnClick(View view) {
        empDeptNameEdit.showDropDown();
    }

    public void levelOnClick(View view) {
        empLevelEdit.showDropDown();
    }

    public void startDateOnClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                empStartDateEdit.setText(year + "/" + month + "/" + dayOfMonth);
            }
        }, currYear, currMonth, currDay);
        datePickerDialog.show();
    }

    public void cancelOnClick(View view){
        Intent intent = new Intent(this, DetailEmployeeView.class);
        intent.putExtra("empId", Integer.toString(empId));
        this.startActivity(intent);
    }

    public void editOnClick(View view){
        if (empFstNameEdit.length() != 0 && empLstNameEdit.length() != 0 && empDeptNameEdit.length() != 0 && empLevelEdit.length() != 0 && empEmailEdit.length() != 0) {
            String fstName = empFstNameEdit.getText().toString();
            String lstName = empLstNameEdit.getText().toString();
            String fullName = fstName + " " + lstName;
            String dept = empDeptNameEdit.getText().toString();
            String startDate = empStartDateEdit.getText().toString();
            String level = empLevelEdit.getText().toString();
            String email = empEmailEdit.getText().toString();
            Employee e = new Employee(fullName, dept, startDate, email, level);
            boolean res = empDb.modify(e, empId);
            if (res)
            {
                Toast.makeText(this, "Employee has been updated successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, DetailEmployeeView.class);
                intent.putExtra("empId", Integer.toString(empId));
                this.startActivity(intent);
            }

            else
                Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
        }
    }
}
