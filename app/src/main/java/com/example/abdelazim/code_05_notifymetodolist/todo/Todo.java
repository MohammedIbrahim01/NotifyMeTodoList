package com.example.abdelazim.code_05_notifymetodolist.todo;

import java.util.Date;

public class Todo {

    private int id;
    private String title, priority;
    private Date date;

    public Todo(String title, String priority, Date date) {
        this.title = title;
        this.priority = priority;
        this.date = date;
    }

    public Todo(int id, String title, String priority, Date date) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}