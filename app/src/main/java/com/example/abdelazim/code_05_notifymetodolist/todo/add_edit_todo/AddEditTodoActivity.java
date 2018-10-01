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

    // Priority constants
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    // intent keys and actions
    public static final String KEY_TODO_ID = "key-todo-id";
    private static final String ACTION_ADD = "action-add";
    private static final String ACTION_EDIT = "action-edit";
    // View declarations
    private EditText titleEditText;
    private RadioGroup priorityRadioGroup;
    private Button addSaveButton;
    private LinearLayout notificationInfoLinearLayout;
    private TextView notificationTimeTextView;
    private Button setTimeButton;
    // TimePicker
    private TimePickerDialog timePickerDialog;
    private Calendar notificationCalendar;
    // To hold current state  Edit / Add
    private String mAction;
    // To hold the edited task id
    private int todoId;
    // Database
    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_todo);

        // Get database Instance
        database = AppDatabase.getInstance(getApplicationContext());

        // Get the current state Edit / Add
        Intent intent = getIntent();
        mAction = intent.getAction();

        initializeViews();

        if (mAction.equals(ACTION_EDIT)) {

            todoId = intent.getIntExtra(KEY_TODO_ID, 0);

            setupViewModel();
        }

        setButtonText();
    }


    private void setupViewModel() {

        EditTodoViewModelFactory factory = new EditTodoViewModelFactory(database, todoId);
        final EditTodoViewModel viewModel = ViewModelProviders.of(this, factory).get(EditTodoViewModel.class);

        /**
         *  Get the data cached in the viewModel
         *
         *  so we don't need to re-query the database
         */
        viewModel.getTodo().observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {

                // Remove the observer , we don't need to observe the changes in the database
                viewModel.getTodo().removeObserver(this);

                // fill the task info in the views
                populateUI(todo);
            }
        });
    }


    /**
     * Fill the views with the task info
     *
     * @param todo
     */
    private void populateUI(Todo todo) {

        titleEditText.setText(todo.getTitle());
        setPriority(todo.getPriority());
    }


    /**
     * Check the appropriate radioButton
     *
     * @param priority
     */
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


    /**
     * Views initialization
     */
    private void initializeViews() {

        titleEditText = findViewById(R.id.title_editText);
        priorityRadioGroup = findViewById(R.id.priority_radioGroup);
        addSaveButton = findViewById(R.id.add_save_button);
        notificationInfoLinearLayout = findViewById(R.id.notification_info_LinearLayout);
        notificationTimeTextView = findViewById(R.id.notification_time_textView);
        setTimeButton = findViewById(R.id.set_time_button);

        hideNotificationInfo();

        addSaveButton.setOnClickListener(this);
        setTimeButton.setOnClickListener(this);
    }


    /**
     * Initialize timePicker and launch it
     */
    private void launchTimePicker() {

        // initialize
        Date date = new Date();
        timePickerDialog = new TimePickerDialog(this, this, date.getHours(), date.getMinutes(), false);
        timePickerDialog.show();
    }


    /**
     * Set the notification time to the textView
     *
     * Show Notification info layout
     */
    private void showNotificationInfo() {

        notificationTimeTextView.setText(getFormattedTime(notificationCalendar));
        notificationInfoLinearLayout.setVisibility(View.VISIBLE);
    }


    /**
     * Hide layout that contains the notification info (time)
     */
    private void hideNotificationInfo() {

        notificationInfoLinearLayout.setVisibility(View.GONE);
    }


    /**
     * Set the appropriate text to the addSaveButton
     */
    private void setButtonText() {

        // Check the current state Add / Edit
        if (mAction.equals(ACTION_EDIT)) {

            addSaveButton.setText("Edit");
        } else if (mAction.equals(ACTION_ADD)) {

            addSaveButton.setText("Add");
        }
    }


    /**
     * Save a new Task in the database and schedule it
     */
    private void saveNewTask() {

        // get the values from views
        String title = titleEditText.getText().toString();
        int priority = getPriority();
        // Create new Task instance with the previous values
        final Todo newTodo = new Todo(title, priority, new Date(), notificationCalendar);
        // Schedule this new Task
        NotificationScheduler.schedule(this, newTodo);

        // Within the appropriate Thread insert this new Task
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                database.todoDao().insertTodo(newTodo);
            }
        });
    }


    /**
     * Save the edited task in the database
     */
    private void saveEditedTask() {

        // Get the edited values from views
        String title = titleEditText.getText().toString();
        int priority = getPriority();
        // Make a new Task with same taskId and the edited values
        final Todo editedTodo = new Todo(todoId, title, priority, new Date(), notificationCalendar);

        // Within the appropriate thread update this Task in the database
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                database.todoDao().updateTodo(editedTodo);
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


    /**
     * format time
     *
     * @param calendar
     * @return formatted time
     */
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


    /**
     * Called after click ok button in PickTimeDialog
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        // Get instance of Calendar ad set its time to system currentTimeMillis
        notificationCalendar = Calendar.getInstance();
        notificationCalendar.clear();
        notificationCalendar.setTimeInMillis(System.currentTimeMillis());
        // Set calendar hour and minute to the ones returned by TimePickerDialog
        notificationCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        notificationCalendar.set(Calendar.MINUTE, minute);
        // Show Notification time info
        showNotificationInfo();
    }


    /**
     * Handling clicks
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.set_time_button:
                launchTimePicker();
                break;

            case R.id.add_save_button:

                // Check the current state Edit / Add
                if (mAction.equals(ACTION_EDIT)) {

                    saveEditedTask();

                } else if (mAction.equals(ACTION_ADD)) {

                    saveNewTask();
                }

                // Finish this activity
                finish();

                break;
        }
    }
}
