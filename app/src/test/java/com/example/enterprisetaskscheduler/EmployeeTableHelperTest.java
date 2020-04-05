package com.example.enterprisetaskscheduler;

import android.database.Cursor;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class EmployeeTableHelperTest {

    private EmployeeTableHelper helper;
    private Cursor cur;

    @Before
    public void setup() {
        helper = new EmployeeTableHelper(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void isEmptyDatabase() {
        cur = helper.getData();
        assertEquals(0, cur.getCount());
    }

    @Test
    public void addOneEmployeeCountDatabaseEntry() {
        Employee e = new Employee("Jane Doe",
                "Sales",
                "2020/3/14",
                "janedoe@gmail.com",
                "Entry");

        helper.add(e);
        cur = helper.getData();
        assertEquals(1, cur.getCount());
    }

    @Test
    public void addMultipleEmployeeCountDatabaseEntry() {
        int n = 5;
        String [] names = new String[] {"Jane Doe", "John Smith", "David Johnson", "Paul Wilson", "Peter Brown"};
        String [] dept = new String[] {"R&D", "Marketing", "Manufacturing", "Sales", "Logistic"};
        String [] startDate = new String[] {"2020/3/10","2020/3/11","2020/3/12","2020/3/13","2020/3/14"};
        String [] email = new String[] {"jd@gmail.com", "js@hotmail.com", "dj@outlook.com", "pw@gmail.com", "pb@yahoo.ca"};
        String [] entry = new String[] {"Entry", "Junior", "Intermediate", "Senior", "Entry"};

        Employee e;
        for (int i = 0; i<n; i++) {
            e = new Employee(names[i], dept[i], startDate[i], email[i], entry[i]);
            helper.add(e);
        }

        cur = helper.getData();
        assertEquals(n, cur.getCount());
    }

    @Test
    public void findEmployeeNameByDepartment() {
        int n = 3;
        String [] names = new String[] {"Jane Doe", "John Smith", "David Johnson"};
        String [] dept = new String[] {"R&D", "Marketing", "Manufacturing"};
        String [] startDate = new String[] {"2020/3/10","2020/3/11","2020/3/12"};
        String [] email = new String[] {"jd@gmail.com", "js@hotmail.com", "dj@outlook.com"};
        String [] entry = new String[] {"Entry", "Junior", "Intermediate"};

        Employee e;
        for (int i = 0; i<n; i++) {
            e = new Employee(names[i], dept[i], startDate[i], email[i], entry[i]);
            helper.add(e);
        }

        ArrayList<String> result = helper.getNamesByDept("Marketing");
        assertEquals("John Smith #2", result.get(0));
    }

    @Test
    public void findEmployeeNameByDepartmentMultiple() {
        int n = 3;
        String [] names = new String[] {"Jane Doe", "John Smith", "David Johnson"};
        String [] dept = new String[] {"R&D", "Marketing", "Marketing"};
        String [] startDate = new String[] {"2020/3/10","2020/3/11","2020/3/12"};
        String [] email = new String[] {"jd@gmail.com", "js@hotmail.com", "dj@outlook.com"};
        String [] entry = new String[] {"Entry", "Junior", "Intermediate"};

        Employee e;
        for (int i = 0; i<n; i++) {
            e = new Employee(names[i], dept[i], startDate[i], email[i], entry[i]);
            helper.add(e);
        }

        ArrayList<String> result = helper.getNamesByDept("Marketing");
        assertEquals(2, result.size());
    }

    @Test
    public void findEmployeeNameByDepartmentNone() {
        Employee e = new Employee("", "", "", "", "");
        helper.add(e);

        ArrayList<String> result = helper.getNamesByDept("Sales");
        assertEquals(0, result.size());
    }

    @Test
    public void findEmployeeById() {
        int n = 3;
        String [] names = new String[] {"Jane Doe", "John Smith", "David Johnson"};
        String [] dept = new String[] {"R&D", "Marketing", "Manufacturing"};
        String [] startDate = new String[] {"2020/3/10","2020/3/11","2020/3/12"};
        String [] email = new String[] {"jd@gmail.com", "js@hotmail.com", "dj@outlook.com"};
        String [] entry = new String[] {"Entry", "Junior", "Intermediate"};

        Employee e;
        for (int i = 0; i<n; i++) {
            e = new Employee(names[i], dept[i], startDate[i], email[i], entry[i]);
            helper.add(e);
        }

        int searchId = 2;
        cur = helper.getDataById(searchId);
        cur.moveToFirst();
        assertEquals(searchId, cur.getInt(0));
    }

    @Test
    public void findNameById() {
        Employee e = new Employee("Jane Doe",
                "Sales",
                "2020/3/14",
                "janedoe@gmail.com",
                "Entry");

        helper.add(e);
        assertEquals("Jane Doe", helper.getNameById(1));
    }

//    @Test
//    public void removeDataByIdSuccessful() {
//        int n = 3;
//        String [] names = new String[] {"Jane Doe", "John Smith", "David Johnson"};
//        String [] dept = new String[] {"R&D", "Marketing", "Manufacturing"};
//        String [] startDate = new String[] {"2020/3/10","2020/3/11","2020/3/12"};
//        String [] email = new String[] {"jd@gmail.com", "js@hotmail.com", "dj@outlook.com"};
//        String [] entry = new String[] {"Entry", "Junior", "Intermediate"};
//
//        Employee e;
//        for (int i = 0; i<n; i++) {
//            e = new Employee(names[i], dept[i], startDate[i], email[i], entry[i]);
//            helper.add(e);
//        }
//
//        helper.removeDataById(1);
//        assertEquals(2, helper.getData().getCount());
//    }
}
