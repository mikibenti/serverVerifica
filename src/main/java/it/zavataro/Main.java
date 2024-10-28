package it.zavataro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {    
    public static ArrayList<String> usernames = new ArrayList<>();
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(3000);
            System.out.println("Server Avviato");
            do {
                Socket s = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                System.out.println("Client Connesso");
                String username = "";
                while (true) {
                    username = in.readLine();
                    if (usernames.contains(username)) {
                        out.writeBytes("CHA" + "\n");
                    } else {
                        out.writeBytes("REG" + "\n");
                        usernames.add(username);
                        break;
                    }
                }
                System.out.println("User Client : " + username);
                MyThread t = new MyThread(s,username);
                t.start();
            } while (true);
        } catch (IOException e) {
            System.out.println("ERRORE");
            System.exit(1);
        }
    }
}