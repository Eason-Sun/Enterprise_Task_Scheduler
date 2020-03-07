package com.example.enterprisetaskscheduler;


public class Task extends ListItem{
    private String taskName;
    private String empName;
    private String description;
    private String startDate;
    private String endDate;
    private String status;
    private int empId;


    public Task(String taskName, String startDate, String endDate, int empId) {
        super();
        this.taskName = taskName;
        this.empId = empId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "On Going";
    }

    public String getTaskName() {
        return format(taskName);
    }

    public String getDescription() {
        return format(description);
    }

    public String getEmpId() {
        return String.valueOf(empId);
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
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
                taskName.toLowerCase().contains(s) ||
                empName.toLowerCase().contains(s) ||
                endDate.toLowerCase().contains(s);
    }

}

