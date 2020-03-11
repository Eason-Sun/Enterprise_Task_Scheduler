package com.example.enterprisetaskscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowDatePickerDialog;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowSQLiteOpenHelper;
import org.robolectric.shadows.ShadowToast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class AddEmployeeTest {
    private AddEmployee addEmployee;
    private Intent startedIntent;
    private ShadowIntent shadowIntent;
    // This path is relative to ${project_root}/src/test/resources
    // This path is used in building the absolute path for the database
    private static final String DB_PATH = "/database/list.db";
    // This will contain the absolute file path to the database
    private DatabaseHelper db;

    @Before
    public void setUp() throws Exception {
        addEmployee = Robolectric.buildActivity(AddEmployee.class).create().visible().get();
        db = new DatabaseHelper(addEmployee.getApplicationContext());


    }

    @Test
    public void onCreate() {
        ImageView taskStartDateArrow = addEmployee.findViewById(R.id.taskStartDateArrow);
        EditText empLstNameInput = addEmployee.findViewById(R.id.empLstNameInput);
        Button empAddButton = addEmployee.findViewById(R.id.empAddButton);
        Button empViewButton = addEmployee.findViewById(R.id.empViewButton);
        EditText empFstNameInput = addEmployee.findViewById(R.id.empFstNameInput);
        AutoCompleteTextView empDeptNameInput = addEmployee.findViewById(R.id.empDeptNameInput);
        ImageView empDeptArrow = addEmployee.findViewById(R.id.empDeptArrow);
        TextView empStartDateText = addEmployee.findViewById(R.id.empStartDateText);
        //Check if all above component display correct
        assertNotNull(taskStartDateArrow);
        assertNotNull(empLstNameInput);
        assertNotNull(empAddButton);
        assertNotNull(empViewButton);
        assertNotNull(empFstNameInput);
        assertNotNull(empDeptNameInput);
        assertNotNull(empDeptArrow);
        assertNotNull(empStartDateText);
    }

    @Test
    public void addOnClick() {
        //Prepare Raw Data
        String[] lstNameArray = {"Wu"};
        String[] fstNameArray = {"Beichen"};
        String[] empDeptName = {"R&D", "Marketing", "Manufacturing", "Sales", "Logistic"};
        String pattern = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar.getInstance().getTime();
        String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        //Activity Object Setup
        EditText empLstNameInput = addEmployee.findViewById(R.id.empLstNameInput);
        EditText empFstNameInput = addEmployee.findViewById(R.id.empFstNameInput);
        AutoCompleteTextView empDeptNameInput = addEmployee.findViewById(R.id.empDeptNameInput);
        TextView empStartDateText = addEmployee.findViewById(R.id.empStartDateText);
        Button empAddButton = addEmployee.findViewById(R.id.empAddButton);


        //---Case Employee added successfully
        //Setup the input
        empLstNameInput.setText(lstNameArray[0]);
        empFstNameInput.setText(fstNameArray[0]);
        empDeptNameInput.setText(empDeptName[0]);
        empStartDateText.setText(currentTime);
        empAddButton.performClick();
        //Check the toast are displayed properly
        assertEquals(ShadowToast.getTextOfLatestToast(), "New employee has been added successfully!");
        //Check that the employee information has been successfully uploaded with toast shown
        Cursor data = db.searchDataBase(db.EMPLOYEE_TABLE_NAME, "Start_Date", currentTime);
        data.moveToNext();
        assertEquals(data.getString(1), fstNameArray[0]);
        assertEquals(data.getString(2), lstNameArray[0]);
        assertEquals(data.getString(3), empDeptName[0]);
        db.close();


        //----Case Employee added not successfully
        //Setup the input
        empLstNameInput.setText("");
        empFstNameInput.setText("");
        empDeptNameInput.setText(empDeptName[0]);
        empStartDateText.setText(currentTime);
        empAddButton.performClick();
        //Check the toast are displayed properly
        assertEquals(ShadowToast.getTextOfLatestToast(), "All fields must be filled!");
    }

    @Test
    public void viewOnClick() {
        Button empViewButton = addEmployee.findViewById(R.id.empViewButton);
        empViewButton.performClick();
        startedIntent = shadowOf(addEmployee).getNextStartedActivity();
        shadowIntent = shadowOf(startedIntent);
        assertEquals(shadowIntent.getIntentClass().getName(),
                "com.example.enterprisetaskscheduler.EmployeeListView");
    }

    @Test
    public void deptOnClick() {
        ImageView empDeptArrow = addEmployee.findViewById(R.id.empDeptArrow);
        AutoCompleteTextView empDeptNameInput = addEmployee.findViewById(R.id.empDeptNameInput);
        assertEquals(empDeptArrow.performClick(), true);
        //Test ArrayAdapter
        int index = 0;
        System.out.println(empDeptNameInput.getAdapter().getCount());
        while (index < empDeptNameInput.getAdapter().getCount()) {
            assertEquals(empDeptNameInput.getAdapter().getItem(index).toString(),
                    Employee.DEPARTMENTS[index]);
            index++;
        }
    }

    @Test
    public void startDateOnClick() {
        TextView empStartDateText = addEmployee.findViewById(R.id.empStartDateText);
        ImageView taskStartDateArrow = addEmployee.findViewById(R.id.taskStartDateArrow);
        taskStartDateArrow.performClick();
        DatePickerDialog dialog = (DatePickerDialog) ShadowDatePickerDialog.getLatestDialog();
        dialog.updateDate(2013, 10, 23);
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).performClick();
        assertEquals("2013/11/23", empStartDateText.getText().toString());
    }

    @After
    public void tearDown() throws Exception {
        addEmployee = null;
    }
}