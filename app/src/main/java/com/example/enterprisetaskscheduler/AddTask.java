package com.example.enterprisetaskscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTask extends AppCompatActivity {
    private Calendar currDate;
    private int startDay, startMonth, startYear;
    private EditText taskNameInput, taskDescriptionInput;
    private AutoCompleteTextView taskDeptNameInput, taskEmpNameInput, taskLevelInput;
    private TextView taskStartDateText, taskEndDateText;
    private TaskTableHelper taskDb;
    private EmployeeTableHelper empDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_task);

        currDate = Calendar.getInstance();
        startDay = currDate.get(Calendar.DAY_OF_MONTH);
        startMonth = currDate.get(Calendar.MONTH);
        startYear = currDate.get(Calendar.YEAR);
        taskDb = new TaskTableHelper(this);
        empDb = new EmployeeTableHelper(this);
        taskNameInput = findViewById(R.id.taskNameInput);
        taskDescriptionInput = findViewById(R.id.taskDescriptionInput);
        taskStartDateText = findViewById(R.id.taskStartDateText);
        taskEndDateText = findViewById(R.id.taskEndDateText);

        taskDeptNameInput = findViewById(R.id.taskDeptNameInput);
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Task.DEPARTMENTS);
        taskDeptNameInput.setAdapter(deptAdapter);
        taskDeptNameInput.setThreshold(1);
        taskDeptNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String deptName = taskDeptNameInput.getText().toString();
                    for (String e : Task.DEPARTMENTS)
                        if (deptName.compareTo(e) == 0) return;
                    taskDeptNameInput.setText("");
                }
            }
        });

        //AutoComplete EmployeeName
        taskEmpNameInput = findViewById(R.id.taskEmpNameInput);
        if (getIntent().getStringExtra("empInfo") != null) {
            String[] empInfo = getIntent().getStringExtra("empInfo").split(",");
            taskDeptNameInput.setText(empInfo[1]);
            taskEmpNameInput.setText(empInfo[0]);
        }
        taskEmpNameInput.setThreshold(0);

        taskEmpNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ArrayList<String> empNameIds = empDb.getNamesByDept(taskDeptNameInput.getText().toString());
                    ArrayAdapter<String> EmployeeAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_dropdown_item_1line, empNameIds);
                    taskEmpNameInput.setAdapter(EmployeeAdapter);
                } else {
                    String empNameId = taskEmpNameInput.getText().toString();
                    ListAdapter listAdapter = taskEmpNameInput.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++)
                        if (empNameId.compareTo(listAdapter.getItem(i).toString()) == 0) return;
                    taskEmpNameInput.setText("");
                }
            }
        });

        //AutoCompleteView Task Level
        taskLevelInput= findViewById(R.id.taskLevelInput);
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Task.LEVELS);
        taskLevelInput.setAdapter(levelAdapter);
        taskLevelInput.setThreshold(1);
        taskLevelInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String level = taskLevelInput.getText().toString();
                    for(String e : Task.LEVELS)
                        if (level.compareTo(e) == 0) return;
                    taskLevelInput.setText("");
                }
            }
        });

        taskStartDateText.setText(startYear + "/" + (startMonth + 1) + "/" + startDay);
    }

    public void levelOnClick(View view) {
        taskLevelInput.showDropDown();
    }

    public void deptOnClick(View view) {
        taskDeptNameInput.showDropDown();
    }

    public void startDateOnClick(View view) {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (year <= startYear && month <= startMonth && dayOfMonth < startDay) {
                    Toast.makeText(AddTask.this, "End date cannot be earlier than start date!", Toast.LENGTH_LONG).show();
                } else {
                    month = month + 1;
                    taskEndDateText.setText(year + "/" + month + "/" + dayOfMonth);
                }

            }
        }, startYear, startMonth, startDay);
        datePickerDialog.show();
    }

    public void addOnClick(View view) {
        if (taskNameInput.length() != 0 && taskStartDateText.length() != 0 && taskEndDateText.length() != 0 && taskEmpNameInput.length() != 0 && taskDeptNameInput.length() != 0) {
            String taskName = taskNameInput.getText().toString();
            String startDate = taskStartDateText.getText().toString();
            String endDate = taskEndDateText.getText().toString();
            String empNameId = taskEmpNameInput.getText().toString();
            String empName = empNameId.split(" #")[0];
            String level = taskLevelInput.getText().toString();
            int empId = Integer.parseInt(empNameId.split(" #")[1]);
            String dept = taskDeptNameInput.getText().toString();
            Task task = new Task(taskName, startDate, endDate, empId, dept);
            task.setEmpName(empName);
            task.setDescription(taskDescriptionInput.getText().toString());
            task.setLevel(level);

            boolean res = taskDb.add(task);
            taskNameInput.setText("");
            taskDeptNameInput.setText("");
            taskStartDateText.setText(startYear + "/" + (startMonth + 1) + "/" + startDay);
            taskEndDateText.setText("");
            taskEmpNameInput.setText("");
            taskDescriptionInput.setText("");
            taskLevelInput.setText("");
            if (res) {
                Toast.makeText(this, "New Task has been added successfully!", Toast.LENGTH_LONG).show();
                String email = empDb.getEmailById(empId);
                String subject = "New task assigned to you!";
                String msg = String.format("Hello %s,\n\nThe %s level task: %s has been assigned to you. Please submit the work before %s.\n\nThank you!",
                        empName.split(" ")[0], level, taskName, endDate);
                Intent intent = new Intent(this, ContactEmployee.class);
                intent.putExtra("fromActivity", "AddTask");
                intent.putExtra("email", email);
                intent.putExtra("subject", subject);
                intent.putExtra("msg", msg);
                startActivity(intent);
            }
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









