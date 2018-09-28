package com.example.abdelazim.code_05_notifymetodolist.done;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.todo.display_todos.TodoAdapter;

import java.util.List;

public class DoneFragment extends Fragment implements TodoAdapter.ListItemClickListener {

    private RecyclerView doneRecyclerView;
    private TodoAdapter adapter;


    // Mandatory constructor to instantiate the fragment
    public DoneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        adapter = new TodoAdapter(getContext(), this);

        View view = inflater.inflate(R.layout.done_layout, container, false);

        initViews(view);

        setupRecyclerView();

        setupViewModel();

        return view;
    }


    /**
     * set layoutManager and adapter for the RecyclerView
     */
    private void setupRecyclerView() {
        doneRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        doneRecyclerView.setAdapter(adapter);
        doneRecyclerView.setHasFixedSize(true);
    }


    /**
     * setup viewModel for this fragment
     */
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


    /**
     * Initialize views
     *
     * @param view
     */
    private void initViews(View view) {
        doneRecyclerView = view.findViewById(R.id.done_recyclerView);
    }

    @Override
    public void onListItemClickListener(int index) {

    }
}
