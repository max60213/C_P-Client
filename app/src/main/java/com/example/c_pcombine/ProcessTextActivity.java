package com.example.c_pcombine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class ProcessTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_text2);
        String ipAddress = "";

//        SharedPreferences sharedPref = null;
//        String ipAddress = sharedPref.getString("IP", "0.0.0.0");

        //get selected text
        String text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ipAddress, Toast.LENGTH_SHORT).show();
        new Thread(new ClientThread(text, ipAddress)).start();
    }
}