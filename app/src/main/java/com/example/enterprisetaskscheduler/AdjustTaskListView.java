package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class AdjustTaskListView extends AppCompatActivity {
    private String headerIndex;
    private ArrayList<CheckBox> checkBoxArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_task_list_view);
        headerIndex = getIntent().getStringExtra("headerIndex");

        //Define All views and checkboxArray
        checkBoxArray = new ArrayList<>();
        CheckBox checkIDColumn = findViewById(R.id.checkIDColumn);
        CheckBox checkTaskNameColumn = findViewById(R.id.checkTaskNameColumn);
        CheckBox checkTaskLevelColumn = findViewById(R.id.checkTaskLevelColumn);
        CheckBox checkTaskDeptColumn = findViewById(R.id.checkTaskDeptColumn);
        CheckBox checkTaskEmpNameColumn = findViewById(R.id.checkTaskEmpNameColumn);
        CheckBox checkTaskStatusColumn = findViewById(R.id.checkTaskStatusColumn);
        CheckBox checkTaskStartDateColumn = findViewById(R.id.checkTaskStartDateColumn);
        CheckBox checkTaskEndDateColumn = findViewById(R.id.checkTaskEndDateColumn);
        checkBoxArray.add(checkIDColumn);
        checkBoxArray.add(checkTaskNameColumn);
        checkBoxArray.add(checkTaskLevelColumn);
        checkBoxArray.add(checkTaskDeptColumn);
        checkBoxArray.add(checkTaskEmpNameColumn);
        checkBoxArray.add(checkTaskStatusColumn);
        checkBoxArray.add(checkTaskStartDateColumn);
        checkBoxArray.add(checkTaskEndDateColumn);
    }

    public void applyOnClick(View view) {
        headerIndex = getHeaderIndex(checkBoxArray);
        Intent intent = new Intent(this, TaskListView.class);
        intent.putExtra("headerIndex", headerIndex);
        startActivity(intent);
    }

    public void cancelOnClick(View view) {
        Intent intent = new Intent(this, TaskListView.class);
        intent.putExtra("headerIndex", headerIndex);
        startActivity(intent);
    }
    public String getHeaderIndex(ArrayList<CheckBox> checkBoxArray) {
        String headerIndex;
        headerIndex = "";
        for (CheckBox checkBox : checkBoxArray) {
            if (checkBox.isChecked()) {
                headerIndex = headerIndex + "1";
            } else {
                headerIndex = headerIndex + "0";
            }
        }
        return headerIndex;
    }
}

