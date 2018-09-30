package com.example.abdelazim.code_05_notifymetodolist.todo.display_todos;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.todo.add_edit_todo.AddEditTodoActivity;
import com.example.abdelazim.code_05_notifymetodolist.todo_done.TodoDoneAdapter;
import com.example.abdelazim.code_05_notifymetodolist.utils.AppExecutors;

import java.util.List;

public class TodoFragment extends Fragment implements TodoDoneAdapter.ListItemClickListener {

    // intent actions
    private static final String ACTION_ADD = "action-add";
    private static final String ACTION_EDIT = "action-edit";
    public static final String KEY_TODO_ID = "key-todo-id";
    // layout views
    private RecyclerView todoRecyclerView;
    private FloatingActionButton addFab;
    private TextView todoIsEmptyTextView;
    private TodoDoneAdapter todoAdapter;

    //Mandatory constructor to instantiate the Fragment
    public TodoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.todo_layout, container, false);

        todoAdapter = new TodoDoneAdapter(getContext(), this);

        initViews(view);

        setupRecyclerView();

        setupViewModel();

        return view;
    }


    /**
     * initialize views
     *
     * @param view
     */
    private void initViews(View view) {

        //get reference to views
        addFab = view.findViewById(R.id.add_fab);
        todoRecyclerView = view.findViewById(R.id.todo_recyclerView);
        todoIsEmptyTextView = view.findViewById(R.id.todo_is_empty_textView);

        // handling click on addFab
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddEditTodoActivity.class);
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

        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

                //make the todoo marked as done
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        Todo todo = todoAdapter.getTodoList().get(viewHolder.getAdapterPosition());
                        todo.setDone(1);
                        AppDatabase.getInstance(getContext()).todoDao().updateTodo(todo);
                    }
                });
            }
        }).attachToRecyclerView(todoRecyclerView);
    }


    /**
     * setup viewModel with todoAdapter
     * <p>
     * so when the data in room changed it updates the adapter via onChanged method
     */
    private void setupViewModel() {

        TodoViewModel viewModel = ViewModelProviders.of(getActivity()).get(TodoViewModel.class);
        viewModel.getTodoList().observe(getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {

                todoAdapter.setTodoList(todos);
                todoAdapter.notifyDataSetChanged();

                if (todoAdapter.getItemCount() == 0)
                    displayTodoIsEmptyMessage();
                else
                    displayTodoList();

            }
        });
    }

    private void displayTodoList() {
        todoIsEmptyTextView.setVisibility(View.INVISIBLE);
        todoRecyclerView.setVisibility(View.VISIBLE);
    }

    private void displayTodoIsEmptyMessage() {
        todoRecyclerView.setVisibility(View.INVISIBLE);
        todoIsEmptyTextView.setVisibility(View.VISIBLE);
    }


    /**
     * handle click on list item
     *
     * @param index
     */
    @Override
    public void onListItemClickListener(int index) {

        Intent intent = new Intent(getActivity(), AddEditTodoActivity.class);
        intent.setAction(ACTION_EDIT);
        intent.putExtra(KEY_TODO_ID, todoAdapter.getTodoList().get(index).getId());
        startActivity(intent);

        Log.i("WWW", "Title:" + todoAdapter.getTodoList().get(index).getTitle());
    }

}
