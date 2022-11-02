package com.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
  static Boolean running = true;
  public static void main(String[] args) throws Exception {
    ServerSocket ss = new ServerSocket(3000);
    ArrayList<ClientHandler> clients = new ArrayList<>();
    System.out.println("Server in ascolto sulla porta 3000");
    int c = 1;

    while(running) {
      Socket s = ss.accept();
      System.out.println("Client connesso");
      ClientHandler client = new ClientHandler(s, c, "Server Maurizio", clients);
      clients.add(client);
      c++;
      client.start();
    } 
    ss.close();
  }
}