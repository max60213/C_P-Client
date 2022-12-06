package com.example.c_pcombine;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {

    final String message;
    Socket client;
    PrintWriter printwriter;
    String ipAddress;

    ClientThread(String message, String ipAddress) {
        this.message = message;
        this.ipAddress = ipAddress;
    }

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
