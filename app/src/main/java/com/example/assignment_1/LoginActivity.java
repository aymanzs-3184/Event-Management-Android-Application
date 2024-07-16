package com.example.assignment_1;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        retrieveUsername();
    }

    @Override
    protected void onStart() {
        super.onStart();
        retrieveUsername();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        retrieveUsername();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveUsername();
    }

    private void retrieveUsername()
    {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(SpKeys.SP_FILE_NAME, MODE_PRIVATE);

            String userString = sharedPreferences.getString(SpKeys.SP_USER,"" );

            Type type = new TypeToken<User>() {}.getType();
            Gson gson = new Gson();
            User user = gson.fromJson(userString,type);
            String username = user.getUserName();

            EditText loginUsername = findViewById(R.id.loginUsername);

            loginUsername.setText(username);
        } catch (Exception e) {
            Toast.makeText(this, "You have not registered!, Kindly Register and Come back!", Toast.LENGTH_LONG).show();
        }

    }

    public void onRegisterButtonClick(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClick(View view)
    {
        String username, enteredUsername;
        String password, enteredPassword;

        EditText loginUsername = findViewById(R.id.loginUsername);
        EditText loginPassword = findViewById(R.id.loginPassword);

        enteredUsername = loginUsername.getText().toString();
        enteredPassword = loginPassword.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(SpKeys.SP_FILE_NAME, MODE_PRIVATE);

        String userString = sharedPreferences.getString(SpKeys.SP_USER,"" );

        Type type = new TypeToken<User>() {}.getType();
        Gson gson = new Gson();
        User user = gson.fromJson(userString,type);

        username = user.getUserName();
        password = user.getPassword();

        if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
            Toast.makeText(this,"Login Successful, Welcome to the Dashboard!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Authentication Failure: Username or Password incorrect",Toast.LENGTH_SHORT).show();
        }

    }

}