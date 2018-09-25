package com.example.abdelazim.code_05_notifymetodolist.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.abdelazim.code_05_notifymetodolist.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private RecyclerView todoRecyclerView;
    private List<Todo> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todoRecyclerView = (RecyclerView) findViewById(R.id.todo_recyclerView);

        todoList = new ArrayList<>();
        todoList.add(new Todo("drink water1", "1", new Date()));
        todoList.add(new Todo("drink water2", "2", new Date()));
        todoList.add(new Todo("drink water3", "1", new Date()));

        TodoAdapter todoAdapter = new TodoAdapter();
        todoAdapter.setTodoList(todoList);

        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setAdapter(todoAdapter);
        todoRecyclerView.setHasFixedSize(true);
    }
}
