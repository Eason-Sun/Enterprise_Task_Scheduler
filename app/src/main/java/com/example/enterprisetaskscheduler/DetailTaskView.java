package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;


public class DetailTaskView extends AppCompatActivity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task_view);

        TextView taskNameDisplay, departmentDisplay, employeeNameDisplay,taskStatusDisplay;
        TextView startdateDisplay,enddateDisplay,taskDesDisplay;
        db = new DatabaseHelper(this);

        String taskID = getIntent().getStringExtra("taskID");
        Cursor data = db.getTaskDetail(taskID);

        taskNameDisplay = findViewById(R.id.taskNameDisplay);
        departmentDisplay = findViewById(R.id.departmentDisplay);
        employeeNameDisplay =findViewById(R.id.employeeNameDisplay);
        taskStatusDisplay=findViewById(R.id.taskStatusDisplay);
        startdateDisplay=findViewById(R.id.startdateDisplay);
        enddateDisplay = findViewById(R.id.enddateDisplay);
        taskDesDisplay = findViewById(R.id.taskDesDisplay);

        data.moveToNext();
        taskNameDisplay.setText(data.getString(1));
        startdateDisplay.setText(data.getString(2));
        enddateDisplay.setText(data.getString(3));
        taskStatusDisplay.setText(data.getString(5));
        taskDesDisplay.setText(data.getString(6));

        String employeeID = data.getString(4);
        data = db.getEmployeeInformation(employeeID);
        data.moveToNext();
        departmentDisplay.setText(data.getString(3));
        employeeNameDisplay.setText(data.getString(1)+" "+data.getString(2));




    }
}
