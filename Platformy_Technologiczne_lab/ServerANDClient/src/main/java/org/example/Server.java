package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    static final Integer PORT = 7770;
    private ServerSocket server = null;
    private boolean serverIsRunning = true;

    public Server() {
        System.out.println("Server started...");
        try{
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server error");
        }

        while(serverIsRunning){
            try{
                Socket client = server.accept();
                System.out.println("New client connected");
                ConnectionHandler handler = new ConnectionHandler(client);
                Thread thread = new Thread(handler);
                thread.start();
            }catch (IOException e){
                // TODO: handle
            }
        }
    }

    public void stopServer(){
        serverIsRunning = false;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 class ConnectionHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

     public ConnectionHandler(Socket client) {
         this.client = client;
     }

     @Override
    public void run() {
         try {
             out = new PrintWriter(client.getOutputStream(), true);
             in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             out.print("Enter username: ");
             username = in.readLine();
             System.out.println(username + " connected!");
             String message;
             while((message = in.readLine()) != null){
                 if (message.equalsIgnoreCase("quit")){
                        break;
                 }
                 System.out.println(username + " says: " + message);
                 out.println("You said: " + message);
             }
             out.println(username + "disconnected");
         } catch (IOException e) {
             throw new RuntimeException(e);
         }

    }




    static class Main {
        public static void main(String[] args) {
            new Server();
        }
    }
}