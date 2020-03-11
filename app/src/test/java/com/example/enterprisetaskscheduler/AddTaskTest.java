package com.example.enterprisetaskscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDatePickerDialog;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class AddTaskTest {
    private AddTask addTask;
    private Intent startedIntent;
    private ShadowIntent shadowIntent;

    @Before
    public void setUp() throws Exception {
        addTask = Robolectric.buildActivity(AddTask.class).create().visible().get();
    }

    @Test
    public void onCreate() {
        AutoCompleteTextView taskEmpNameInput = addTask.findViewById(R.id.taskEmpNameInput);
        ImageView taskEndDateArrow= addTask.findViewById(R.id.taskEndDateArrow);
        ImageView taskStartDateArrow = addTask.findViewById(R.id.taskStartDateArrow);
        TextView taskEmpIdText = addTask.findViewById(R.id.taskEmpIdText);
        TextView taskEndDateText = addTask.findViewById(R.id.taskEndDateText);
        ImageView taskDeptArrow = addTask.findViewById(R.id.taskDeptArrow);
        EditText taskNameInput = addTask.findViewById(R.id.taskNameInput);
        TextView taskStartDateText = addTask.findViewById(R.id.taskStartDateText);
        EditText taskDescriptionInput = addTask.findViewById(R.id.taskDescriptionInput);
        Button taskAddButton = addTask.findViewById(R.id.taskAddButton);
        Button taskViewButton = addTask.findViewById(R.id.taskViewButton);
        AutoCompleteTextView taskDeptNameInput = addTask.findViewById(R.id.taskDeptNameInput);
        //Check if all above component display correct
        assertNotNull(taskEmpNameInput);
        assertNotNull(taskEndDateArrow);
        assertNotNull(taskStartDateArrow);
        assertNotNull(taskEmpIdText);
        assertNotNull(taskEndDateText);
        assertNotNull(taskDeptArrow);
        assertNotNull(taskNameInput);
        assertNotNull(taskStartDateText);
        assertNotNull(taskDescriptionInput);
        assertNotNull(taskAddButton);
        assertNotNull(taskViewButton);
        assertNotNull(taskDeptNameInput);
    }

    @Test
    public void deptOnClick() {
        ImageView taskDeptArrow = addTask.findViewById(R.id.taskDeptArrow);
        AutoCompleteTextView taskEmpNameInput = addTask.findViewById(R.id.taskEmpNameInput);
        assertEquals(taskDeptArrow.performClick(), true);

        //Test ArrayAdapter
        int index = 0;
        System.out.println(taskEmpNameInput.getAdapter().getCount());
        while (index < taskEmpNameInput.getAdapter().getCount()) {
            assertEquals(taskEmpNameInput.getAdapter().getItem(index).toString(),
                    Employee.DEPARTMENTS[index]);
            index++;
        }
    }

    @Test
    public void startDateOnClick() {
        TextView taskStartDateText = addTask.findViewById(R.id.taskStartDateText);
        ImageView taskStartDateArrow = addTask.findViewById(R.id.taskStartDateArrow);
        taskStartDateArrow.performClick();
        DatePickerDialog dialog = (DatePickerDialog) ShadowDatePickerDialog.getLatestDialog();
        dialog.updateDate(2013, 10, 23);
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).performClick();
        assertEquals("2013/11/23", taskStartDateText.getText().toString());
    }

    @Test
    public void endDateOnClick() {
        TextView taskEndDateText = addTask.findViewById(R.id.taskEndDateText);
        ImageView taskEndDateArrow = addTask.findViewById(R.id.taskEndDateArrow);
        taskEndDateArrow.performClick();
        DatePickerDialog dialog = (DatePickerDialog) ShadowDatePickerDialog.getLatestDialog();
        dialog.updateDate(2013, 10, 23);
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).performClick();
        assertEquals("2013/11/23", taskEndDateText.getText().toString());
    }

    @Test
    public void addOnClick() {
    }

    @Test
    public void viewOnClick() {
        Button taskViewButton = addTask.findViewById(R.id.taskViewButton);
        taskViewButton.performClick();
        startedIntent = shadowOf(addTask).getNextStartedActivity();
        shadowIntent = shadowOf(startedIntent);
        assertEquals(shadowIntent.getIntentClass().getName(),
                "com.example.enterprisetaskscheduler.TaskListView");
    }
}