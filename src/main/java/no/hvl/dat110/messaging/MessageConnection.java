package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {
		try {
			this.socket = socket;
			outStream = new DataOutputStream(socket.getOutputStream());
			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) throws IOException {
		byte[] segment = MessageUtils.encapsulate(message);
		outStream.write(segment);
		outStream.flush(); 

	}

	public Message receive() throws IOException {
		byte[] segment = new byte[MessageUtils.SEGMENTSIZE]; 
		inStream.readFully(segment); 
		return MessageUtils.decapsulate(segment); 
		
	}

	public void close() {
	    try {
	        if (outStream != null) outStream.close();
	        if (inStream != null) inStream.close();
	        if (socket != null) socket.close();
	    } catch (IOException ex) {
	        System.out.println("Error closing connection: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	}
 
}