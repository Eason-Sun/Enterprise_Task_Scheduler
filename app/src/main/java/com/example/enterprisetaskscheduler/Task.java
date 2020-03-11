package com.example.enterprisetaskscheduler;


public class Task extends ListItem{
    private String dueDate;
    private int empId;
    private String description = "N/A";
    private String status;
    private String empName;


    public Task(String taskName, String startDate, String dueDate, int empId, String dept) {
        super(taskName, dept, startDate);
        this.dueDate = dueDate;
        this.empId = empId;
        this.status = "On Going";
    }

    public String getDescription() {
        return format(description);
    }

    public int getEmpId() {
        return empId;
    }


    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean contains(String s) {
        s = s.toLowerCase();
        return Integer.toString(super.getId()).contains(s) ||
                super.getName().toLowerCase().contains(s) ||
                empName.toLowerCase().contains(s) ||
                dueDate.toLowerCase().contains(s);
    }

}

