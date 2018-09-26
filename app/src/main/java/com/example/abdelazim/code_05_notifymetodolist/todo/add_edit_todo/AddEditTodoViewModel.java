package com.example.abdelazim.code_05_notifymetodolist.todo.add_edit_todo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;

class AddEditTodoViewModel extends ViewModel {

    private LiveData<Todo> todo;

    public AddEditTodoViewModel(AppDatabase database, int todoId) {

        todo = database.todoDao().getTodoById(todoId);
    }

    public LiveData<Todo> getTodo() {
        return todo;
    }
}
