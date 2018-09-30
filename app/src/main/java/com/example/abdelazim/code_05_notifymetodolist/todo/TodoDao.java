package com.example.abdelazim.code_05_notifymetodolist.todo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    /**
     * todos
     *
     * @return
     */
    @Query("SELECT * FROM todoTable WHERE done = 0 ORDER BY priority")
    LiveData<List<Todo>> getAllTodo();

    @Query("SELECT * FROM todoTable WHERE id = :id")
    LiveData<Todo> getTodoById(int id);

    @Insert
    void insertTodo(Todo todo);

    @Delete
    void deleteTodo(Todo todo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTodo(Todo todo);


    /**
     * Done
     *
     * @return
     */
    @Query("SELECT * FROM todoTable WHERE done = 1 ORDER BY priority")
    LiveData<List<Todo>> getAllDone();
}
