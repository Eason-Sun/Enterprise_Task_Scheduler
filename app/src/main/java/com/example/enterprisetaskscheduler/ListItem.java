package com.example.enterprisetaskscheduler;

public abstract class ListItem {
    private int id = -1;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    protected String format(String s) {
        if (!s.equals("")) {
            return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return s;
    }

    public abstract boolean contains(String s);
}
