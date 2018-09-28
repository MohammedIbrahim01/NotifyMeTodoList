package com.example.abdelazim.code_05_notifymetodolist.todo.display_todos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.abdelazim.code_05_notifymetodolist.R;
import com.example.abdelazim.code_05_notifymetodolist.done.DoneActivity;
import com.example.abdelazim.code_05_notifymetodolist.done.DoneFragment;

public class TodoActivity extends AppCompatActivity {

    // DrawerLayout
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    // Toolbar
    private Toolbar toolbar;
    // FragmentManager
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        fragmentManager = getSupportFragmentManager();

        initializeViews();
    }


    /**
     * initialize views
     */
    private void initializeViews() {

        // DrawerLayout views
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        // Toolbar view
        toolbar = findViewById(R.id.toolbar);

        // setup ActionBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // handling click on navigation items
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // mark menu item as checked and close drawers
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_todo:
                        // switch to TodoFragment
                        fragmentManager.beginTransaction()
                                .add(R.id.fragment_container, new TodoFragment())
                                .commit();
                        return true;
                    case R.id.nav_done:
                        // switch to DoneFragment
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, new DoneFragment())
                                .commit();
                        return true;
                    case R.id.nav_settings:
                        // switch to SettingsFragment
                        return true;
                }
                return false;
            }
        });
    }


    /**
     * handling click on menu icon
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
