package com.example.abdelazim.code_05_notifymetodolist.todo.add_edit_todo;

import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.database.AppDatabase;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.utils.AppExecutors;
import com.example.abdelazim.code_05_notifymetodolist.utils.NotificationScheduler;

import java.util.Calendar;
import java.util.Date;

public class AddEditTodoActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    // intent keys and actions
    public static final String KEY_TODO_ID = "key-todo-id";
    private static final String ACTION_ADD = "action-add";
    private static final String ACTION_EDIT = "action-edit";
    // priority constants
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;

    private String mAction;
    private Todo todoToEdit;
    private int todoId;

    private EditText titleEditText;
    private RadioGroup priorityRadioGroup;
    private Button addSaveButton;
    private LinearLayout notificationInfoLinearLayout;
    private TextView notificationTimeTextView;
    private Button setTimeButton;

    private TimePickerDialog timePickerDialog;
    private Calendar notificationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_todo);

        Intent intent = getIntent();
        mAction = intent.getAction();

        initializeViews();

        if (mAction.equals(ACTION_EDIT)) {

            todoId = intent.getIntExtra(KEY_TODO_ID, 0);

            setupViewModel(todoId);
        }
        setButtonText();
    }


    private void setupViewModel(int todoId) {

        AddEditTodoViewModelFactory factory = new AddEditTodoViewModelFactory(AppDatabase.getInstance(getApplicationContext()), todoId);
        final AddEditTodoViewModel viewModel = ViewModelProviders.of(this, factory).get(AddEditTodoViewModel.class);
        viewModel.getTodo().observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {

                viewModel.getTodo().removeObserver(this);
                populateUI(todo);
            }
        });
    }

    private void populateUI(Todo todo) {

        titleEditText.setText(todo.getTitle());
        setPriority(todo.getPriority());
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


    private void initializeViews() {

        titleEditText = findViewById(R.id.title_editText);
        priorityRadioGroup = findViewById(R.id.priority_radioGroup);
        addSaveButton = findViewById(R.id.add_save_button);
        notificationInfoLinearLayout = findViewById(R.id.notification_info_LinearLayout);
        notificationTimeTextView = findViewById(R.id.notification_time_textView);
        setTimeButton = findViewById(R.id.set_time_button);

        Date date = new Date();
        timePickerDialog = new TimePickerDialog(this, this, date.getHours(), date.getMinutes(), false);

        hideNotificationInfo();

        addSaveButton.setOnClickListener(this);
        setTimeButton.setOnClickListener(this);
    }

    private void hideNotificationInfo() {
        notificationInfoLinearLayout.setVisibility(View.GONE);
    }


    private void setButtonText() {

        if (mAction.equals(ACTION_EDIT)) {

            addSaveButton.setText("Save");
        } else if (mAction.equals(ACTION_ADD)) {

            addSaveButton.setText("Add");
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.set_time_button:

                timePickerDialog.show();

                break;

            case R.id.add_save_button:

                if (mAction.equals(ACTION_EDIT)) {

                    String title = titleEditText.getText().toString();
                    int priority = getPriority();
                    final Todo editedTodo = new Todo(todoId, title, priority, new Date(), notificationTime);

                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            AppDatabase.getInstance(getApplicationContext()).todoDao().updateTodo(editedTodo);
                        }
                    });
                } else if (mAction.equals(ACTION_ADD)) {

                    String title = titleEditText.getText().toString();
                    int priority = getPriority();
                    final Todo newTodo = new Todo(title, priority, new Date(), notificationTime);
                    NotificationScheduler.schedule(this, newTodo);

                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            AppDatabase.getInstance(getApplicationContext()).todoDao().insertTodo(newTodo);
                        }
                    });
                }

                finish();

                break;
        }
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


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        notificationTime = Calendar.getInstance();
        notificationTime.clear();
        notificationTime.setTimeInMillis(System.currentTimeMillis());
        notificationTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        notificationTime.set(Calendar.MINUTE, minute);
        showNotificationInfo();
    }


    private void showNotificationInfo() {

        notificationTimeTextView.setText(getFormattedTime(notificationTime));
        notificationInfoLinearLayout.setVisibility(View.VISIBLE);
    }


    private String getFormattedTime(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String AM_PM = "am";

        if (hour > 11) {
            AM_PM = "pm";

            if (hour > 12) {
                hour -= 12;
            }
        }

        String formattedTime = hour + " : " + minute + " " + AM_PM;

        return formattedTime;

    }
}
