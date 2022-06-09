package Attack2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class TCPClient{


	private static final String hostname = "localhost";
	private static int ServerPort = 6789;

	public static void main(String[]args)throws Exception{

		String sentence = "";
		String modifiedSentence=" ";

		// create client socket and connect to server; this initiates TCP cnx between client and server
		Socket clientSocket = new Socket(hostname, ServerPort);

		// create input stream, client reads line from standard input
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		// create output stream attached to socket
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		// create input stream attached to socket
		BufferedReader inFromServer = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));

		//modified sentence will be null when connection is closed due to flooding the buffer.
		while (true && modifiedSentence != null) {

			System.out.println("Client is running, enter some text:");

			StringBuilder sb = new StringBuilder();
			sentence = inFromUser.readLine();

			//byteBuffer array of chars to represent maximum server buffer capacity
			char[] byteBuffer = new char[(byte)64000];

			int ByteBufferLength = byteBuffer.length;



			if (sentence.equals("Exit")) {
				System.out.println("Closing this connection..." + clientSocket);
				clientSocket.close();
				System.out.println("Connection closed.");
				break;
			}

			// Bad Client will flood the server's buffer by sending more Bytes than the buffer can carry.
			// Bad Client will keep on appending the same input until the buffer size is exceeded.

			while (sb.length() < 65536) {       //Buffer Size is 64KB.
				sb.append(sentence);
			}

			if (ByteBufferLength <= sb.length()) {
				System.out.println("Buffer is flooded. Connection Closed. ");
				clientSocket.close();
				break;
			}
			outToServer.writeBytes(sb.toString() + '\n');
			System.out.println("Length of bad client message: " + outToServer.size());

			modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);

		}

		clientSocket.close();
	}
}