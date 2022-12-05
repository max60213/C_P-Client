package com.example.c_pcombine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
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

        //--------------------- Clipboard ----------------------

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        //---------------------- Client -----------------------
        // reference to the send button
        button = findViewById(R.id.button1);

        // Button press event listener
        button.setOnClickListener(v -> {
            String message;
            String ipAddress;

            // get the message from clipboard
            message = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            ipAddress = editIp.getText().toString();

            // start the Thread to connect to server
            if (Objects.equals(message, "")){
                message = "Am I a joke to you?";
            }
            new Thread(new ClientThread(message, ipAddress)).start();
        });
    }

    // the ClientThread class performs
    // the networking operations
    static class ClientThread implements Runnable {
        final String message;
        Socket client;
        PrintWriter printwriter;
        String ipAddress;

        ClientThread(String message, String ipAddress) {
            this.message = message;
            this.ipAddress = ipAddress;
        }
        @Override
        public void run() {
            try {
                // the IP and port should be correct to have a connection established
                // Creates a stream socket and connects it to the specified port number on the named host.
                client = new Socket(ipAddress, 8964); // connect to server
                printwriter = new PrintWriter(client.getOutputStream(),true);
                printwriter.write(message); // write the message to output stream

                printwriter.flush();
                printwriter.close();

                // closing the connection
                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}