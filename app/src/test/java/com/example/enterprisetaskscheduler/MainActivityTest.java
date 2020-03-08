package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenu;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.ShadowPopupMenu;



import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity mainActivity;
    private Intent startedIntent;
    private ShadowIntent shadowIntent;


    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().get();
    }
    @Test
    public void onCreate() {
        Button taskButton = mainActivity.findViewById(R.id.taskButton);
        Button employeeButton = mainActivity.findViewById(R.id.employeeButton);

        //Check if the button display

        assertNotNull(taskButton);
        assertNotNull(employeeButton);
    }

    @Test
    public void employeeMenuOnClick() throws Exception {
        Button employeeButton = mainActivity.findViewById(R.id.employeeButton);
        employeeButton.performClick();
        PopupMenu latestPopupMenu = ShadowPopupMenu.getLatestPopupMenu();
        Menu menu = latestPopupMenu.getMenu();
        assertEquals(menu.getItem(0).toString(), "Add Employee");
        assertEquals(menu.getItem(1).toString(), "Search Employee");

        //Test on for addEmployeeOption
        shadowOf(latestPopupMenu).getOnMenuItemClickListener()
                .onMenuItemClick(new RoboMenuItem(R.id.addEmployeeOption));
        startedIntent = shadowOf(mainActivity).getNextStartedActivity();
        shadowIntent = shadowOf(startedIntent);
        assertEquals(shadowIntent.getIntentClass().getName(),
                "com.example.enterprisetaskscheduler.AddEmployee");

        //Test on for searchEmployeeOption
        shadowOf(latestPopupMenu).getOnMenuItemClickListener()
                .onMenuItemClick(new RoboMenuItem(R.id.searchEmployeeOption));
        startedIntent = shadowOf(mainActivity).getNextStartedActivity();
        shadowIntent = shadowOf(startedIntent);
        assertEquals(shadowIntent.getIntentClass().getName(),
                "com.example.enterprisetaskscheduler.EmployeeListView");
    }

    @Test
    public void taskMenuOnClick() {
        Button taskButton = mainActivity.findViewById(R.id.taskButton);
        taskButton.performClick();
        PopupMenu latestPopupMenu = ShadowPopupMenu.getLatestPopupMenu();
        Menu menu = latestPopupMenu.getMenu();
        assertEquals(menu.getItem(0).toString(), "Add Task");
        assertEquals(menu.getItem(1).toString(), "Search Task");

        //Test on for addTaskOption
        shadowOf(latestPopupMenu).getOnMenuItemClickListener()
                .onMenuItemClick(new RoboMenuItem(R.id.addTaskOption));
        startedIntent = shadowOf(mainActivity).getNextStartedActivity();
        shadowIntent = shadowOf(startedIntent);
        assertEquals(shadowIntent.getIntentClass().getName(),
                "com.example.enterprisetaskscheduler.AddTask");

        //Test on for searchTaskOption
        shadowOf(latestPopupMenu).getOnMenuItemClickListener()
                .onMenuItemClick(new RoboMenuItem(R.id.searchTaskOption));
        startedIntent = shadowOf(mainActivity).getNextStartedActivity();
        shadowIntent = shadowOf(startedIntent);
        assertEquals(shadowIntent.getIntentClass().getName(),
                "com.example.enterprisetaskscheduler.TaskListView");
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }


}