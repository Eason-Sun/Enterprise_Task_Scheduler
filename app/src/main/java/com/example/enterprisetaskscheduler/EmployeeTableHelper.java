package com.example.enterprisetaskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EmployeeTableHelper extends DatabaseHelper {


    public EmployeeTableHelper(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean add(ListItem item) {
        Employee e = (Employee) item;
        ContentValues values = new ContentValues();
        values.put(COL_NAME, e.getName());
        values.put(COL_DEPARTMENT, e.getDept());
        values.put(COL_START_DATE, e.getStartDate());
        values.put(COL_EMAIL, e.getEmail());
        values.put(COL_LEVEL, e.getLevel());
        SQLiteDatabase db = getWritableDatabase();
        long res = db.insert(EMPLOYEE_TABLE_NAME, null, values);
        return res != -1;
    }

    public Cursor getData() {
        return super.getDataFromTable(EMPLOYEE_TABLE_NAME);
    }

    public Cursor getDataById(int id) {return super.getDataFromTableById(EMPLOYEE_TABLE_NAME, id);}

    public String getNameById(int id) {
        Cursor data = getDataById(id);
        data.moveToFirst();
        return data.getString(1);
    }

    public ArrayList<String> getNamesByDept(String deptName) {
        ArrayList<Integer> empIds =  super.getIdFromTableByDept(EMPLOYEE_TABLE_NAME, deptName);
        ArrayList<String> empNameIds = new ArrayList<>();
        for (int id : empIds) {
            String name = getNameById(id);
            empNameIds.add(name + " #" + id);
        }
        return empNameIds;
    }

    public boolean modify(ListItem item, int empID) {
        Employee e = (Employee) item;
        ContentValues values = new ContentValues();
        values.put(COL_NAME, e.getName());
        values.put(COL_DEPARTMENT, e.getDept());
        values.put(COL_START_DATE, e.getStartDate());
        values.put(COL_EMAIL, e.getEmail());
        values.put(COL_LEVEL, e.getLevel());
        SQLiteDatabase db = getWritableDatabase();
        long res = db.update(EMPLOYEE_TABLE_NAME, values, "_id = " + empID, null);
        return res != -1;
    }

    public void removeDataById(int id) {
        super.removeDataFromTableById(EMPLOYEE_TABLE_NAME, id);
    }
}
