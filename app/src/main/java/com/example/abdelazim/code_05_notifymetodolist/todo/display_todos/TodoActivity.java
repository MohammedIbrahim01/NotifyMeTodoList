package com.example.abdelazim.code_05_notifymetodolist.todo.display_todos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.done.DoneActivity;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.todo.add_edit_todo.AddEditTodoActivity;
import com.example.abdelazim.code_05_notifymetodolist.utils.AppExecutors;

import java.util.List;

public class TodoActivity extends AppCompatActivity implements TodoAdapter.ListItemClickListener {

    // intent actions
    private static final String ACTION_ADD = "action-add";
    private static final String ACTION_EDIT = "action-edit";
    public static final String KEY_TODO_ID = "key-todo-id";

    // root layout views
    private RecyclerView todoRecyclerView;
    private FloatingActionButton addFab;
    private TodoAdapter todoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        startActivity(new Intent(TodoActivity.this, DoneActivity.class));

        initializeViews();

        todoAdapter = new TodoAdapter(this, this);

        setupRecyclerView();

        setupViewModel();
    }


    /**
     * initialize views
     */
    private void initializeViews() {

        addFab = findViewById(R.id.add_fab);
        todoRecyclerView = findViewById(R.id.todo_recyclerView);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TodoActivity.this, AddEditTodoActivity.class);
                intent.setAction(ACTION_ADD);
                startActivity(intent);
            }
        });
    }


    /**
     * set LayoutManager and adapter
     * and attach ItemTouchHelper to enable swipe to delete
     */
    private void setupRecyclerView() {

        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setAdapter(todoAdapter);
        todoRecyclerView.setHasFixedSize(true);

        //enable swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                //delete the todoo from database
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        Todo todo = todoAdapter.getTodoList().get(viewHolder.getAdapterPosition());
                        todo.setDone(true);
                        AppDatabase.getInstance(getApplicationContext()).todoDao().updateTodo(todo);
                    }
                });
            }
        }).attachToRecyclerView(todoRecyclerView);
    }


    /**
     * handle click on list item
     *
     * @param index
     */
    @Override
    public void onListItemClickListener(int index) {

        Intent intent = new Intent(TodoActivity.this, AddEditTodoActivity.class);
        intent.setAction(ACTION_EDIT);
        intent.putExtra(KEY_TODO_ID, todoAdapter.getTodoList().get(index).getId());
        startActivity(intent);

        Log.i("WWW", "Title:" + todoAdapter.getTodoList().get(index).getTitle());
    }


    /**
     * setup viewModel with todoAdapter
     * <p>
     * so when the data in room changed it updates the adapter via onChanged method
     */
    private void setupViewModel() {

        TodoViewModel viewModel = ViewModelProviders.of(TodoActivity.this).get(TodoViewModel.class);
        viewModel.getTodoList().observe(TodoActivity.this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {

                todoAdapter.setTodoList(todos);
                todoAdapter.notifyDataSetChanged();

            }
        });
    }
}
