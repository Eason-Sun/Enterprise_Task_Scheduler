package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;

import java.util.Calendar;

public class AddEmployee extends AppCompatActivity {
    private Calendar currDate;
    private int currDay, currMonth, currYear;
    private EditText empFstNameInput, empLstNameInput, empEmailInput;
    private TextView empStartDateText;
    private AutoCompleteTextView empDeptNameInput, empLevelInput;
    private EmployeeTableHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        db = new EmployeeTableHelper(this);

        currDate = Calendar.getInstance();
        currDay = currDate.get(Calendar.DAY_OF_MONTH);
        currMonth = currDate.get(Calendar.MONTH);
        currYear = currDate.get(Calendar.YEAR);
        empFstNameInput = findViewById(R.id.empFstNameInput);
        empLstNameInput = findViewById(R.id.empLstNameInput);
        empEmailInput = findViewById(R.id.empEmailInput);
        empStartDateText = findViewById(R.id.empStartDateText);
        empStartDateText.setText(currYear + "/" + (currMonth + 1) + "/" + currDay);

        empDeptNameInput = findViewById(R.id.empDeptNameInput);
        final ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Employee.DEPARTMENTS);
        empDeptNameInput.setAdapter(deptAdapter);
        empDeptNameInput.setThreshold(1);
        empDeptNameInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String deptName = empDeptNameInput.getText().toString();
                    for(String e : Employee.DEPARTMENTS)
                        if (deptName.compareTo(e) == 0) return;
                    empDeptNameInput.setText("");
                }
            }
        });

        empLevelInput = findViewById(R.id.empLevelInput);
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Employee.LEVELS);
        empLevelInput.setAdapter(levelAdapter);
        empLevelInput.setThreshold(1);
        empLevelInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String level = empLevelInput.getText().toString();
                    for(String e : Employee.LEVELS)
                        if (level.compareTo(e) == 0) return;
                    empLevelInput.setText("");
                }
            }
        });
    }

    public void addOnClick(View view) {
        if (empFstNameInput.length() != 0 && empLstNameInput.length() != 0 && empDeptNameInput.length() != 0 && empLevelInput.length() != 0 && empEmailInput.length() != 0) {
            String fstName = empFstNameInput.getText().toString();
            String lstName = empLstNameInput.getText().toString();
            String fullName = fstName + " " + lstName;
            String dept = empDeptNameInput.getText().toString();
            String startDate = empStartDateText.getText().toString();
            String level = empLevelInput.getText().toString();
            String email = empEmailInput.getText().toString();
            Employee e = new Employee(fullName, dept, startDate, email, level);
            boolean res = db.add(e);
            empFstNameInput.setText("");
            empLstNameInput.setText("");
            empDeptNameInput.setText("");
            empStartDateText.setText(currYear + "/" + (currMonth + 1) + "/" + currDay);
            empLevelInput.setText("");
            empEmailInput.setText("");
            if (res)
                Toast.makeText(this, "New employee has been added successfully!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
        }
    }

    public void viewOnClick(View view) {
        Intent intent = new Intent(this, EmployeeListView.class);
        startActivity(intent);
    }

    public void deptOnClick(View view) {
        empDeptNameInput.showDropDown();
    }

    public void levelOnClick(View view) {
        empLevelInput.showDropDown();
    }

    public void startDateOnClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                empStartDateText.setText(year + "/" + month + "/" + dayOfMonth);
            }
        }, currYear, currMonth, currDay);
        datePickerDialog.show();
    }


}
