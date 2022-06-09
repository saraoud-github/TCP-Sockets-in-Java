package Attack1;

import java.io.*;
import java.net.*;

public class TCPClient{

    // Declare the server hostname as a private variable
    private static final String hostname = "localhost";
    // Declare the port number used by client as a private variable
    private static int ServerPort = 6789;
    /*
     !!!!!!!!!! RUN SECOND !!!!!!!!!!
     */

    public static void main(String[]args)throws Exception{

        String sentence; // Data to send to server
        String modifiedSentence; // Data received from server

        // Create client socket and connect to server; this initiates TCP cnx between client and server
        Socket clientSocket = new Socket(hostname, ServerPort);

        // Create input stream, client reads line from standard input
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        // Create output stream attached to socket
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        // Create input stream attached to socket
        BufferedReader inFromServer = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

            while (true) {

                System.out.println("Client is running, enter some text:");
                // Read user's input and store it in a String
                sentence = inFromUser.readLine();

                // Send the user's input to the server
                outToServer.writeBytes(sentence + '\n'); //  Characters continue to accumulate in modifiedSentence until the line ends with a carriage return

                if (sentence.equals("Exit")) { // If user inputs "Exit", close the connection
                    System.out.println("Closing this connection..." + clientSocket);
                    clientSocket.close();
                    System.out.println("Connection closed.");
                    break;
                }
                    // Read line from server
                    modifiedSentence = inFromServer.readLine();
                    // Display the server's output to the user
                    System.out.println("FROM SERVER: " + modifiedSentence);

            }

            clientSocket.close(); // Close the socket
            System.out.println("Client closed the socket and is done execution");


    }
}