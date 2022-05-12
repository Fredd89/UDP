package udpTime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class Server {
    
    private final int port = 2000;
    DatagramSocket dSocket;
    byte[] buffer;
    DatagramPacket inPacket;
    DatagramPacket outPacket;
    String messageIn, messageOut;
    Date date;
    
    public void listen() {
        try {
            dSocket = new DatagramSocket(port);
            System.out.println("Server listening on port: " + port);
        }
        catch (SocketException e) {
			e.printStackTrace();
		}
    }
    
    public void comuncation() {
		try {
			
			buffer = new byte[256];
			inPacket = new DatagramPacket(buffer, buffer.length);
			dSocket.receive(inPacket);
			InetAddress cAddress = inPacket.getAddress();
			int cPort = inPacket.getPort();
			
			messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
			System.out.println("CLIENT " + cAddress + ":" + cPort + "-> " + messageIn);
			
			date = new Date();
			messageOut = date.toString();
			
			outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), cAddress, cPort);
			dSocket.send(outPacket);
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args) {
    	Server server = new Server();
    	server.listen();
    	server.comuncation();
    }
    
}