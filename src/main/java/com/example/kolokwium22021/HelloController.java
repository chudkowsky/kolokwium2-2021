package com.example.kolokwium22021;

import com.example.kolokwium22021.Client.ServerConnectionThread;
import com.example.kolokwium22021.Server.ServerInstance;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class HelloController {

    private ServerConnectionThread serverConnectionThread;
    @FXML
    private Canvas canva;


    @FXML
    private TextField textField;

    @FXML
    protected void StartServerAndConnect() {
        try {
            ServerInstance server = new ServerInstance(50008);
            server.start();
            System.out.println("Server started on port 50008");
            serverConnectionThread = new ServerConnectionThread("localhost", 50008, this);
            serverConnectionThread.start();
            System.out.println("Prawidlowo podlaczony");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void ConnectToServer() throws IOException {
        if (serverConnectionThread != null) {
            System.out.println("Already connected\n");
        } else {
            try {
                serverConnectionThread = new ServerConnectionThread("localhost", 50008, this);
                serverConnectionThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Prawidlowo podlaczony");
            }
        }
    }

    @FXML
    protected void onSendButtonClick() throws IOException {
        String message = textField.getText();
        serverConnectionThread.sendToServer(message);
    }

    private Color color = Color.BLACK;

    @FXML
    public Color getColor() {
        return color;
    }

    @FXML
    public void drawLine(String message, Color color) {
        System.out.println("wykonuje sie");
        double x1, x2, y1, y2;
        if (message.startsWith("#")) {
            String colorCode = message.substring(1);
            this.color = Color.web(colorCode);
            System.out.println(String.format("R = %f, G = %f, B = %f", color.getRed(), color.getGreen(), color.getBlue()));
        } else {
            String[] tmp = message.split(" ");
            x1 = Double.parseDouble(tmp[0]);
            y1 = Double.parseDouble(tmp[1]);
            x2 = Double.parseDouble(tmp[2]);
            y2 = Double.parseDouble(tmp[3]);
            GraphicsContext context = canva.getGraphicsContext2D();
            context.setStroke(color);
            context.strokeLine(x1, y1, x2, y2);
            System.out.println(String.format("x1 = %f, y1 = %f  x2 = %f, y2 = %f", x1, y1, x2, y2));
        }
    }

}
