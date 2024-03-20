package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Server {
    static final Integer PORT = 7770;
    private ServerSocket server = null;
    private boolean serverIsRunning = true;
    private List<ConnectionHandler> clients = new ArrayList<>();

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
                clients.add(handler);
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

    public void SendToAllClients(String message) {
        for (ConnectionHandler client : clients) {
            client.out.println(message);
        }
    }


 class ConnectionHandler implements Runnable{
    private Socket client;
    private BufferedReader in;
    public PrintWriter out;
    private String username;

     public ConnectionHandler(Socket client) {
         this.client = client;
     }

     @Override
    public void run() {
         try {
             out = new PrintWriter(client.getOutputStream(), true);
//             in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             ObjectInputStream in = null;
             try {
                 in = new ObjectInputStream(client.getInputStream());
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }


             Random rand = new Random();
             out.println("Enter username: ");
             Message client_message = (Message) in.readObject();
             username = client_message.getContent();
             System.out.println(username + " connected!");
             SendToAllClients(username + " connected!");
             String message;
             out.println("Type number of messages:");
             while((client_message = (Message) in.readObject()) != null){
                 if (client_message.getContent().equalsIgnoreCase("quit")){
                        break;
                 }
                 try{
                     Integer numberOfMessages = Integer.parseInt(client_message.getContent());
                     List<Message> messages = new ArrayList<>();

                    for (int i = 0; i < numberOfMessages; i++) {
                        out.println("Enter message: ");
                        client_message = (Message) in.readObject();
                        Message newMessage = client_message;
                        messages.add(newMessage);
                    }
                    for (Message m : messages) {
                        System.out.println("#" + m.getId()+ " " + username + " says: " + m.getContent());
                        SendToAllClients("#" + m.getId()+ " " + username + " says: " + m.getContent());
                    }
                    messages.clear();
                    out.println("Type number of messages:");
                 }
                 catch (NumberFormatException e){
                     out.println("Invalid input, try again!");
                 }
             }
             System.out.println(username + " disconnected");
             out.close();
             in.close();
             client.close();
             SendToAllClients(username + " disconnected");
             clients.remove(this);

         } catch (IOException e) {
             System.out.println(username + " disconnected");
             SendToAllClients(username + " disconnected");
             clients.remove(this);
         } catch (ClassNotFoundException e) {
             throw new RuntimeException(e);
         }

     }
}

    public static void main(String[] args) {
        new Server();
    }

}
