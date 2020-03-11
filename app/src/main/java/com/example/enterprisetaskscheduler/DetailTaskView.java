package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;


public class DetailTaskView extends AppCompatActivity {
    private TaskTableHelper taskDb;
    private EmployeeTableHelper empDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task_view);
        TextView taskDetailNameText, taskDetailDeptText, taskDetailEmpNameText, taskDetailStatusText, taskDetailStartDateText, taskDetailEndDateText, taskDetailDescriptionText;
        taskDb = new TaskTableHelper(this);
        empDb = new EmployeeTableHelper(this);
        int taskID = Integer.parseInt(getIntent().getStringExtra("taskID"));
        Cursor data = taskDb.getDataById(taskID);

        taskDetailNameText = findViewById(R.id.taskDetailNameText);
        taskDetailDeptText = findViewById(R.id.taskDetailDeptText);
        taskDetailEmpNameText = findViewById(R.id.taskDetailEmpNameText);
        taskDetailStatusText = findViewById(R.id.taskDetailStatusText);
        taskDetailStartDateText = findViewById(R.id.taskDetailStartDateText);
        taskDetailEndDateText = findViewById(R.id.taskDetailEndDateText);
        taskDetailDescriptionText = findViewById(R.id.taskDetailDescriptionText);

        data.moveToNext();
        taskDetailNameText.setText(data.getString(1));
        taskDetailStartDateText.setText(data.getString(2));
        taskDetailEndDateText.setText(data.getString(3));
        int empId = data.getInt(4);
        taskDetailEmpNameText.setText(empDb.getNameById(empId));
        taskDetailDeptText.setText(data.getString(5));
        taskDetailStatusText.setText(data.getString(6));
        taskDetailDescriptionText.setText(data.getString(7));


    }
}
