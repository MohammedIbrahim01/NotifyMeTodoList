package com.example.abdelazim.code_05_notifymetodolist.done;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.todo.Todo;
import com.example.abdelazim.code_05_notifymetodolist.todo.display_todos.TodoAdapter;

import java.util.List;

public class DoneActivity extends AppCompatActivity implements TodoAdapter.ListItemClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);




    }



    @Override
    public void onListItemClickListener(int index) {

    }
}
