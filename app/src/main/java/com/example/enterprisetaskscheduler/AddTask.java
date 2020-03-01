package com.example.enterprisetaskscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {
    private Calendar currDate;
    private int startDay, startMonth, startYear;
    private EditText taskNameInput, taskDescriptionInput;
    private AutoCompleteTextView taskDeptNameInput, taskEmpNameInput;
    private TextView taskEmpIdText, taskStartDateText, taskEndDateText;
    private DatabaseHelper db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_task);
        currDate = Calendar.getInstance();
        startDay = currDate.get(Calendar.DAY_OF_MONTH);
        startMonth = currDate.get(Calendar.MONTH);
        startYear = currDate.get(Calendar.YEAR);
        db = new DatabaseHelper(this);
        taskNameInput = findViewById(R.id.taskNameInput);
        taskDescriptionInput = findViewById(R.id.taskDescriptionInput);
        taskEmpIdText = findViewById(R.id.taskEmpIdText);
        taskStartDateText = findViewById(R.id.taskStartDateText);
        taskEndDateText = findViewById(R.id.taskEndDateText);

        taskDeptNameInput = findViewById(R.id.taskDeptNameInput);
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Employee.DEPARTMENTS);
        taskDeptNameInput.setAdapter(deptAdapter);
        taskDeptNameInput.setThreshold(1);

        //AutoComplete EmployeeName
        taskEmpNameInput = findViewById(R.id.taskEmpNameInput);
        taskEmpNameInput.setThreshold(0);
        taskEmpNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String[] employees = db.getEmployeeFromDept(taskDeptNameInput.getText().toString());
                    ArrayAdapter<String> EmployeeAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_dropdown_item_1line, employees);
                    taskEmpNameInput.setAdapter(EmployeeAdapter);
                } else {
                    String[] name = taskEmpNameInput.getText().toString().split("\\s+");
                    if (name.length == 2) {
                        String empId = db.getEmpIdFromName(taskDeptNameInput.getText().toString(), name[0], name[1]);
                        if (!empId.equals("None")) {
                            taskEmpIdText.setText(" #" + empId);
                        }
                    }

                }
            }
        });
        taskStartDateText.setText(startYear + "/" + (startMonth + 1) + "/" + startDay);

    }

    public void deptOnClick(View view) {
        taskDeptNameInput.showDropDown();
    }

    public void startDateOnClick(View view) {
        taskEmpNameInput.clearFocus();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDay = dayOfMonth;
                startMonth = month;
                startYear = year;
                month = month + 1;
                taskStartDateText.setText(year + "/" + month + "/" + dayOfMonth);
            }
        }, startYear, startMonth, startDay);
        datePickerDialog.show();
    }

    public void endDateOnClick(View view) {
        taskEmpNameInput.clearFocus();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (year <= startYear && month <= startMonth && dayOfMonth < startDay) {
                    Toast.makeText(AddTask.this, "End date cannot be earlier than start date!" , Toast.LENGTH_LONG).show();
                } else {
                    month = month + 1;
                    taskEndDateText.setText(year + "/" + month + "/" + dayOfMonth);
                }

            }
        }, startYear, startMonth, startDay);
        datePickerDialog.show();
    }

    public void addOnClick(View view) {
        if (taskNameInput.length() != 0 && taskStartDateText.length() != 0 && taskEndDateText.length() != 0 && taskEmpIdText.length() != 0) {
            String taskName = taskNameInput.getText().toString();
            String startDate = taskStartDateText.getText().toString();
            String endDate = taskEndDateText.getText().toString();
            int empID = Integer.valueOf(taskEmpIdText.getText().toString().substring(2));
            Task task = new Task(taskName, startDate, endDate, empID);
            task.setEmpName(taskEmpNameInput.getText().toString());
            task.setDescription(taskDescriptionInput.getText().toString());

            boolean res = db.addTask(task);
            taskNameInput.setText("");
            taskDeptNameInput.setText("");
            taskStartDateText.setText(startYear + "/" + (startMonth + 1) + "/" + startDay);
            taskEndDateText.setText("");
            taskEmpIdText.setText("");
            taskEmpNameInput.setText("");
            taskDescriptionInput.setText("");
            if (res)
                Toast.makeText(this, "New Task has been added successfully!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
        }
    }

    public void viewOnClick(View view) {
        Intent intent = new Intent(this, TaskListView.class);
        startActivity(intent);
    }

}









