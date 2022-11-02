package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket s;
    private PrintWriter pr = null;
    private BufferedReader br = null;
    private int contatore;
    private LocalDate data;
    private LocalTime ora;
    private ArrayList<ClientHandler> clients;

    public ClientHandler(Socket s, int c, String nome, ArrayList<ClientHandler> cls) {
        this.s = s;
        contatore = c;
        setName(nome);
        clients = cls;
        
        try {
            // per parlare
            pr = new PrintWriter(s.getOutputStream(), true);
            // per ascoltare
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println(br.readLine());
            pr.println("Ciao come ti chiami?"); // invio messaggio
            String nome = br.readLine(); // ricevo: il nome
            String nomeUpper = nome.toUpperCase(); // nome in maiuscolo
            System.out.println("Utente di nome " + nomeUpper + " connesso"); // console: nome ricevuto
            pr.println("Salve " + nomeUpper + " sei l'utente connesso numero " + contatore + ", i comandi disponibili sono: data, ora, nome, id, fine e chiudi"); // invio messaggio
            Boolean chatOn = true;

            while(chatOn) {
                String input = br.readLine();

                switch(input) {
                    default: {
                        pr.println("comando non riconosciuto");
                    }break;
                    case("data"): {
                        data = LocalDate.now();
                        pr.println("data: " + data);
                    }break;
                    case("ora"): {
                        ora = LocalTime.now();
                        pr.println("ora: " + ora);
                    }break;
                    case("nome"): {
                        pr.println("nome: " + getName());
                    }break;
                    case("id"): {
                        pr.println("id: " + getId());
                    }break;
                    case("fine"): {
                        pr.println("chiuso");
                        chatOn = false;
                    }break;
                    case("chiudi"): {
                        for(int i = 0; i < clients.size(); i++) {
                            clients.get(i).pr.println("chiuso");
                        }
                        chatOn = false;
                    }break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
