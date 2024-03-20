package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

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
            new Thread(new InputHandler(client)).start();
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
        Socket socket;
        Scanner console;

        public InputHandler(Socket socket) {
            this.socket = socket;
            this.console = new Scanner(System.in);
        }

        @Override
        public void run() {
            Random rand = new Random();
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String message;
            try {
                while(!(message = console.nextLine()).equalsIgnoreCase("quit")){
                    Message message_protocol = new Message();
                    message_protocol.setContent(message);
                    message_protocol.setId(rand.nextInt(999));
                    out.writeObject(message_protocol);
                }
                out.close();
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
