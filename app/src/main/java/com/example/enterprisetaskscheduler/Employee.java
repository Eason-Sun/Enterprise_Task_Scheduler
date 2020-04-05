package com.example.enterprisetaskscheduler;

public class Employee extends ListItem {
    private String email;
    private String level;

    public Employee(String fullName, String department, String startDate, String email, String level) {
        super(fullName, department, startDate);
        this.email = email;
        this.level = level;
    }

    public boolean contains(String s) {
        s = s.toLowerCase();
        return Integer.toString(super.getId()).contains(s) ||
                super.getName().toLowerCase().contains(s) ||
                super.getDept().toLowerCase().contains(s);
    }

    public String getEmail() {
        return email;
    }

    public String getLevel() {
        return level;
    }
}
