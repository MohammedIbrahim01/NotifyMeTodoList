package com.example.abdelazim.code_05_notifymetodolist.todo.edit_todo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;

public class TodoDetailsViewModel extends ViewModel {

    LiveData<Todo> todo;

    public TodoDetailsViewModel(AppDatabase database, int todoId) {

        todo = database.todoDao().getTodoById(todoId);
    }

    public LiveData<Todo> getTodo() {
        return todo;
    }
}
