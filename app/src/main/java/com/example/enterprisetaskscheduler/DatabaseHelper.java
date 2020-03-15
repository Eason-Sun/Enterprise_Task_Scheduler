package com.example.enterprisetaskscheduler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public abstract class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "list1.db";
    protected static final String COL_ID = "_id";
    protected static final String COL_NAME = "Name";
    protected static final String COL_START_DATE = "`Start Date`";
    protected static final String COL_DEPARTMENT = "Department";

    protected static final String EMPLOYEE_TABLE_NAME = "employee";
    protected static final String COL_EMAIL = "email";
    protected static final String COL_LEVEL = "level";

    protected static final String TASK_TABLE_NAME = "task";
    protected static final String COL_EMPLOYEE_ID = "`Employee Id`";
    protected static final String COL_DESCRIPTION = "Note";
    protected static final String COL_DUE_DATE = "`Due Date`";
    protected static final String COL_STATUS = "Status";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Employee Table
        String createEmployeeTable = "create table " + EMPLOYEE_TABLE_NAME + " (" +
                COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " text, " +
                COL_DEPARTMENT + " text, " +
                COL_START_DATE + " text, " +
                COL_EMAIL + " text, " +
                COL_LEVEL + " text)";
        db.execSQL(createEmployeeTable);
        //Create Task Table
        String createTaskTable = "create table " + TASK_TABLE_NAME + " (" +
                COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " text, " +
                COL_START_DATE + " text, " +
                COL_DUE_DATE + " text, " +
                COL_EMPLOYEE_ID + " integer, " +
                COL_DEPARTMENT + " text, " +
                COL_STATUS+ " text, " +
                COL_DESCRIPTION + " text, " +
                COL_LEVEL + " text)";
        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + EMPLOYEE_TABLE_NAME);
        onCreate(db);
    }

    public abstract boolean add(ListItem item);

    protected Cursor getDataFromTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + tableName, null);
        return data;
    }

    protected Cursor getDataFromTableById(String tableName, int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + tableName + " where " + COL_ID + " = "+ "'"+ id + "'", null);
        return data;
    }

    protected ArrayList<Integer> getIdFromTableByDept(String tableName, String deptName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + tableName + " where " + COL_DEPARTMENT + "='" + deptName + "'", null);
        data.moveToFirst();
        ArrayList<Integer> itemNames = new ArrayList<>();
        while(!data.isAfterLast()) {
            itemNames.add(data.getInt(data.getColumnIndex(COL_ID)));
            data.moveToNext();
        }
        data.close();
        return itemNames;
    }

    public void removeDataFromTableById(String tableName, int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + tableName + " where " + COL_ID + "=\"" + id);
    }

}
