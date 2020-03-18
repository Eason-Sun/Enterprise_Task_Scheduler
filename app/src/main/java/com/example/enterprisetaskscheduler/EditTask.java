package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    private Calendar currDate;
    private int startDay, startMonth, startYear;
    private EditText taskNameEdit, taskDetailDescriptionEdit;
    private AutoCompleteTextView taskLevelEdit, taskDeptNameEdit, taskEmpNameEdit, taskStatusEdit;
    private TextView taskStartDateEdit, taskEndDateEdit, taskIDEdit;
    private TaskTableHelper taskDb;
    private EmployeeTableHelper empDb;
    private int taskID;
    private Task task;
    private int empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        currDate = Calendar.getInstance();
        startDay = currDate.get(Calendar.DAY_OF_MONTH);
        startMonth = currDate.get(Calendar.MONTH);
        startYear = currDate.get(Calendar.YEAR);
        taskDb = new TaskTableHelper(this);
        empDb = new EmployeeTableHelper(this);
        taskID = Integer.parseInt(getIntent().getStringExtra("taskID"));
        Cursor data = taskDb.getDataById(taskID);

        taskNameEdit = findViewById(R.id.taskNameEdit);
        taskDetailDescriptionEdit = findViewById(R.id.taskDetailDescriptionEdit);
        taskLevelEdit = findViewById(R.id.taskLevelEdit);
        taskDeptNameEdit = findViewById(R.id.taskDeptNameEdit);
        taskEmpNameEdit = findViewById(R.id.taskEmpNameEdit);
        taskStatusEdit = findViewById(R.id.taskStatusEdit);
        taskStartDateEdit = findViewById(R.id.taskStartDateEdit);
        taskEndDateEdit = findViewById(R.id.taskEndDateEdit);
        taskIDEdit = findViewById(R.id.taskIDEdit);

        data.moveToNext();
        taskIDEdit.setText("#" + data.getString(0));
        taskNameEdit.setText(data.getString(1));
        taskStartDateEdit.setText(data.getString(2));
        taskEndDateEdit.setText(data.getString(3));
        empId = data.getInt(4);
        String empName = empDb.getNameById(empId);
        taskEmpNameEdit.setText(empName);
        taskDeptNameEdit.setText(data.getString(5));
        taskStatusEdit.setText(data.getString(6));
        taskDetailDescriptionEdit.setText(data.getString(7));
        taskLevelEdit.setText(data.getString(8));

        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Task.DEPARTMENTS);
        taskDeptNameEdit.setAdapter(deptAdapter);
        taskDeptNameEdit.setThreshold(1);
        taskDeptNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String deptName = taskDeptNameEdit.getText().toString();
                    for (String e : Task.DEPARTMENTS)
                        if (deptName.compareTo(e) == 0) return;
                    taskDeptNameEdit.setText("");
                }
            }
        });

        //AutoComplete EmployeeName
        if (getIntent().getStringExtra("empInfo") != null) {
            String[] empInfo = getIntent().getStringExtra("empInfo").split(",");
            taskEmpNameEdit.setText(empInfo[1]);
            taskEmpNameEdit.setText(empInfo[0]);
        }
        taskEmpNameEdit.setThreshold(0);

        taskEmpNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ArrayList<String> empNameIds = empDb.getNamesByDept(taskDeptNameEdit.getText().toString());
                    ArrayAdapter<String> EmployeeAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_dropdown_item_1line, empNameIds);
                    taskEmpNameEdit.setAdapter(EmployeeAdapter);
                } else {
                    String empNameId = taskEmpNameEdit.getText().toString();
                    ListAdapter listAdapter = taskEmpNameEdit.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); i++)
                        if (empNameId.compareTo(listAdapter.getItem(i).toString()) == 0) return;
                    taskEmpNameEdit.setText("");
                }
            }
        });

        //AutoCompleteView Task Level
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Task.LEVELS);
        taskLevelEdit.setAdapter(levelAdapter);
        taskLevelEdit.setThreshold(1);
        taskLevelEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String level = taskLevelEdit.getText().toString();
                    for(String e : Task.LEVELS)
                        if (level.compareTo(e) == 0) return;
                    taskLevelEdit.setText("");
                }
            }
        });

        //AutoCompleteView Task Level
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Task.STATUS);
        taskStatusEdit.setAdapter(statusAdapter);
        taskStatusEdit.setThreshold(1);
        taskStatusEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String level = taskStatusEdit.getText().toString();
                    for(String e : Task.STATUS)
                        if (level.compareTo(e) == 0) return;
                    taskStatusEdit.setText("");
                }
            }
        });

        taskDeptNameEdit.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                taskEmpNameEdit.setText("");
            }
        });
    }

    public void levelOnClick(View view) {
        taskLevelEdit.showDropDown();
    }

    public void deptOnClick(View view) {
        taskDeptNameEdit.showDropDown();
    }

    public void statusOnClick(View view) {
        taskStatusEdit.showDropDown();
    }

    public void startDateOnClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditTask.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                startDay = dayOfMonth;
                startMonth = month;
                startYear = year;
                month = month + 1;
                taskStartDateEdit.setText(year + "/" + month + "/" + dayOfMonth);
            }
        }, startYear, startMonth, startDay);
        datePickerDialog.show();
    }

    public void endDateOnClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditTask.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (year <= startYear && month <= startMonth && dayOfMonth < startDay) {
                    Toast.makeText(EditTask.this, "End date cannot be earlier than start date!", Toast.LENGTH_LONG).show();
                } else {
                    month = month + 1;
                    taskEndDateEdit.setText(year + "/" + month + "/" + dayOfMonth);
                }

            }
        }, startYear, startMonth, startDay);
        datePickerDialog.show();
    }

    public void cancelOnClick(View view){
        Intent intent = new Intent(this, DetailTaskView.class);
        intent.putExtra("empId", Integer.toString(taskID));
        this.startActivity(intent);
    }

    public void editOnClick(View view){
        int empIDNew;
        if (taskNameEdit.length() != 0 && taskStartDateEdit.length() != 0 && taskEndDateEdit.length() != 0 && taskEmpNameEdit.length() != 0 &&
                taskDeptNameEdit.length() != 0 &&  taskStatusEdit.length() != 0 && taskLevelEdit.length() != 0) {
            String taskName = taskNameEdit.getText().toString();
            String startDate = taskStartDateEdit.getText().toString();
            String endDate = taskEndDateEdit.getText().toString();
            String empNameNew = taskEmpNameEdit.getText().toString().split(" #")[0];
            Cursor data = taskDb.getDataById(taskID);
            //Check if there is an EmpName Change
            data.moveToNext();
            empId = data.getInt(4);
            String empNameOld = empDb.getNameById(empId);
            if (empNameOld.contentEquals(empNameNew)) {
                empIDNew = empId;
            }else{
                empIDNew = Integer.parseInt(taskEmpNameEdit.getText().toString().split(" #")[1]);
            }
            //End
            String dept = taskDeptNameEdit.getText().toString();
            task = new Task(taskName, startDate, endDate, empIDNew, dept);
            task.setStatus(taskStatusEdit.getText().toString());
            task.setDescription(taskDetailDescriptionEdit.getText().toString());
            task.setLevel(taskLevelEdit.getText().toString());
            boolean res = taskDb.modify(task, taskID);
            if (res)
            {
                Toast.makeText(this, "Task has been updated successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, DetailTaskView.class);
                intent.putExtra("taskID", Integer.toString(taskID));
                this.startActivity(intent);
            }

            else
                Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
        }
    }
}
