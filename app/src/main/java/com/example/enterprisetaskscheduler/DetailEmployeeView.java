package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailEmployeeView extends AppCompatActivity {

    private EmployeeTableHelper empDb;
    TextView empDetailIdText, empDetailNameText, empDetailDeptText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_emp_view);
        TextView empDetailLevelText, empDetailStartDateText, empDetailEmailText;

        empDb = new EmployeeTableHelper(this);
        int empId = Integer.parseInt(getIntent().getStringExtra("empId"));
        Cursor data = empDb.getDataById(empId);

        empDetailIdText = findViewById(R.id.empDetailIdText);
        empDetailNameText = findViewById(R.id.empDetailNameText);
        empDetailLevelText = findViewById(R.id.empDetailLevelText);
        empDetailDeptText = findViewById(R.id.empDetailDeptText);
        empDetailStartDateText = findViewById(R.id.empDetailStartDateText);
        empDetailEmailText = findViewById(R.id.empDetailEmailText);

        data.moveToFirst();
        empDetailIdText.setText("#" + data.getString(0));
        empDetailNameText.setText(data.getString(1));
        empDetailDeptText.setText(data.getString(2));
        empDetailStartDateText.setText(data.getString(3));
        empDetailEmailText.setText(data.getString(4));
        empDetailLevelText.setText(data.getString(5));
    }

    public void assignOnClick(View view){
        String empNameId = empDetailNameText.getText().toString() + " " + empDetailIdText.getText().toString();
        String empDept = empDetailDeptText.getText().toString();
        String empInfo = empNameId + "," + empDept;
        Intent intent = new Intent(this, AddTask.class);
        intent.putExtra("empInfo", empInfo);
        startActivity(intent);
    }

}
