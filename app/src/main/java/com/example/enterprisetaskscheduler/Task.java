package com.example.enterprisetaskscheduler;


public class Task {
    private String taskName;
    private String empName;
    private String description;
    private String startDate;
    private String endDate;
    private String status;
    private int empId;
    private int id = -1;


    public Task(String taskName, String startDate, String endDate, int empId) {
        super();
        this.taskName = taskName;
        this.empId = empId;
        this.description = description;
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

    public int getId() {
        return id;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String format(String s) {
        if (!s.equals("")) {
            return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return s;
    }

    public boolean contains(String s) {
        s = s.toLowerCase();
        return Integer.toString(id).contains(s) ||
                taskName.toLowerCase().contains(s) ||
                empName.toLowerCase().contains(s) ||
                endDate.toLowerCase().contains(s);
    }

}

