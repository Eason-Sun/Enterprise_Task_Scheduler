package com.example.enterprisetaskscheduler;


import android.content.Intent;

public class Employee{
    public static final String[] DEPARTMENTS = {"R&D", "Marketing", "Manufacturing", "Sales", "Logistic"};
    private String firstName;
    private String lastName;
    private String department;
    private String startDate;
    private int id = -1;


    public Employee(String firstName, String lastName, String department, String startDate) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.startDate = startDate;
    }

    public String getFirstName() {
        return format(firstName);
    }

    public String getLastName() {
        return format(lastName);
    }

    public String getDepartment() {
        return department;
    }

    public String getStartDate() { return startDate; }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private String format(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public boolean contains(String s) {
        s = s.toLowerCase();
        return Integer.toString(id).contains(s) ||
                firstName.toLowerCase().contains(s) ||
                lastName.toLowerCase().contains(s) ||
                department.toLowerCase().contains(s);
    }
}
