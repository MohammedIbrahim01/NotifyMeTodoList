package com.example.abdelazim.code_05_notifymetodolist.todo;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdelazim.code_05_notifymetodolist.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private String DATE_FORMAT = "dd/MM/yy";

    private List<Todo> todoList;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public TodoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        holder.titleTextView.setText(todoList.get(position).getTitle());
        holder.dateTextView.setText(simpleDateFormat.format(todoList.get(position).getDate()));
        holder.priorityTextView.setText(String.valueOf(todoList.get(position).getPriority()));
        GradientDrawable priorityBackground = (GradientDrawable) holder.priorityTextView.getBackground();
        priorityBackground.setColor(getPriorityColor(todoList.get(position).getPriority()));
    }

    @Override
    public int getItemCount() {
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView, dateTextView, priorityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.todo_item_title_textView);
            dateTextView = itemView.findViewById(R.id.todo_item_date_textView);
            priorityTextView = itemView.findViewById(R.id.todo_item_priority_textView);
        }
    }
}