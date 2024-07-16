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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NewCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_category);
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
        }, 0);

        MyCategoryBroadCastReceiver myCategoryBroadCastReceiver = new MyCategoryBroadCastReceiver();
        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myCategoryBroadCastReceiver, new IntentFilter(SMSKeys.SMS_FILTER), RECEIVER_EXPORTED);
    }

    public void onSaveCategoryButtonClick(View view)
    {
        String categoryName;
        int eventCount;
        boolean isActive;

        EditText etCategoryID = findViewById(R.id.etCategoryID);
        EditText etCategoryName = findViewById(R.id.etCategoryName);
        EditText etEventCount = findViewById(R.id.etEventCount);
        Switch switchIsActive = findViewById(R.id.switchIsActive);


        String errorMessage = "Invalid Value!";

        try {
            categoryName = etCategoryName.getText().toString();
            eventCount = Integer.parseInt(etEventCount.getText().toString());
            isActive = switchIsActive.isChecked();

            if ( !(categoryName.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9 ]+$")))
            {
                errorMessage = "Invalid Category Name!";
                throw new Exception();
            } else if (!(eventCount > 0))
            {
                errorMessage = "Invalid Event Count!";
                throw new Exception();
            }

            Category category = new Category(categoryName, eventCount, isActive);

            saveDataToSharedPreferences(category);

            etCategoryID.setText(category.getCATEGORY_ID());

            Toast.makeText(this, "Category Saved Successfully: " + category.getCATEGORY_ID(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }

    }

    private void saveDataToSharedPreferences(Category category)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SpKeys.SP_FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String catagoryDBString = sharedPreferences.getString(SpKeys.SP_CATEGORY_DB, "[]");
        Type type = new TypeToken<ArrayList<Category>>() {}.getType();
        ArrayList<Category> categoryDB = gson.fromJson(catagoryDBString,type);
        categoryDB.add(category);

        String categoryDBString1 = gson.toJson(categoryDB);

        editor.putString(SpKeys.SP_CATEGORY_DB, categoryDBString1);

        editor.apply();
        editor.commit();
    }

    class MyCategoryBroadCastReceiver extends BroadcastReceiver {
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

                if (!categoryTokenizer.nextToken().equals("category"))
                {
                    throw new Exception();
                }

                String categoryName = categoryTokenizer.nextToken();
                int eventCount = Integer.parseInt(sT.nextToken());
                String bool = sT.nextToken();
                boolean isActive = false;
                if(bool.toLowerCase().equals("true"))
                {
                    isActive = true;
                }
                else if(bool.toLowerCase().equals("false"))
                {
                    isActive = false;
                }else
                {
                    throw new Exception();
                }

                EditText etCategoryName = findViewById(R.id.etCategoryName);
                EditText etEventCount = findViewById(R.id.etEventCount);
                Switch switchIsActive = findViewById(R.id.switchIsActive);

                etCategoryName.setText(categoryName);
                etEventCount.setText(eventCount);
                switchIsActive.setChecked(isActive);

            } catch (Exception e) {
                Toast.makeText(context,"Invalid Message! Please try again!", Toast.LENGTH_LONG).show();
            }


        }
    }



}