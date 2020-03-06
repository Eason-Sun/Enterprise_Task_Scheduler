package com.example.enterprisetaskscheduler;

import android.os.Build;
import android.view.MenuItem;
import android.widget.Button;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import static org.junit.Assert.*;
@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void onCreate() {
        Button taskButton = mainActivity.findViewById(R.id.taskButton);
        Button employeeButton = mainActivity.findViewById(R.id.employeeButton);

        //Check if the button display
        assertNotNull(taskButton);
        assertNotNull(employeeButton);
    }

/*    @Test
    public void employeeMenuOnClick() {
        Button employeeButton = mainActivity.findViewById(R.id.employeeButton);
        MenuItem addEmployeeOption = mainActivity.findViewById(R.id.addEmployeeOption);
        MenuItem searchEmployeeOption = mainActivity.findViewById(R.id.searchEmployeeOption);

        //Check if the MenuItem display
        employeeButton.performClick();
        assertNotNull(addEmployeeOption);
        assertNotNull(searchEmployeeOption);
    }*/

/*    @Test
    public void taskMenuOnClick() {
        Button taskButton = mainActivity.findViewById(R.id.taskButton);
        MenuItem addTaskOption = mainActivity.findViewById(R.id.addTaskOption);
        MenuItem searchTaskOption = mainActivity.findViewById(R.id.searchTaskOption);

        //Check if the MenuItem display
        taskButton.performClick();
        assertNotNull(addTaskOption);
        assertNotNull(searchTaskOption);
    }*/

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }


}