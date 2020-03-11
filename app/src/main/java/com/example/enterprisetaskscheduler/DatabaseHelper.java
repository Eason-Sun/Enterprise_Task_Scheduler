package com.example.enterprisetaskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "list.db";
    // Common Columns
    public static final String COL_ID = "_id";
    public static final String COL_START_DATE = "`Start_Date`";
    public static final String COL_END_DATE = "`End_Date`";
    public static final String COL_STATUS = "`Status`";
    // Table for Task
    public static final String TASK_TABLE_NAME = "task";
    public static final String COL_TASK_NAME = "`Task_Name`";
    public static final String COL_EMPLOYEE_ID = "`Employee_ID`";
    public static final String COL_DESCRIPTION = "`Note`";
    public static final String COL_EMPLOYEE_NAME = "`Employee_Name`";
    // Table for Employee
    public static final String EMPLOYEE_TABLE_NAME = "employee";
    public static final String COL_FIRST_NAME = "`First Name`";
    public static final String COL_LAST_NAME = "`Last Name`";
    public static final String COL_DEPARTMENT = "Department";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Employee Table
        String createEmployeeTable = "create table " + EMPLOYEE_TABLE_NAME + " (" +
                COL_ID + " integer primary key autoincrement, " +
                COL_FIRST_NAME + " text, " +
                COL_LAST_NAME + " text, " +
                COL_DEPARTMENT  + " text, " +
                COL_START_DATE + " text)";
        db.execSQL(createEmployeeTable);
        //Create Task Table
        String createTable = "create table " + TASK_TABLE_NAME + " (" +
                COL_ID + " integer primary key autoincrement, " +
                COL_TASK_NAME + " text, " +
                COL_START_DATE + " text, " +
                COL_END_DATE + " text, " +
                COL_EMPLOYEE_ID + " integer, " +
                //COL_EMPLOYEE_NAME + " text, " +
                COL_STATUS+ " text, " +
                COL_DESCRIPTION + " text)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TASK_TABLE_NAME);
        db.execSQL("drop table if exists " + EMPLOYEE_TABLE_NAME);
        onCreate(db);
    }

    public boolean addTask(Task t) {
        ContentValues values = new ContentValues();
        values.put(COL_TASK_NAME, t.getTaskName());
        values.put(COL_START_DATE, t.getStartDate());
        values.put(COL_END_DATE, t.getEndDate());
        values.put(COL_EMPLOYEE_ID, t.getEmpId());
        //values.put(COL_EMPLOYEE_NAME, t.getEmpName());
        values.put(COL_STATUS, t.getStatus());
        values.put(COL_DESCRIPTION, t.getDescription());
        SQLiteDatabase db = getWritableDatabase();
        long res = db.insert(TASK_TABLE_NAME, null, values);
        return res != -1;
    }

    public boolean addEmployee(Employee e) {
        ContentValues values = new ContentValues();
        values.put(COL_FIRST_NAME, e.getFirstName());
        values.put(COL_LAST_NAME, e.getLastName());
        values.put(COL_DEPARTMENT, e.getDepartment());
        values.put(COL_START_DATE, e.getStartDate());
        SQLiteDatabase db = getWritableDatabase();
        long res = db.insert(EMPLOYEE_TABLE_NAME, null, values);
        return res != -1;
    }

//    public void removeEmployee(int employeeId) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "=\"" + employeeId);
//    }

    public Cursor getDataFromTable(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + tableName, null);
        return data;
    }

    public Cursor getTaskDetail(String taskID) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from  task where _id = "+ "'"+ taskID + "'", null);
        return data;
    }

    public Cursor getEmployeeInformation(String employeeID) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from  employee where _id = "+ "'"+ employeeID + "'", null);
        return data;
    }

    public String[] getEmployeeFromDept(String deptName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select `First Name`, `Last Name` from " + EMPLOYEE_TABLE_NAME + " where Department = " + "'"+ deptName + "'", null);
        data.moveToFirst();
        ArrayList<String> EmployeeNames = new ArrayList<String>();
        while(!data.isAfterLast()) {
            EmployeeNames.add(data.getString(data.getColumnIndex("First Name")) + " " + data.getString(data.getColumnIndex("Last Name")));
            data.moveToNext();
        }
        data.close();
        return EmployeeNames.toArray(new String[EmployeeNames.size()]);
    }

    public String getEmpIdFromName(String DeptName, String FirstName, String LastName) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select `_id` from " + EMPLOYEE_TABLE_NAME + " where Department = " + "'"+ DeptName + "'" +
                " AND `First Name` = " +  "'" + FirstName + "'" + " AND `Last Name` = " + "'"+LastName+"'", null);
        if (data.getCount() == 0) {
            data.close();
            return "None";
        }
        else if (data.getCount() >= 1){
            data.moveToFirst();
            String employeeID = (data.getString(data.getColumnIndex("_id")));
            data.close();
            return employeeID;
        }
        else{
            data.close();
            return "None";
        }
    }

    public Cursor searchDataBase(String tableName, String condition, String value) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + tableName + " where " +
                condition + " = " + "'"+ value + "'", null);
        return data;
    }



}
