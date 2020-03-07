package com.example.enterprisetaskscheduler;

public class Employee extends ListItem {
    public static final String[] DEPARTMENTS = {"R&D", "Marketing", "Manufacturing", "Sales", "Logistic"};
    private String firstName;
    private String lastName;
    private String department;
    private String startDate;


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

    public String getStartDate() {
        return startDate;
    }

    public boolean contains(String s) {
        s = s.toLowerCase();
        return Integer.toString(super.getId()).contains(s) ||
                firstName.toLowerCase().contains(s) ||
                lastName.toLowerCase().contains(s) ||
                department.toLowerCase().contains(s);
    }
}
