package udpTime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	
	private final int port = 2000;
	InetAddress mcastaddr;
	InetSocketAddress group;
	NetworkInterface netIf;
	MulticastSocket mSocket;
	DatagramPacket inPacket;
	DatagramPacket outPacket;
	String message = "Hello I am the client.", response;
	byte[] buffer;
	
	public void connection() {
		try {
			mcastaddr = InetAddress.getByName("224.0.0.20");
			InetSocketAddress group = new InetSocketAddress(mcastaddr, port);
			netIf = NetworkInterface.getByName("test");
			mSocket = new MulticastSocket(port);
			mSocket.joinGroup(group, netIf);
			System.out.println("Connected.");
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void comunication() {
		try {
			outPacket = new DatagramPacket(message.getBytes(), message.length(), group);
			mSocket.send(outPacket);
			buffer = new byte[256];
			inPacket = new DatagramPacket(buffer, buffer.length);
			mSocket.receive(inPacket);
			response = new String(inPacket.getData(), 0, inPacket.getLength());
			System.out.println("SERVER-> " + response);
			mSocket.leaveGroup(group, netIf);
			mSocket.close();
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