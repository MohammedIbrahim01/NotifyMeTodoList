package com.example.abdelazim.code_05_notifymetodolist.done;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;

import java.util.List;

public class DoneViewModel extends AndroidViewModel {

    private LiveData<List<Todo>> todoList;

    public DoneViewModel(@NonNull Application application) {
        super(application);

        todoList = AppDatabase.getInstance(application.getApplicationContext()).todoDao().getAllDone();
    }

    public LiveData<List<Todo>> getTodoList() {
        return todoList;
    }
}
