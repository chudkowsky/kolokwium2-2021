package com.example.kolokwium22021.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerInstance extends Thread{
    private final ServerSocket server;
    private List<ServerClientThread> clients = new ArrayList<>();
    public ServerInstance(int port) throws IOException {
        this.server = new ServerSocket(port);
    }
    @Override
    public void run(){
        while (true){
            try {
                final Socket socket = server.accept();
                final ServerClientThread client = new ServerClientThread(socket,this);
                addClient(client);
                client.start();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
    public void broadcast(String message) throws IOException{
        for(ServerClientThread client :clients){
            client.sendMessage(message);
        }
    }
    public void addClient(ServerClientThread serverClientThread){
        clients.add(serverClientThread);
    }
    public void removeClient(ServerClientThread serverClientThread){
        clients.remove(serverClientThread);
    }
}
