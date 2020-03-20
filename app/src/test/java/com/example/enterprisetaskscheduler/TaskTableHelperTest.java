package com.example.enterprisetaskscheduler;

import android.database.Cursor;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class TaskTableHelperTest {

    private TaskTableHelper helper;
    private Cursor cur;

    @Before
    public void setup() {
        helper = new TaskTableHelper(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void isEmptyDatabase() {
        cur = helper.getData();
        assertEquals(0, cur.getCount());
    }

    @Test
    public void addOneTaskCountDatabaseEntry() {
        Task t = new Task("Testing",
                "2020/3/17",
                "2020/3/20",
                2,
                "Sales");

        helper.add(t);
        cur = helper.getData();
        assertEquals(1, cur.getCount());
    }

    @Test
    public void addMultipleTaskCountDatabaseEntry() {
        int n = 3;
        String [] taskName = new String[] {"Testing", "Debugging", "Deploying"};
        String [] startDate = new String[] {"2020/3/10","2020/3/11","2020/3/12"};
        String [] dueDate = new String[] {"2020/3/20","2020/3/21","2020/3/22"};
        Integer [] id = new Integer[] {1, 2, 3};
        String [] dept = new String[] {"R&D", "R&D", "Manufacturing"};

        Task t;
        for (int i = 0; i<n; i++) {
            t = new Task(taskName[i], startDate[i], dueDate[i], id[i], dept[i]);
            helper.add(t);
        }

        cur = helper.getData();
        assertEquals(n, cur.getCount());
    }

    @Test
    public void findTaskById() {
        int n = 3;
        String [] taskName = new String[] {"Testing", "Debugging", "Deploying"};
        String [] startDate = new String[] {"2020/3/10","2020/3/11","2020/3/12"};
        String [] dueDate = new String[] {"2020/3/20","2020/3/21","2020/3/22"};
        Integer [] id = new Integer[] {4, 5, 6};
        String [] dept = new String[] {"R&D", "Sales", "Manufacturing"};

        Task t;
        for (int i = 0; i<n; i++) {
            t = new Task(taskName[i], startDate[i], dueDate[i], id[i], dept[i]);
            helper.add(t);
        }

        int searchId = 2;
        cur = helper.getDataById(searchId);
        cur.moveToFirst();
        assertEquals(searchId, cur.getInt(0));
    }
}
