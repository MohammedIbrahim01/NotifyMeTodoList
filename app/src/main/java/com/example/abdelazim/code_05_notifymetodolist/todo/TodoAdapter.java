package com.example.abdelazim.code_05_notifymetodolist.todo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdelazim.code_05_notifymetodolist.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<Todo> todoList;

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
        holder.dateTextView.setText(todoList.get(position).getDate().toString());
        holder.priorityTextView.setText(todoList.get(position).getPriority());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
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