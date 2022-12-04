package com.example.c_pcombine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    // declaring required variables
    private Socket client;
    private PrintWriter printwriter;
    private Button button;
    private String message;
    private EditText editIp;
    private String ipAddress;

    // Clipboard
    private final String CLIPBOARD_LABEL = "CLIPBOARD_LABEL";
    private ClipboardManager clipboardManager;
    StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------------------- IP EditText -----------------

        editIp = findViewById(R.id.editIP);

        //--------------------- Clipboard ----------------------


        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        //---------------------- Client -----------------------


        // reference to the send button
        button = (Button) findViewById(R.id.button1);

        // Button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Send message
                // get the text message on the text field
                //message = textField.getText().toString();

                // get the message from clipboard
                message = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                ipAddress = editIp.getText().toString();

                // start the Thread to connect to server
                new Thread(new ClientThread(message, ipAddress)).start();

            }
        });
    }

    // the ClientThread class performs
    // the networking operations
    class ClientThread implements Runnable {
        private final String message;

        ClientThread(String message, String ipAddress) {
            this.message = message;
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