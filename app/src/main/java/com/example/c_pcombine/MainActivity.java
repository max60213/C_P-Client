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
    private EditText textField;
    private Button button;
    private String message;
    private EditText editIp;
    private String ipAddress;

    // Clipboard
    TextInputLayout tilData;
    TextView txtResult;
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

        tilData = findViewById(R.id.tilData);
        txtResult = findViewById(R.id.txtResult);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        //---------------------- Client -----------------------

        // reference to the text field
        textField = (EditText) findViewById(R.id.ediText1);

        // reference to the send button
        button = (Button) findViewById(R.id.button1);

        // Button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // clipboard
                String data = tilData.getEditText().getText().toString();

                if(!TextUtils.isEmpty(data)){
                    ClipData clip = ClipData.newPlainText(CLIPBOARD_LABEL, data);
                    clipboardManager.setPrimaryClip(clip);

                    for(int i = 0; i < clipboardManager.getPrimaryClip().getItemCount(); i++){
                        txtResult.setText(clipboardManager.getPrimaryClip().getItemAt(i).getText() + "\n");
                    }

                    // Show copied text
//                    txtResult.setText(clipboardManager.getPrimaryClip().getItemAt(i).getText() + "\n");

                } else {
                    Toast.makeText(MainActivity.this, "Filed is empty", Toast.LENGTH_LONG).show();
                }

                // Send message
                // get the text message on the text field
                message = textField.getText().toString();
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

            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textField.setText("");
                }
            });
        }
    }
}