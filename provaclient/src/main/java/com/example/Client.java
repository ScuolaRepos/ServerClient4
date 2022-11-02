package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 3000);
        Controllo contr = new Controllo(s);
        contr.start();

        // per parlare
        PrintWriter pr = new PrintWriter(s.getOutputStream(), true);
        // per ascoltare
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        // per la tastiera
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
    
        Boolean loop = true;
        pr.println("Eccomi");

        System.out.println(br.readLine()); // ricevo: Ciao come ti chiami?
        pr.println(tastiera.readLine()); // leggo da tastiera il nome e lo invio
        System.out.println(br.readLine()); // ricevo: Salve {nome} sei l'utente connesso numero {x} + i comandi disponibili

        while(loop) {
            
            pr.println(tastiera.readLine());
        };
        br.close();
        pr.close();
        s.close();
    }
}
