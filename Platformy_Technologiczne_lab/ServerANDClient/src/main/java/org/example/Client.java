package org.example;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connectedToServer = true;

    public Client() {
        try {
            client = new Socket("localhost", 7770);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Connected to server");
            new Thread(new InputHandler()).start();
            String message;
            while(connectedToServer){
                if((message = in.readLine()) != null){
                    System.out.println(message);
                }
            }
        } catch (IOException ignored) {
        }
    }

    public void disconnect(){
        System.out.println("Disconnecting from server...");
        connectedToServer = false;
        try{
            client.close();
            out.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    class InputHandler implements Runnable{

        @Override
        public void run() {
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String message;
            try {
                while(!(message = console.readLine()).equalsIgnoreCase("quit")){
                    out.println(message);
                }
                console.close();
                disconnect();
            } catch (IOException e) {
                System.out.println("erorr");
            }
        }
    }

    public static void main(String[] args) {
        new Client();
    }

}
