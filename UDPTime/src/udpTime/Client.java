package udpTime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	
	private final int port = 2000;
	InetAddress sAddress;
	DatagramSocket dSocket;
	DatagramPacket inPacket;
	DatagramPacket outPacket;
	String message = "RICHIESTA DATA E ORA", response;
	byte[] buffer;
	
	public void connection() {
		try {
			sAddress = InetAddress.getLocalHost();
			System.out.println("Connected.");
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void comunication() {
		try {
			dSocket = new DatagramSocket();
			outPacket = new DatagramPacket(message.getBytes(), message.length(), sAddress, port);
			dSocket.send(outPacket);
			buffer = new byte[256];
			inPacket = new DatagramPacket(buffer, buffer.length);
			dSocket.receive(inPacket);
			response = new String(inPacket.getData(), 0, inPacket.getLength());
			System.out.println("Data e ora del server: " + response);
			dSocket.close();
		}
		catch (SocketException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.connection();
		client.comunication();
	}
	
}