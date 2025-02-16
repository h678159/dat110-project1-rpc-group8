package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;
import java.io.IOException; 

public class RPCClient {

	private MessagingClient msgclient;
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {
	    connection = msgclient.connect();
	}

	public void disconnect() {
	    try {
	        if (connection != null) {
	            connection.close();
	        }
	    } catch (Exception e) {
	        System.out.println("Error disconnecting: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public byte[] call(byte rpcid, byte[] param) {
	    byte[] response = null;
	    try {
	        byte[] request = RPCUtils.encapsulate(rpcid, param);
	        connection.send(new Message(request));

	        Message responseMessage = connection.receive();
	        response = RPCUtils.decapsulate(responseMessage.getData());
	    } catch (IOException e) {
	        System.out.println("Error during RPC call: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return response;
	}

}
