package com.example.enterprisetaskscheduler;

public abstract class ListItem {
    protected static final String[] DEPARTMENTS = {"R&D", "Marketing", "Manufacturing", "Sales", "Logistic"};
    protected static final String[] LEVELS = {"Entry", "Junior", "Intermediate", "Senior"};
    private String dept;
    String startDate;
    private int id = -1;
    private String name;


    public ListItem(String name, String dept, String startDate) {
        this.name = name;
        this.dept = dept;
        this.startDate = startDate;
    }

    public String getDept() {
        return dept;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return format(name);
    }

    protected String format(String s) {
        if (!s.equals("")) {
            String[] splited = s.split("\\s+");
            StringBuilder formated = new StringBuilder();
            for (String i : splited)
                formated.append(i.substring(0, 1).toUpperCase()).append(i.substring(1).toLowerCase()).append(" ");
            return formated.toString().trim();
        }
        return s;
    }

    public abstract boolean contains(String s);
}
