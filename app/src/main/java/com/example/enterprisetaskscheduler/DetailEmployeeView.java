package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailEmployeeView extends AppCompatActivity {

    private EmployeeTableHelper empDb;
    TextView empDetailIdText, empDetailNameText, empDetailDeptText,
            empDetailEmailText ,empDetailLevelText, empDetailStartDateText;
    int empId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_emp_view);

        empDb = new EmployeeTableHelper(this);
        empId = Integer.parseInt(getIntent().getStringExtra("empId"));
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
                intent = new Intent(this, EditEmployee.class);
                intent.putExtra("empID",  Integer.toString(empId));
                this.startActivity(intent);
                break;
            case R.id.returnEditItem:
                intent = new Intent(this, EmployeeListView.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void assignOnClick(View view){
        String empNameId = empDetailNameText.getText().toString() + " " + empDetailIdText.getText().toString();
        String empDept = empDetailDeptText.getText().toString();
        String empInfo = empNameId + "," + empDept;
        Intent intent = new Intent(this, AddTask.class);
        intent.putExtra("empInfo", empInfo);
        startActivity(intent);
    }

    public void contactOnClick(View view) {
        String empName = empDetailNameText.getText().toString().split(" ")[0];
        String empEmail = empDetailEmailText.getText().toString();
        Intent intent = new Intent(this, ContactEmployee.class);
        intent.putExtra("fromActivity", "DetailEmployeeView");
        intent.putExtra("email", empEmail);
        intent.putExtra("subject", "");
        intent.putExtra("msg", "Hello " + empName + ",\n\nThank you!");
        startActivity(intent);
    }

}
