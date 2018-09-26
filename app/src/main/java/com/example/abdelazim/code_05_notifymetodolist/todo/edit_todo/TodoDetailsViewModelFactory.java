package com.example.abdelazim.code_05_notifymetodolist.todo.edit_todo;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;

public class TodoDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase database;
    private final int todoId;

    public TodoDetailsViewModelFactory(AppDatabase database, int todoId) {
        this.database = database;
        this.todoId = todoId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TodoDetailsViewModel(database, todoId);
    }
}
