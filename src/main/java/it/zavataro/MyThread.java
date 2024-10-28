package it.zavataro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MyThread extends Thread{
    private Socket mySocket;
    ArrayList<String> list;
    String username;

    public MyThread(Socket s, String u) {
        mySocket = s;
        list = new ArrayList<>();
        username = u;
    }

    @Override
    public void run() {
        String stringRed = "";
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
                DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
                do {    
                    stringRed = in.readLine();
                    if (stringRed.equals("#")) {
                        String delete = in.readLine().toLowerCase();
                        if (list.contains(delete)) {
                            list.remove(delete);
                            out.writeBytes("OK" + "\n");
                        } else {
                            out.writeBytes("NO" + "\n");
                        }
                    } else if (stringRed.equals("?")) {
                        for (int i = 0; i < list.size(); i++) {
                            out.writeBytes(list.get(i) + "\n");
                        }
                        out.writeBytes("@" + "\n");
                    } else if (stringRed.equals("!")) {
                        mySocket.close();
                        break;
                    } else {
                        list.add(stringRed.toLowerCase());
                        out.writeBytes("OK" + "\n");
                    }
                } while(true);
            } catch (Exception e) {
                System.err.println("ERRORE");
                System.exit(1);
            }
        super.run();
    }
}