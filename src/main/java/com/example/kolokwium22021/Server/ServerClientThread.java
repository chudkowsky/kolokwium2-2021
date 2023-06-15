package com.example.kolokwium22021.Server;

import com.example.kolokwium22021.HelloController;

import java.io.*;
import java.net.Socket;
public class ServerClientThread extends Thread{
    private final Socket socket;
    private final ServerInstance server;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ServerClientThread(Socket socket,ServerInstance server)throws IOException {
        this.socket = socket;
        this.server = server;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    public void run(){
        try {
            String message;
         while ((message = readFromClient()) != null) {
             System.out.println(message);
             server.broadcast(message);
            }
        } catch (IOException e) {
        server.removeClient(this);
        cleanUpConnection();
        throw new RuntimeException(e);
        }
    }
    public String readFromClient() throws IOException {
        return this.reader.readLine();
    }
    public void cleanUpConnection(){
        try {
            if(socket!=null) socket.close();
            if(reader!=null) reader.close();
            if(writer!=null) writer.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) throws IOException{
        this.writer.write(message);
        this.writer.newLine();
        this.writer.flush();
    }

}
