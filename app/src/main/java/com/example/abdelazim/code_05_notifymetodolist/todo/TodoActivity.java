package com.example.abdelazim.code_05_notifymetodolist.todo;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.utils.AppExecutors;

import java.util.Date;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    // priority constants
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    // root layout views
    private RecyclerView todoRecyclerView;
    private FloatingActionButton addFab;
    private CoordinatorLayout rootLayout;

    private TodoAdapter todoAdapter;

    //popup layout views
    private EditText nameEditText;
    private RadioGroup priorityRadioGroup;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        initializeRootViews();

        todoAdapter = new TodoAdapter(this);

        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setAdapter(todoAdapter);
        todoRecyclerView.setHasFixedSize(true);

        setupViewModel();
    }


    /**
     * setup viewModel with todoAdapter
     * <p>
     * so when the data in room changed it updates the adapter via onChanged method
     */
    private void setupViewModel() {

        TodoViewModel viewModel = new TodoViewModel(getApplication());
        viewModel.getTodoList().observe(TodoActivity.this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {

                todoAdapter.setTodoList(todos);
                todoAdapter.notifyDataSetChanged();

            }
        });
    }


    /**
     * initialize views of the root layout
     */
    private void initializeRootViews() {
        addFab = findViewById(R.id.add_fab);
        todoRecyclerView = findViewById(R.id.todo_recyclerView);
        rootLayout = findViewById(R.id.root_coordinatorLayout);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.new_todo_layout, null, false);
                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(rootLayout, Gravity.CENTER, 0, 0);

                popupWindow.setFocusable(true);
                popupWindow.update();
                initializePopupViews(popupView, popupWindow);
            }
        });
    }


    /**
     * initialize views of the popup layout
     * <p>
     * must be called after the popup displayed
     *
     * @param popupView
     * @param popupWindow
     */
    private void initializePopupViews(View popupView, final PopupWindow popupWindow) {

        nameEditText = popupView.findViewById(R.id.name_editText);
        priorityRadioGroup = popupView.findViewById(R.id.priority_radioGroup);
        addButton = popupView.findViewById(R.id.add_button);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int priority = getPriority();
                final String name = nameEditText.getText().toString();
                final Date date = new Date();
                popupWindow.dismiss();
                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(TodoActivity.this).todoDao().insertTodo(new Todo(name, priority, date));
                    }
                });
            }
        });

    }


    /**
     * get the priority from priorityRadioGroup
     *
     * @return
     */
    private int getPriority() {

        int id = priorityRadioGroup.getCheckedRadioButtonId();

        switch (id) {
            case R.id.high_rad:
                return PRIORITY_HIGH;

            case R.id.medium_rad:
                return PRIORITY_MEDIUM;

            case R.id.low_rad:
                return PRIORITY_LOW;

            default:
                return 0;
        }

    }
}
