package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;
import java.io.IOException; 

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;
	private HashMap<Byte, RPCRemoteImpl> services; 
	
	public RPCServer(int port) {
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {
	    try {
	        RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP, this);
	        services.put(RPCCommon.RPIDSTOP, rpcstop); 

	        System.out.println("RPC SERVER RUN - Services: " + services.size());
	        connection = msgserver.accept(); 

	        System.out.println("RPC SERVER ACCEPTED");

	        boolean stop = false;

	        while (!stop) {
	            Message requestMsg = connection.receive(); 
	            byte[] requestData = requestMsg.getData(); 

	            byte rpcid = requestData[0];
	            byte[] payload = RPCUtils.decapsulate(requestData);

	            RPCRemoteImpl method = services.get(rpcid);
	            byte[] replyData = method.invoke(payload);

	            connection.send(new Message(RPCUtils.encapsulate(rpcid, replyData)));

	            if (rpcid == RPCCommon.RPIDSTOP) {
	                stop = true;
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("IOException in RPCServer: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        stop(); // Lukk tilkoblingen når serveren stopper
	    }
	}

	// used by server side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {
		try {
			
		if (connection != null) { 
			connection.close();
		}
		} catch (Exception e) {
			System.out.println("Error closing connection: " + e.getMessage());
			
		}
		
		if (msgserver != null) {
			msgserver.stop();
		
	}
	}
}
