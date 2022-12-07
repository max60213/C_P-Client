package com.example.c_pcombine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button;
        Button button2;
        EditText editIp;

        //-------------------- IP EditText -----------------

        editIp = findViewById(R.id.editIP);

        //---------------------- Client -----------------------
        //Reference to the send button
        button = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        //Button press event listener
        button.setOnClickListener(v -> {

            String ipAddress;
            ipAddress = editIp.getText().toString();

            //Store data using SharedPreferences
            SharedPreferences sharedPref;
            sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
            sharedPref.edit().putString("IP", ipAddress).apply();

            Toast.makeText(MainActivity.this, "Done, you can close me now", Toast.LENGTH_SHORT).show();

        });

        //Send Manually button
        button2.setOnClickListener(u ->{
            //Clipboard
            ClipboardManager clipboardManager;
            String text;
            clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            //Get IP from SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
            String ipAddress = sharedPref.getString("IP", "0.0.0.0");

            //Get text from clipboard
            text = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();

            new Thread(new ClientThread(text, ipAddress)).start();
        });
    }
}