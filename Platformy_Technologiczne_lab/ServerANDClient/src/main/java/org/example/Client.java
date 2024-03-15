package org.example;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket client;
    private BufferedWriter out;
    private BufferedReader in;
    private boolean connectedToServer = true;

    public Client() {
        try {
            client = new Socket("localhost", 7770);
            out = new BufferedWriter(new PrintWriter(client.getOutputStream(), true));
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Connected to server");
            new Thread(new InputHandler()).start();
            String message;
            while(connectedToServer){
                if((message = in.readLine()) != null){
                    System.out.println(message);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class InputHandler implements Runnable{

        @Override
        public void run() {
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;
            try {
                while((message = console.readLine()).equalsIgnoreCase("quit")){
                    out.write(message);
                }
                console.close();
                connectedToServer = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Main {
        public static void main(String[] args) {
            new Client();
        }
    }
}
