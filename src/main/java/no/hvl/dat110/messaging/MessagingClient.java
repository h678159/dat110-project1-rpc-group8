package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.Socket;
import no.hvl.dat110.TODO;

public class MessagingClient {

	
	// name/IP address of the messaging server
	private String server;
	// server port on which the messaging server is listening
	private int port;
	
	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	
	// setup of a messaging connection to a messaging server
	public MessageConnection connect() {
	    Socket clientSocket = null;
	    MessageConnection connection = null;
	    int attempts = 3; // Retry up to 3 times

	    while (attempts > 0) {
	        try {
	            clientSocket = new Socket(server, port);
	            connection = new MessageConnection(clientSocket);
	            return connection;
	        } catch (IOException e) {
	            System.err.println("Failed to connect, retrying... (" + attempts + " attempts left)");
	            attempts--;
	            try {
	                Thread.sleep(1000); // Wait before retrying
	            } catch (InterruptedException ignored) {}
	        }
	    }

	    throw new RuntimeException("Unable to connect to the messaging server.");
	}
}
