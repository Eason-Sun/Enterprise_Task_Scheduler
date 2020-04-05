package com.example.enterprisetaskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class AdjustEmployeeListView extends AppCompatActivity {
    private String headerIndex;
    private ArrayList<CheckBox> checkBoxArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_employee_list_view);
        headerIndex = getIntent().getStringExtra("headerIndex");

        //Define All views and checkboxArray
        checkBoxArray = new ArrayList<>();
        CheckBox checkEmployeeIDColumn = findViewById(R.id.checkEmployeeIDColumn);
        CheckBox checkEmployeeNameColumn = findViewById(R.id.checkEmployeeNameColumn);
        CheckBox checkEmployeeLevelColumn = findViewById(R.id.checkEmployeeLevelColumn);
        CheckBox checkEmployeeDeptColumn = findViewById(R.id.checkEmployeeDeptColumn);
        CheckBox checkEmployeeStartDateColumn = findViewById(R.id.checkEmployeeStartDateColumn);
        CheckBox checkEmployeeEmailColumn = findViewById(R.id.checkEmployeeEmailColumn);
        checkBoxArray.add(checkEmployeeIDColumn);
        checkBoxArray.add(checkEmployeeNameColumn);
        checkBoxArray.add(checkEmployeeLevelColumn);
        checkBoxArray.add(checkEmployeeDeptColumn);
        checkBoxArray.add(checkEmployeeStartDateColumn);
        checkBoxArray.add(checkEmployeeEmailColumn);
    }
    public void applyOnClick(View view) {
        headerIndex = getHeaderIndex(checkBoxArray);
        Intent intent = new Intent(this, EmployeeListView.class);
        intent.putExtra("headerIndex", headerIndex);
        startActivity(intent);
    }

    public void cancelOnClick(View view) {
        Intent intent = new Intent(this, EmployeeListView.class);
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
