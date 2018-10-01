package com.example.abdelazim.code_05_notifymetodolist.todo.add_edit_todo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;

class EditTodoViewModel extends ViewModel {

    // Data to cache
    private LiveData<Todo> todo;

    public EditTodoViewModel(AppDatabase database, int todoId) {

        // Get Data from the database
        todo = database.todoDao().getTodoById(todoId);
    }

    public LiveData<Todo> getTodo() {
        return todo;
    }
}
