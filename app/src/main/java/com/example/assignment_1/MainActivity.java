package com.example.assignment_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegisterButtonClick(View view)
    {
        String username;
        String password;
        String passwordConfirm;

        EditText etUsername = findViewById(R.id.registerUsername);
        EditText etPassword = findViewById(R.id.registerPassword);
        EditText etPasswordCon = findViewById(R.id.registerPasswordCon);

        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        passwordConfirm = etPasswordCon.getText().toString();

        if (username.isEmpty()
                        && password.isEmpty()
                        && passwordConfirm.isEmpty()
        )
        {
            Toast.makeText(this, "Invalid username or passwords", Toast.LENGTH_LONG).show();
        }else if (!password.equals(passwordConfirm))
        {
            Toast.makeText(this, "Invalid username or passwords", Toast.LENGTH_LONG).show();
        }
        else
        {
            saveRegisterDetailsToSharedPreferences(username,password);
            Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_LONG).show();
        }
    }

    private void saveRegisterDetailsToSharedPreferences(String username, String password)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SpKeys.SP_FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        User user = new User(username, password);
        Gson gson = new Gson();
        String userString = gson.toJson(user);

        editor.putString(SpKeys.SP_USER, userString);

        editor.apply();
    }

    public void onLoginButtonClick(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}