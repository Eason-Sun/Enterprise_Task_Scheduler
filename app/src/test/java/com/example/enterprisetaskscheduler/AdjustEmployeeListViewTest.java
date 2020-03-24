package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.CheckBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;

import static org.junit.Assert.*;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class AdjustEmployeeListViewTest {

    private AdjustEmployeeListView activity;
    
    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(AdjustEmployeeListView.class)
                .create()
                .start()
                .get();
    }

    @Test
    public void checkOnCreateShowButton() {
        Button apply = activity.findViewById(R.id.applyEmployeeListViewAdjust);
        Button cancel = activity.findViewById(R.id.cancelEmployeeListViewAdjust);

        assertNotNull(apply);
        assertNotNull(cancel);
    }

    @Test
    public void checkOnCreateShowCheckBox() {
        CheckBox id = activity.findViewById(R.id.checkEmployeeIDColumn);
        CheckBox name = activity.findViewById(R.id.checkEmployeeNameColumn);
        CheckBox level = activity.findViewById(R.id.checkEmployeeLevelColumn);
        CheckBox date = activity.findViewById(R.id.checkEmployeeStartDateColumn);
        CheckBox email = activity.findViewById(R.id.checkEmployeeEmailColumn);

        assertNotNull(id);
        assertNotNull(name);
        assertNotNull(level);
        assertNotNull(date);
        assertNotNull(email);
    }

    @Test
    public void buttonApplyOnClickShowActivity() {
        Button apply = activity.findViewById(R.id.applyEmployeeListViewAdjust);
        apply.performClick();

        Intent expectedIntent = new Intent(activity, EmployeeListView.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void buttonCancelOnClickShowActivity() {
        Button cancel = activity.findViewById(R.id.cancelEmployeeListViewAdjust);
        cancel.performClick();

        Intent expectedIntent = new Intent(activity, EmployeeListView.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void checkGetHeaderIndexOutput() {
        CheckBox id = activity.findViewById(R.id.checkEmployeeIDColumn);
        CheckBox name = activity.findViewById(R.id.checkEmployeeNameColumn);
        CheckBox level = activity.findViewById(R.id.checkEmployeeLevelColumn);
        CheckBox date = activity.findViewById(R.id.checkEmployeeStartDateColumn);
        CheckBox email = activity.findViewById(R.id.checkEmployeeEmailColumn);

        ArrayList<CheckBox> checkBoxArray = new ArrayList<>();
        checkBoxArray.add(id);
        checkBoxArray.add(name);
        checkBoxArray.add(level);
        checkBoxArray.add(date);
        checkBoxArray.add(email);

        id.performClick();
        level.performClick();

        assertEquals("10100", activity.getHeaderIndex(checkBoxArray));
    }
}
