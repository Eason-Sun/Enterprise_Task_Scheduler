package com.example.enterprisetaskscheduler;


public class Task extends ListItem{
    protected static final String[] STATUS = {"On Going", "Completed", "Canceled"};
    private String dueDate;
    private int empId;
    private String description = "N/A";
    private String status;
    private String empName;
    private String level;


    public Task(String taskName, String startDate, String dueDate, int empId, String dept) {
        super(taskName, dept, startDate);
        this.dueDate = dueDate;
        this.empId = empId;
        this.status = STATUS[0];
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

    public String getStatus() { return status; }

    public String getEmpName() {
        return empName;
    }

    public String getLevel(){return level;}

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean contains(String s) {
        s = s.toLowerCase();
        return Integer.toString(super.getId()).contains(s) ||
                super.getName().toLowerCase().contains(s) ||
                empName.toLowerCase().contains(s) ||
                dueDate.toLowerCase().contains(s);
    }

}

