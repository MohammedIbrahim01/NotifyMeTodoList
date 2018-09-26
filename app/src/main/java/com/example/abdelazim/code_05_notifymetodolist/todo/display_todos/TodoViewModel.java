package com.example.abdelazim.code_05_notifymetodolist.todo.display_todos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.utils.AppExecutors;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private LiveData<List<Todo>> todoList;


    public TodoViewModel(@NonNull Application application) {
        super(application);

        // get instance of AppDatabase
        final AppDatabase database = AppDatabase.getInstance(application);

        /**
         * retrieve todos
         */
        todoList = database.todoDao().getAllTodo();
    }

    public LiveData<List<Todo>> getTodoList() {
        return todoList;
    }
}
