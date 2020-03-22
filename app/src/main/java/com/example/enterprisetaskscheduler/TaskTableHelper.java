package com.example.enterprisetaskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TaskTableHelper extends DatabaseHelper {

    public TaskTableHelper(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean add(ListItem item) {
        Task t = (Task) item;
        ContentValues values = new ContentValues();
        values.put(COL_NAME, t.getName());
        values.put(COL_START_DATE, t.getStartDate());
        values.put(COL_DUE_DATE, t.getDueDate());
        values.put(COL_EMPLOYEE_ID, t.getEmpId());
        values.put(COL_DEPARTMENT, t.getDept());
        values.put(COL_STATUS, t.getStatus());
        values.put(COL_DESCRIPTION, t.getDescription());
        values.put(COL_LEVEL, t.getLevel());
        SQLiteDatabase db = getWritableDatabase();
        long res = db.insert(TASK_TABLE_NAME, null, values);
        return res != -1;
    }

    public boolean modify(ListItem item, int taskID) {
        Task t = (Task) item;
        ContentValues values = new ContentValues();
        values.put(COL_NAME, t.getName());
        values.put(COL_START_DATE, t.getStartDate());
        values.put(COL_DUE_DATE, t.getDueDate());
        values.put(COL_EMPLOYEE_ID, t.getEmpId());
        values.put(COL_DEPARTMENT, t.getDept());
        values.put(COL_STATUS, t.getStatus());
        values.put(COL_DESCRIPTION, t.getDescription());
        values.put(COL_LEVEL, t.getLevel());
        SQLiteDatabase db = getWritableDatabase();
        long res = db.update(TASK_TABLE_NAME, values, "_id = " + taskID, null);
        return res != -1;
    }

    public Cursor getData() {
        return super.getDataFromTable(TASK_TABLE_NAME);
    }

    public Cursor getDataById(int id) {
        return super.getDataFromTableById(TASK_TABLE_NAME, id);
    }

    public Cursor getDataByDueDate(String Date) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + TASK_TABLE_NAME + " where " + COL_DUE_DATE + " = "+ "'"+ Date + "'", null);
        return data;
    }

    public ArrayList<Integer> getIdByDept(String deptName) {
        return super.getIdFromTableByDept(TASK_TABLE_NAME, deptName);
    }


    public void removeDataById(int id) {
        super.removeDataFromTableById(TASK_TABLE_NAME, id);
    }
}
