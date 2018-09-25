package com.example.abdelazim.code_05_notifymetodolist.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todoTable ORDER BY priority")
    List<Todo> getAllTodo();

    @Insert
    void insertTodo(Todo todo);

    @Delete
    void deleteTodo(Todo todo);

    @Update
    void updateTodo(Todo todo);
}
