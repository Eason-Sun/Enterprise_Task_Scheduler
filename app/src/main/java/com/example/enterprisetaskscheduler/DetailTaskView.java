package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;


public class DetailTaskView extends AppCompatActivity {
    private TaskTableHelper taskDb;
    private EmployeeTableHelper empDb;
    private Task task;
    private int taskID, empId;
    private String empName, taskName, level;
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
        taskID = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("taskID")));
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
        taskName = data.getString(1);
        String startDate = data.getString(2);
        String endDate = data.getString(3);
        empId = data.getInt(4);
        empName = empDb.getNameById(empId);
        level = data.getString(8);
        String dept = data.getString(5);
        task = new Task(taskName, startDate, endDate, empId, dept);
        task.setEmpName(empName);
        task.setStatus(data.getString(6));
        task.setDescription(data.getString(7));
        task.setLevel(level);

        taskDetailNameText.setText(task.getName());
        taskDetailStartDateText.setText(task.getStartDate());
        taskDetailEndDateText.setText(task.getDueDate());
        taskDetailEmpNameText.setText(task.getEmpName());
        taskDetailDeptText.setText(task.getDept());
        taskDetailStatusText.setText(task.getStatus());
        taskDetailDescriptionText.setText(task.getDescription());
        taskDetailLevelText.setText(task.getLevel());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu); // Inflate employee_search_menu_menu.xml and display it.
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch(item.getItemId()) {
            case R.id.editItem:
                intent = new Intent(this, EditTask.class);
                intent.putExtra("taskID",  Integer.toString(taskID));
                this.startActivity(intent);
                break;
            case R.id.returnEditItem:
                intent = new Intent(this, TaskListView.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void completeOnClick(View view) {
        String taskStatus = taskDetailStatusText.getText().toString();
        if (taskStatus.equals(Task.STATUS[1])){
            Toast.makeText(this, "The task has already been completed", Toast.LENGTH_LONG).show();
        }
        else if (taskStatus.equals(Task.STATUS[2])){
            Toast.makeText(this, "Can not complete an cancelled task", Toast.LENGTH_LONG).show();
        }
        else if (taskStatus.equals(Task.STATUS[0])){
            task.setStatus(Task.STATUS[1]);
            boolean res = taskDb.modify(task, taskID);
            if (res){
                Toast.makeText(this, "Task has been successfully marked as complete", Toast.LENGTH_LONG).show();
                Cursor data = taskDb.getDataById(taskID);
                data.moveToNext();
                taskDetailStatusText.setText(data.getString(6));
                // Send a completed confirmation email to the assignee.
                sendConfirmation("completed");
            }
            else
                Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelOnClick(View view) {
        String taskStatus = taskDetailStatusText.getText().toString();
        if (taskStatus.equals(Task.STATUS[1])){
            Toast.makeText(this, "Can not cancel an completed Task", Toast.LENGTH_LONG).show();
        }
        else if (taskStatus.equals(Task.STATUS[2])){
            Toast.makeText(this, "The Task has already been cancelled", Toast.LENGTH_LONG).show();
        }
        else if (taskStatus.equals(Task.STATUS[0])){
            task.setStatus(Task.STATUS[2]);
            boolean res = taskDb.modify(task, taskID);
            if (res) {
                Toast.makeText(this, "Task has been successfully marked as cancelled", Toast.LENGTH_LONG).show();
                Cursor data = taskDb.getDataById(taskID);
                data.moveToNext();
                taskDetailStatusText.setText(data.getString(6));
                // Send a cancelled confirmation email to the assignee.
                sendConfirmation("cancelled");
            } else
                Toast.makeText(this, "Operation failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        }

        private void sendConfirmation(String status) {
            String email = empDb.getEmailById(empId);
            @SuppressLint("DefaultLocale") String subject = String.format("Task #%d has been marked as %s!", taskID, status);
            @SuppressLint("DefaultLocale") String msg = String.format("Hello %s,\n\n" +
                            "The %s level task: #%d%s has been marked as %s.\n\nThank you!",
                    empName.split(" ")[0], level, taskID, taskName, status);
            Intent intent = new Intent(this, ContactEmployee.class);
            intent.putExtra("fromActivity", "AddTask");
            intent.putExtra("email", email);
            intent.putExtra("subject", subject);
            intent.putExtra("msg", msg);
            startActivity(intent);
        }
}
