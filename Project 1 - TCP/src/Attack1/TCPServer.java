package Attacks;

import Attack1.ClientHandler;

import java.io.*;
import java.net.*;

public class TCPServer {

    // Declare the port number used by server as a private variable
    private static final int PORT = 6789;

    /*
     !!!!!!!!!! RUN FIRST !!!!!!!!!!
     */

    public static void main(String[] args) throws Exception {

        System.out.println("Server up and running, waiting for requests...");

        // Create welcoming socket at specified port #
        ServerSocket welcomeSocket = new ServerSocket(PORT);

        // Create a new connection socket
        Socket connectionSocket = null;

        try {

            while (true) {

                // Wait on welcoming socket for contact by client, creates a new socket
                connectionSocket = welcomeSocket.accept();
                System.out.println("Connected to client.");

                // Create input stream attached to socket
                BufferedReader inFromClient = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));

                // Create output stream attached to socket
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // Assign a thread for the new connected client
                ClientHandler thread = new ClientHandler(connectionSocket, inFromClient, outToClient);
                // Begin executing the thread associated with the connected client
                thread.start();

            }// Loop back and wait for another client connection

        }catch (Exception e){
            // close the connection whenever the client requests
            connectionSocket.close();
        }

        }

}