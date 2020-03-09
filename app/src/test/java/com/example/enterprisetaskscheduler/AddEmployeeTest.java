package com.example.enterprisetaskscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import org.robolectric.shadows.ShadowPopupMenu;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class AddEmployeeTest {
    private AddEmployee addEmployee;
    private Intent startedIntent;
    private ShadowIntent shadowIntent;

    @Before
    public void setUp() throws Exception {
        addEmployee = Robolectric.buildActivity(AddEmployee.class).create().visible().get();
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
        empDeptArrow.performClick();

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