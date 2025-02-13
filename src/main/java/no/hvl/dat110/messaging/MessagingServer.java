package no.hvl.dat110.messaging;

import java.net.Socket;

import java.io.IOException;
import java.net.ServerSocket;

import no.hvl.dat110.TODO;

public class MessagingServer {

	// server-side socket for accepting incoming TCP connections
	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {

		try {

			this.welcomeSocket = new ServerSocket(port);

		} catch (IOException ex) {

			System.out.println("Messaging server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public MessageConnection accept() {
	    try {
	        Socket socket = welcomeSocket.accept();
	        return new MessageConnection(socket);
	    } catch (IOException e) {
	        System.out.println("Error accepting connection: " + e.getMessage());
	        e.printStackTrace();
	        return null; // Returner null hvis det oppstår en feil
	    }
	}


	public void stop() {

		if (welcomeSocket != null) {

			try {
				welcomeSocket.close();
			} catch (IOException ex) {

				System.out.println("Messaging server: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}
