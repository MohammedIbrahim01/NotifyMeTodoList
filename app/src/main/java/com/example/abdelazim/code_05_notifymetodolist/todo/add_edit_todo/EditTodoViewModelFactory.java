package com.example.abdelazim.code_05_notifymetodolist.todo.add_edit_todo;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;

public class EditTodoViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase database;
    private int todoId;


    public EditTodoViewModelFactory(AppDatabase database, int todoId) {
        this.database = database;
        this.todoId = todoId;
    }

    // Create ViewModel and pass id
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditTodoViewModel(database, todoId);
    }
}
