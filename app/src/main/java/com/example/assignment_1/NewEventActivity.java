package com.example.assignment_1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
        }, 0);

        NewEventActivity.MyEventBroadCastReceiver myEventBroadCastReceiver = new NewEventActivity.MyEventBroadCastReceiver();
        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myEventBroadCastReceiver, new IntentFilter(SMSKeys.SMS_FILTER), RECEIVER_EXPORTED);
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

    class MyEventBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Tokenize received message here
            String msg = intent.getStringExtra(SMSKeys.SMS_MSG_KEY);
            myStringTokenizer(msg, context);
        }

        public void myStringTokenizer(String msg, Context context)
        {
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */
            try {
                StringTokenizer sT = new StringTokenizer(msg, ";");
                String firstToken = sT.nextToken();
                StringTokenizer categoryTokenizer = new StringTokenizer(firstToken, ":");

                if (!categoryTokenizer.nextToken().equals("event"))
                {
                    throw new Exception();
                }

                String eventName = categoryTokenizer.nextToken();
                String categoryID = sT.nextToken();
                String stringIsActive = sT.nextToken();
                int ticketsAvailable = Integer.parseInt(sT.nextToken());
                boolean isActive = false;
                if(stringIsActive.toLowerCase().equals("true"))
                {
                    isActive = true;
                }
                else if(stringIsActive.toLowerCase().equals("false"))
                {
                    isActive = false;
                }else
                {
                    throw new Exception();
                }

                EditText etEventName = findViewById(R.id.etEventName);
                EditText etCategoryID = findViewById(R.id.etEventCategoryID);
                EditText etTicketsAvailable = findViewById(R.id.etTicketsAvailable);
                Switch switchIsActive = findViewById(R.id.switchEventIsActive);

                etEventName.setText(eventName);
                etCategoryID.setText(categoryID);
                etTicketsAvailable.setText(ticketsAvailable);
                switchIsActive.setChecked(isActive);

            } catch (Exception e) {
                Toast.makeText(context,"Invalid Message! Please try again!", Toast.LENGTH_LONG).show();
            }

        }
    }

}