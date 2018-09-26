package com.example.abdelazim.code_05_notifymetodolist.done;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.todo.display_todos.TodoAdapter;

import java.util.List;

public class DoneActivity extends AppCompatActivity implements TodoAdapter.ListItemClickListener {

    private RecyclerView doneRecyclerView;
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        doneRecyclerView = findViewById(R.id.done_recyclerView);

        doneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(this, this);
        doneRecyclerView.setAdapter(adapter);
        doneRecyclerView.setHasFixedSize(true);

        setupViewModel();
    }

    private void setupViewModel() {

        DoneViewModel viewModel = ViewModelProviders.of(this).get(DoneViewModel.class);
        viewModel.getTodoList().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {

                adapter.setTodoList(todos);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onListItemClickListener(int index) {

    }
}
