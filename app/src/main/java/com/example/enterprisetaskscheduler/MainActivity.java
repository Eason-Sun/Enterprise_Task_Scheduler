package com.example.enterprisetaskscheduler;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button employeeButton;
    private Button taskButton;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    private TaskTableHelper taskDb = new TaskTableHelper(this);
    private String lastActivty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        employeeButton = findViewById(R.id.employeeButton);
        taskButton = findViewById(R.id.taskButton);
        lastActivty = getIntent().getStringExtra("lastActivty");
        if (lastActivty == null) {
            startService( new Intent(this, NotificationService.class));
        } else {
            switch (lastActivty) {
                case "TaskListView":
                    startService( new Intent(this, NotificationService.class));
                    break;
            }
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        startService( new Intent(this, NotificationService.class));
    }
    public void closeApp (View view) {
        finish() ;
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
                } else if (item.getItemId() == R.id.searchEmployeeOption) {
                    Intent intent = new Intent(getBaseContext(), EmployeeListView.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.loadCsvOption) {
                    searchFile();
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
                } else if (item.getItemId() == R.id.searchTaskOption) {
                    Intent intent = new Intent(getBaseContext(), TaskListView.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Not Granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri url = data.getData();
                String path = url.getPath();
                path = path.substring(path.indexOf(":") + 1);
                loadCsvFile(path);
            }
        }
    }

    private void searchFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    private void loadCsvFile(String path) {
        Log.d("MainActivity", path);
        File file = new File(path);
        EmployeeTableHelper empDb = new EmployeeTableHelper(this);
        List<Employee> employees = new ArrayList<>();
//        InputStream inputStream = getResources().openRawResource(R.raw.employee_info);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

        String line = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            line = bufferedReader.readLine();
            Log.d("MainActivity", line);
            while ((line = bufferedReader.readLine()) != null) {
                String[] token = line.split(",");
                String fullName = token[0] + " " + token[1];
                String dept = token[2];
                String level = token[3];
                String startDate = token[4];
                String email = token[5];
                employees.add(new Employee(fullName, dept, startDate, email, level));
            }
            bufferedReader.close();
            empDb.addAll(employees);
            Toast.makeText(this, "Finished Loading CSV File!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.wtf("MainActivity", "Encounter error when reading the line: " + line, e);
            Toast.makeText(this, "Failed Loading CSV File!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}


