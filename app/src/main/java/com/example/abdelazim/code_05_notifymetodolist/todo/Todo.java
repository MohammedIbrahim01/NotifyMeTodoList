package com.example.abdelazim.code_05_notifymetodolist.todo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todoTable")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int priority;
    private Date date;
    private int done = 0;

    @Ignore
    public Todo(String title, int priority, Date date) {
        this.title = title;
        this.priority = priority;
        this.date = date;
    }

    public Todo(int id, String title, int priority, Date date) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }
}