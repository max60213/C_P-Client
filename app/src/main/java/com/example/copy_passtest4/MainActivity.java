package com.example.copy_passtest4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference to the text field
        textField = (EditText) findViewById(R.id.editText1);

        // reference to the send button
        button = (Button) findViewById(R.id.button1);

        // Button press event listener
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // get the text message on the text field
                message = textField.getText().toString();

                // start the Thread to connect to server
                new Thread(new ClientThread(message)).start();

            }
        });
    }

    // the ClientThread class performs
    // the networking operations
    class ClientThread implements Runnable {
        private final String message;

        ClientThread(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            try {
                // the IP and port should be correct to have a connection established
                // Creates a stream socket and connects it to the specified port number on the named host.
                client = new Socket("192.168.137.1", 4444); // connect to server
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
