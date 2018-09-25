package com.example.abdelazim.code_05_notifymetodolist.todo;

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
import android.widget.Toast;

import com.example.abdelazim.code_05_notifymetodolist.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    private RecyclerView todoRecyclerView;
    private List<Todo> todoList;

    private FloatingActionButton addFab;
    private CoordinatorLayout rootLayout;

    private EditText nameEditText;
    private RadioGroup priorityRadioGroup;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        initialize();

        todoList = new ArrayList<>();
        todoList.add(new Todo("drink water1", 1, new Date()));
        todoList.add(new Todo("drink water2", 2, new Date()));
        todoList.add(new Todo("drink water3", 1, new Date()));

        TodoAdapter todoAdapter = new TodoAdapter();
        todoAdapter.setTodoList(todoList);

        todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoRecyclerView.setAdapter(todoAdapter);
        todoRecyclerView.setHasFixedSize(true);
    }

    private void initialize() {
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

    private void initializePopupViews(View popupView, final PopupWindow popupWindow) {

        nameEditText = popupView.findViewById(R.id.name_editText);
        priorityRadioGroup = popupView.findViewById(R.id.priority_radioGroup);
        addButton = popupView.findViewById(R.id.add_button);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int priority = getPriority();
                String name = nameEditText.getText().toString();
                Date date = new Date();
                popupWindow.dismiss();
                Log.i("WWW", "name: " + name + " priority: " + priority + "date: " + date.toString());
            }
        });

    }

    private int getPriority() {

        //get the priority from priorityRadioGroup
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
