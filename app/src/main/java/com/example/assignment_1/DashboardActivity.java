package com.example.assignment_1;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private FragmentListCategory fragmentListCategory = new FragmentListCategory();

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        MyNavigationListener myNavigationListener = new MyNavigationListener();
        navigationView.setNavigationItemSelectedListener(myNavigationListener);

        Toolbar myToolBar = findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(myToolBar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, myToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(
                R.id.dashboard_host_container, fragmentListCategory).commit();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonEvent);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSaveEventClick())
                {
                    Snackbar.make(view,"Event Has Been Added!, Click Undo To Cancel Event Addition!", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SharedPreferences sharedPreferences = getSharedPreferences(SpKeys.SP_FILE_NAME,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    Gson gson = new Gson();
                                    String eventDBString = sharedPreferences.getString(SpKeys.SP_EVENT_DB, "[]");
                                    Type type = new TypeToken<ArrayList<Event>>() {}.getType();
                                    ArrayList<Event> eventDB = gson.fromJson(eventDBString,type);
                                    eventDB.remove(eventDB.size() - 1);

                                    eventDBString = gson.toJson(eventDB);

                                    editor.putString(SpKeys.SP_EVENT_DB, eventDBString);

                                    editor.apply();
                                }
                            }).show();
                }

            }
        });
    }

    public boolean onSaveEventClick()
    {
        String eventName;
        String categoryID;
        int ticketsAvailable;
        boolean isActive;

        EditText etEventID = findViewById(R.id.etEventID);
        EditText etEventName = findViewById(R.id.etEventName);
        EditText etCategoryID = findViewById(R.id.etEventCategoryID);
        EditText etTicketsAvailable = findViewById(R.id.etTicketsAvailable);
        Switch switchIsActive = findViewById(R.id.switchEventIsActive);

        String errorMessage = "Invalid Values!";

        try {

            eventName = etEventName.getText().toString();
            categoryID = etCategoryID.getText().toString();
            ticketsAvailable = Integer.parseInt(etTicketsAvailable.getText().toString());
            isActive = switchIsActive.isChecked();

            SharedPreferences sp = getSharedPreferences(SpKeys.SP_FILE_NAME, Context.MODE_PRIVATE);
            String categoryDBString = sp.getString(SpKeys.SP_CATEGORY_DB,"[]");

            Type type = new TypeToken<ArrayList<Category>>() {}.getType();
            Gson gson = new Gson();
            ArrayList<Category> categoryDB = gson.fromJson(categoryDBString,type);

            boolean categoryIDNotValid = true;

            for (Category category : categoryDB)
            {
                if (category.getCATEGORY_ID().equals(categoryID))
                {
                    categoryIDNotValid = false;
                    category.setEventCount(category.getEventCount() + 1);
                    break;
                }
            }

            SharedPreferences.Editor editor = sp.edit();
            categoryDBString = gson.toJson(categoryDB);
            editor.putString(SpKeys.SP_CATEGORY_DB, categoryDBString);
            editor.apply();

            fragmentListCategory.LoadAndSetDataFromSharedPreferences();

            if (categoryIDNotValid)
            {
                errorMessage = "CategoryID does not exist!";
                throw new Exception();
            } else if (!eventName.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 ]+$"))
            {
                errorMessage = "Invalid Event Id!";
                throw new Exception();
            } else if (ticketsAvailable < 0)
            {
                errorMessage = "Tickets Available Cannot Be A Negative Number!";
                throw new Exception();
            }

            Event event = new Event(eventName, categoryID, ticketsAvailable, isActive);

            saveDataToSharedPreferences(event);

            etEventID.setText(event.getEVENT_ID());

            //Toast.makeText(this, "Event Saved: "+event.getEVENT_ID()+" to "+event.getCategoryID(),Toast.LENGTH_LONG).show();

            return true;
        } catch (Exception e) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void saveDataToSharedPreferences(Event event)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SpKeys.SP_FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String eventDBString = sharedPreferences.getString(SpKeys.SP_EVENT_DB, "[]");
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        ArrayList<Event> eventDB = gson.fromJson(eventDBString,type);
        eventDB.add(event);

        eventDBString = gson.toJson(eventDB);

        editor.putString(SpKeys.SP_EVENT_DB, eventDBString);

        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.refresh)
        {
            fragmentListCategory.LoadAndSetDataFromSharedPreferences();
        }else if (id == R.id.clear_event_form)
        {
            EditText etEventID = findViewById(R.id.etEventID);
            EditText etEventName = findViewById(R.id.etEventName);
            EditText etCategoryID = findViewById(R.id.etEventCategoryID);
            EditText etTicketsAvailable = findViewById(R.id.etTicketsAvailable);
            Switch switchIsActive = findViewById(R.id.switchEventIsActive);

            etEventID.setText("");
            etEventName.setText("");
            etCategoryID.setText("");
            etTicketsAvailable.setText("");
            switchIsActive.setChecked(false);
        } else if (id == R.id.delete_all_categories)
        {
            SharedPreferences sP = getSharedPreferences(SpKeys.SP_FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sP.edit();
            editor.putString(SpKeys.SP_CATEGORY_DB, "[]");
            editor.apply();
            fragmentListCategory.LoadAndSetDataFromSharedPreferences();
        } else if (id == R.id.delete_all_events)
        {
            SharedPreferences sP = getSharedPreferences(SpKeys.SP_FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sP.edit();
            editor.putString(SpKeys.SP_EVENT_DB, "[]");
            editor.apply();
        }
        return true;
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.view_all_categories) {
                Intent intent = new Intent(DashboardActivity.this, ListCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.view_all_events) {
                Intent intent = new Intent(DashboardActivity.this, ListEventActivity.class);
                startActivity(intent);
            } else if (id == R.id.add_categories) {
                Intent intent = new Intent(DashboardActivity.this, NewCategoryActivity.class);
                startActivity(intent);
            } else if (id == R.id.logout) {
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

}