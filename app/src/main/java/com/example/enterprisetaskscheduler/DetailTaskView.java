package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DetailTaskView extends AppCompatActivity {
    private TaskTableHelper taskDb;
    private EmployeeTableHelper empDb;
    private Task task;
    private int taskID;
    private TextView taskDetailStatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task_view);
        TextView taskDetailNameText, taskDetailDeptText, taskDetailEmpNameText,
                taskDetailStartDateText, taskDetailEndDateText,
                taskDetailDescriptionText, taskDetailLevelText;
        Button markCompleteButton, MarkCancelButton;
        taskDb = new TaskTableHelper(this);
        empDb = new EmployeeTableHelper(this);
        taskID = Integer.parseInt(getIntent().getStringExtra("taskID"));
        Cursor data = taskDb.getDataById(taskID);

        taskDetailNameText = findViewById(R.id.taskDetailNameText);
        taskDetailDeptText = findViewById(R.id.taskDetailDeptText);
        taskDetailLevelText = findViewById(R.id.taskDetailLevelText);
        taskDetailEmpNameText = findViewById(R.id.taskDetailEmpNameText);
        taskDetailStatusText = findViewById(R.id.taskDetailStatusText);
        taskDetailStartDateText = findViewById(R.id.taskDetailStartDateText);
        taskDetailEndDateText = findViewById(R.id.taskDetailEndDateText);
        taskDetailDescriptionText = findViewById(R.id.taskDetailDescriptionText);

        data.moveToNext();
        String taskName = data.getString(1);
        String startDate = data.getString(2);
        String endDate = data.getString(3);
        int empId = data.getInt(4);
        String empName = empDb.getNameById(empId);
        String dept = data.getString(5);
        task = new Task(taskName, startDate, endDate, empId, dept);
        task.setEmpName(empName);
        task.setStatus(data.getString(6));
        task.setDescription(data.getString(7));
        task.setLevel(data.getString(8));

        taskDetailNameText.setText(task.getName());
        taskDetailStartDateText.setText(task.getStartDate());
        taskDetailEndDateText.setText(task.getDueDate());
        taskDetailEmpNameText.setText(task.getEmpName());
        taskDetailDeptText.setText(task.getDept());
        taskDetailStatusText.setText(task.getStatus());
        taskDetailDescriptionText.setText(task.getDescription());
        taskDetailLevelText.setText(task.getLevel());
    }


    public void completeOnClick(View view) {
        task.setStatus(task.STATUS[1]);
        boolean res = taskDb.modify(task, taskID);
        if (res){
            Toast.makeText(this, "Task has been successfully marked as complete", Toast.LENGTH_LONG).show();
            Cursor data = taskDb.getDataById(taskID);
            data.moveToNext();
            taskDetailStatusText.setText(data.getString(6));
        }
        else
            Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();
    }

    public void cancelOnClick(View view) {
        task.setStatus(task.STATUS[2]);
        boolean res = taskDb.modify(task, taskID);
        if (res) {
            Toast.makeText(this, "Task has been successfully marked as cancel", Toast.LENGTH_LONG).show();
            Cursor data = taskDb.getDataById(taskID);
            data.moveToNext();
            taskDetailStatusText.setText(data.getString(6));
        } else
            Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();
    }

}
