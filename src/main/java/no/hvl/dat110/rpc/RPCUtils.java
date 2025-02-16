package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class RPCUtils {
	
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
	    if (payload == null) {
	        payload = new byte[0]; // Avoid null reference
	    }

	    byte[] rpcmsg = new byte[1 + payload.length];
	    rpcmsg[0] = rpcid;
	    System.arraycopy(payload, 0, rpcmsg, 1, payload.length);
	    return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
		return Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length); // Fjerner første byte (rpcid)
	}

	public static byte[] marshallString(String str) {
		return str.getBytes(); // Konverterer string til byte-array
	}

	public static String unmarshallString(byte[] data) {
		return new String(data); // Konverterer byte-array til string
	}

	public static byte[] marshallVoid() {
		return new byte[0]; // Tom byte-array for void
	}

	public static void unmarshallVoid(byte[] data) {
		// Ingenting å gjøre for void
	}

	public static byte[] marshallInteger(int x) {
		return ByteBuffer.allocate(4).putInt(x).array(); // Konverter int til byte-array
	}

	public static int unmarshallInteger(byte[] data) {
		return ByteBuffer.wrap(data).getInt(); // Konverter byte-array til int
	}
	
	public static byte[] marshallBoolean(boolean b) {
		byte[] encoded = new byte[1]; 
		encoded[0] = (byte) (b ? 1 : 0); 
		return encoded; 
	}
	
	public static boolean unmarshallBoolean(byte[] data) {
		return (data[0] == 1); 
	}
	}
 