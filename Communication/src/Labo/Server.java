package Labo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("SERVER: Could not listen on port: 4444, " + e);
            System.exit(1);
        }
        System.out.println("SERVER: Server connection opened on port 4444.");
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("SERVER: Accept failed: 4444, " + e);
                System.exit(1);
            }
            System.out.println("SERVER: Accepted client connecion on port 4444.");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("SERVER: IN and OUT streams opened. Starting receiving data...");

                out.println("Witam na serwerze. Czekam na dane.");
                out.flush();

                String inputLine, outputLine;
                ChangeOrder handler = new ChangeOrder();
                while ((inputLine = in.readLine()) != null) {
                    outputLine = handler.processMessage(inputLine);
                    out.println(outputLine);
                    out.flush();
                    if (inputLine.equals("koniec"))
                        break;
                }
                System.out.println("SERVER: Ending sequence received. Closing streams and sockets.");
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
