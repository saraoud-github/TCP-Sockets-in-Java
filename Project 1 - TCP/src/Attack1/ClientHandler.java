package Attack1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler extends Thread{

    final private BufferedReader inFromClient;
    final private DataOutputStream outToClient;
    final private Socket connectionSocket;;

    // Define Attack1.ClientHandler's construct and instantiate the socket, input from client, and output to the client
    public ClientHandler(Socket connectionSocket, BufferedReader inFromClient, DataOutputStream outToClient){
        this.connectionSocket = connectionSocket;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }

    @Override // Overrides Thread class's run() method
    public void run(){
        String clientSentence; // Client input
        String capitalizedSentence; // Server output


            while (true) {
                try {
                    clientSentence = inFromClient.readLine(); // Read line from input


                    if (clientSentence.equals("Exit")) { // If user inputs "Exit", close this particular client's connection
                        System.out.println("Client " + this.connectionSocket + " sends exit...");
                        System.out.println("Closing this connection...");
                        this.connectionSocket.close();
                        System.out.println("Connection closed.");
                        break;
                    }

                        // Capitalize client's message
                        capitalizedSentence = clientSentence.toUpperCase() + '\n';
                        outToClient.writeBytes(capitalizedSentence); // Send the modified sentence to the client


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        try {
            inFromClient.close(); // Close input stream
            outToClient.close(); // Close output stream
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    }


