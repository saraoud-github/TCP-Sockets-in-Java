package Attack2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private static final int PORT = 6789;

	public static void main(String[] args) throws Exception {

		System.out.println("Server up and running, waiting for requests...");

		// create welcoming socket at port 6789
		ServerSocket welcomeSocket = new ServerSocket(PORT);

		Socket connectionSocket = null;


		try {
			while (true) {

				connectionSocket = welcomeSocket.accept();
				System.out.println("Connected to client.");

				// create input stream attached to socket
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));

				// create output stream attached to socket
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

				System.out.println("Assigning new thread for this client");

				ClientHandler thread = new ClientHandler(connectionSocket, inFromClient, outToClient);
				thread.start();

			} // loop back and wait for another client connection

		} catch (Exception e) {
			connectionSocket.close();
		}

	}
}