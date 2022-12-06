package com.example.c_pcombine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button;
        EditText editIp;
        ClipboardManager clipboardManager;

        //-------------------- IP EditText -----------------

        editIp = findViewById(R.id.editIP);
            //store data using SharedPreferences
            SharedPreferences sharedPref;
            sharedPref = getSharedPreferences("myPref", MODE_PRIVATE);

        //--------------------- Clipboard ----------------------

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        //---------------------- Client -----------------------
        // reference to the send button
        button = findViewById(R.id.button1);

        // Button press event listener
        button.setOnClickListener(v -> {

            String ipAddress;
            String message;
            ipAddress = editIp.getText().toString();

            //store IP to myPref
            sharedPref.edit().clear();
            sharedPref.edit().putString("IP", ipAddress).commit();

            // get the message from clipboard
            //message = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            //Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

            // start the Thread to connect to server
//            if (Objects.equals(message, "")){
//                message = "Am I a joke to you?";
//            }

            //new Thread(new ClientThread(message, ipAddress)).start();
        });
    }
}