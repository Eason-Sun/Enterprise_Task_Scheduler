package com.example.enterprisetaskscheduler;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.CheckBox;

import org.apache.tools.ant.taskdefs.Parallel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class AdjustTaskListViewTest {

    private AdjustTaskListView activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(AdjustTaskListView.class)
                .create()
                .start()
                .get();
    }

    @Test
    public void checkOnCreateShowButton() {
        Button apply = activity.findViewById(R.id.applyTaskListViewAdjust);
        Button cancel = activity.findViewById(R.id.cancelTaskListViewAdjust);

        assertNotNull(apply);
        assertNotNull(cancel);
    }

    @Test
    public void checkOnCreateShowCheckBox() {
        CheckBox id = activity.findViewById(R.id.checkIDColumn);
        CheckBox taskName = activity.findViewById(R.id.checkTaskNameColumn);
        CheckBox level = activity.findViewById(R.id.checkTaskLevelColumn);
        CheckBox dept = activity.findViewById(R.id.checkTaskDeptColumn);
        CheckBox empName = activity.findViewById(R.id.checkTaskEmpNameColumn);
        CheckBox status = activity.findViewById(R.id.checkTaskStatusColumn);
        CheckBox start = activity.findViewById(R.id.checkTaskEndDateColumn);
        CheckBox end = activity.findViewById(R.id.checkTaskStartDateColumn);

        assertNotNull(id);
        assertNotNull(taskName);
        assertNotNull(level);
        assertNotNull(dept);
        assertNotNull(empName);
        assertNotNull(status);
        assertNotNull(start);
        assertNotNull(end);
    }

    @Test
    public void buttonApplyOnClickShowActivity() {
        Button apply = activity.findViewById(R.id.applyTaskListViewAdjust);
        apply.performClick();

        Intent expectedIntent = new Intent(activity, TaskListView.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void buttonCancelOnClickShowActivity() {
        Button cancel = activity.findViewById(R.id.cancelTaskListViewAdjust);
        cancel.performClick();

        Intent expectedIntent = new Intent(activity, TaskListView.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void checkGetHeaderIndexOutput() {
        CheckBox id = activity.findViewById(R.id.checkIDColumn);
        CheckBox taskName = activity.findViewById(R.id.checkTaskNameColumn);
        CheckBox level = activity.findViewById(R.id.checkTaskLevelColumn);
        CheckBox dept = activity.findViewById(R.id.checkTaskDeptColumn);
        CheckBox empName = activity.findViewById(R.id.checkTaskEmpNameColumn);
        CheckBox status = activity.findViewById(R.id.checkTaskStatusColumn);
        CheckBox start = activity.findViewById(R.id.checkTaskEndDateColumn);
        CheckBox end = activity.findViewById(R.id.checkTaskStartDateColumn);

        ArrayList<CheckBox> checkBoxArray = new ArrayList<>();
        checkBoxArray.add(id);
        checkBoxArray.add(taskName);
        checkBoxArray.add(level);
        checkBoxArray.add(dept);
        checkBoxArray.add(empName);
        checkBoxArray.add(status);
        checkBoxArray.add(start);
        checkBoxArray.add(end);

        id.performClick();
        level.performClick();
        status.performClick();

        assertEquals("10100100", activity.getHeaderIndex(checkBoxArray));
    }
}
