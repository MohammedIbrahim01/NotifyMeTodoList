package com.example.abdelazim.code_05_notifymetodolist.todo.edit_todo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.todo.display_todos.TodoActivity;
import com.example.abdelazim.code_05_notifymetodolist.utils.AppExecutors;

import java.util.Date;

public class TodoDetailsActivity extends AppCompatActivity {

    // priority constants
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    private int todoId;

    private EditText titleEditText;
    private RadioGroup priorityRadioGroup;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        Intent intent = getIntent();

        initializeViews();

        todoId = intent.getIntExtra(TodoActivity.KEY_TODO_ID, 0);

        TodoDetailsViewModelFactory viewModelFactory = new TodoDetailsViewModelFactory(AppDatabase.getInstance(getApplicationContext()), todoId);

        TodoDetailsViewModel viewModel = ViewModelProviders.of(TodoDetailsActivity.this, viewModelFactory).get(TodoDetailsViewModel.class);

        viewModel.getTodo().observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {

                titleEditText.setText(todo.getTitle());
                setPriority(todo.getPriority());
            }
        });
    }



    private void initializeViews() {

        titleEditText = findViewById(R.id.title_editText);
        priorityRadioGroup = findViewById(R.id.priority_radioGroup);
        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String editedTitle = titleEditText.getText().toString();
                int editPriority = getPriority();

                // create new Todoo with the same id so it will be updated
                final Todo editedTodo = new Todo(todoId, editedTitle, editPriority,new Date());

                AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        AppDatabase.getInstance(getApplicationContext()).todoDao().updateTodo(editedTodo);
                    }
                });

                finish();
            }
        });
    }

    private void setPriority(int priority) {

        switch (priority) {
            case 1:
                priorityRadioGroup.check(R.id.high_rad);
                break;
            case 2:
                priorityRadioGroup.check(R.id.medium_rad);
                break;
            case 3:
                priorityRadioGroup.check(R.id.low_rad);
                break;
        }
    }

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
