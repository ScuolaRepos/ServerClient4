package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controllo extends Thread{
    Socket s;
    PrintWriter pr;
    BufferedReader br;
    BufferedReader tastiera;

    public Controllo(Socket s) {
        try {
            this.s = s;
            // per parlare
            pr = new PrintWriter(s.getOutputStream(), true);
            // per ascoltare
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            // per la tastiera
            tastiera = new BufferedReader(new InputStreamReader(System.in));
        
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        String inputStr;
        Boolean chatOn  = true;

        while(chatOn) {
            try {
                inputStr = br.readLine();
                if(inputStr.equals("chiuso")) {
                    System.out.println("chiudo la connessione");
                    chatOn = false;
                    s.close();
                    System.exit(0);
                }
                System.out.println("Server:" + inputStr);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        };
    }
    
}
