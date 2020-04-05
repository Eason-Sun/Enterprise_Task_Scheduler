package com.example.enterprisetaskscheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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
import org.robolectric.shadows.ShadowToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
public class AddTaskTest {
    private AddTask addTask;
    private Intent startedIntent;
    private ShadowIntent shadowIntent;
    private TaskTableHelper db;
    private EmployeeTableHelper empdb;

    @Before
    public void setUp() throws Exception {
        addTask = Robolectric.buildActivity(AddTask.class).create().visible().get();
        db = new TaskTableHelper(addTask.getApplicationContext());
        empdb = new EmployeeTableHelper(addTask.getApplicationContext());
    }

    @Test
    public void onCreate() {
        AutoCompleteTextView taskEmpNameInput = addTask.findViewById(R.id.taskEmpNameInput);
        ImageView taskEndDateArrow= addTask.findViewById(R.id.taskEndDateArrow);
        ImageView taskStartDateArrow = addTask.findViewById(R.id.taskStartDateArrow);
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
        //Prepare Raw Data
        String[] taskNameArray = {"Blending", "Testing"};
        String[] taskDeptName = {"R&D", "Marketing", "Manufacturing", "Sales", "Logistic"};
        String[] taskEmpNameArray = {"Beichen Wu"};
        String[] taskEmpIDArray = {"1"};
        String[] taskDes = {"Test1", "Test2"};
        String pattern = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar.getInstance().getTime();
        String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        //Activity Object Setup
        EditText taskNameInput = addTask.findViewById(R.id.taskNameInput);
        EditText taskDeptNameInput = addTask.findViewById(R.id.taskDeptNameInput);
        EditText taskEmpNameInput = addTask.findViewById(R.id.taskEmpNameInput);
        TextView taskStartDateText = addTask.findViewById(R.id.taskStartDateText);
        TextView taskEndDateText = addTask.findViewById(R.id.taskEndDateText);
        EditText taskDescriptionInput = addTask.findViewById(R.id.taskDescriptionInput);
        Button taskAddButton = addTask.findViewById(R.id.taskAddButton);

        //---Case Task added successfully
        //Pre-set Employee
        String fullName = taskEmpNameArray[0];
        String dept = taskDeptName[0];
        String startDate = currentTime;
        String level = ListItem.LEVELS[0];
        String email = "beichenwu$@gmail.com";
        Employee e = new Employee(fullName, dept, startDate, email, level);
        boolean res = empdb.add(e);
        //Setup the input
        taskNameInput.setText(taskNameArray[0]);
        taskDeptNameInput.setText(taskDeptName[0]);
        taskEmpNameInput.setText(taskEmpNameArray[0]+ " #" + taskEmpIDArray[0]);
        taskStartDateText.setText(currentTime);
        taskEndDateText.setText(currentTime);
        taskDescriptionInput.setText(taskDes[0]);
        taskAddButton.performClick();
        //Check the toast are displayed properly
        assertEquals(ShadowToast.getTextOfLatestToast(), "New Task has been added successfully!");
        //Check that the employee information has been successfully uploaded with toast shown
        Cursor data = db.searchDataBase(db.TASK_TABLE_NAME, "`Start Date`", currentTime);
        data.moveToNext();
        assertEquals(data.getString(1), taskNameArray[0]);
        assertEquals(data.getString(2), currentTime);
        assertEquals(data.getString(3), currentTime);
        assertEquals(data.getString(4), taskEmpIDArray[0]);
        assertEquals(data.getString(5), taskDeptName[0]);
        assertEquals(data.getString(7), taskDes[0]);
        db.close();

        //----Case Employee added not successfully
        //Setup the input
        taskNameInput.setText("");
        taskDeptNameInput.setText("");
        taskEmpNameInput.setText(taskEmpNameArray[0]);
        taskStartDateText.setText("");
        taskEndDateText.setText(currentTime);
        taskDescriptionInput.setText(taskDes[0]);
        taskAddButton.performClick();
        //Check the toast are displayed properly
        assertEquals(ShadowToast.getTextOfLatestToast(), "All fields must be filled!");
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
