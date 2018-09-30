package com.example.abdelazim.code_05_notifymetodolist.todo_done;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoDoneAdapter extends RecyclerView.Adapter<TodoDoneAdapter.ViewHolder> implements AdapterView.OnItemClickListener {

    private String DATE_FORMAT = "dd/MM/yy";

    private List<Todo> todoList;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private final ListItemClickListener mListItemClickListener;

    public interface ListItemClickListener {
        void onListItemClickListener(int index);
    }

    public TodoDoneAdapter(Context context, ListItemClickListener listItemClickListener) {
        mContext = context;
        mListItemClickListener = listItemClickListener;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    @NonNull
    @Override
    public TodoDoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoDoneAdapter.ViewHolder holder, int position) {
        holder.titleTextView.setText(todoList.get(position).getTitle());
        holder.dateTextView.setText(simpleDateFormat.format(todoList.get(position).getDate()));
        holder.priorityTextView.setText(String.valueOf(todoList.get(position).getPriority()));
        GradientDrawable priorityBackground = (GradientDrawable) holder.priorityTextView.getBackground();
        priorityBackground.setColor(getPriorityColor(todoList.get(position).getPriority()));
    }

    @Override
    public int getItemCount() {
        if (todoList == null)
            return 0;
        return todoList.size();
    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTextView, dateTextView, priorityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.todo_item_title_textView);
            dateTextView = itemView.findViewById(R.id.todo_item_date_textView);
            priorityTextView = itemView.findViewById(R.id.todo_item_priority_textView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListItemClickListener.onListItemClickListener(getAdapterPosition());
        }
    }
}