package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button employeeButton;
    Button taskButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employeeButton = findViewById(R.id.employeeButton);
        taskButton = findViewById(R.id.taskButton);
    }
    public void employeeMenuOnClick(View view) {
        PopupMenu employeeMenu = new PopupMenu(this, employeeButton);
        employeeMenu.inflate(R.menu.employee_menu);
        employeeMenu.show();
        employeeMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.addEmployeeOption) {
                    Intent intent = new Intent(getBaseContext(), AddEmployee.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.searchEmployeeOption) {
                    Intent intent = new Intent(getBaseContext(), EmployeeListView.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
    public void taskMenuOnClick(View view) {
        PopupMenu taskMenu = new PopupMenu(this, taskButton);
        taskMenu.inflate(R.menu.task_menu);
        taskMenu.show();
        taskMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.addTaskOption) {
                    Intent intent = new Intent(getBaseContext(), AddTask.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.searchTaskOption) {
                    Intent intent = new Intent(getBaseContext(), TaskListView.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }



}


