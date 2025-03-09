package org.beast.addpersonalb.guiLocalServer;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class chatServer {
	public static void main(String[] args) {
		serverFrame server=new serverFrame();
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
}
class serverFrame extends JFrame implements Runnable {
	public serverFrame() {
		setTitle("servidor");
		setBounds(1200, 300, 280, 200);

		JPanel panel1 = new JPanel();
		textAr = new JTextArea();
		panel1.add(textAr);
		add(panel1);

		setVisible(true);
		Thread useSrvrThread = new Thread(this);
		useSrvrThread.start();
	}

	private final ConcurrentHashMap<String, Socket> connectedClients = new ConcurrentHashMap<>();
	private final JTextArea textAr;

	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(9999)) {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				InetAddress clientAddress = clientSocket.getInetAddress();
				String clientIp = clientAddress.getHostAddress();
				String clientId = UUID.randomUUID().toString();


				ObjectInputStream dataIn = new ObjectInputStream(clientSocket.getInputStream());
				shippingDataPackage receivedPackage = (shippingDataPackage) dataIn.readObject();
				String senderIp = receivedPackage.getIp();
				String message = receivedPackage.getMessage();
				receivedPackage.setClientId(clientId);


				connectedClients.put(clientIp, clientSocket);
				System.out.println("[" + clientIp + "] online");

				// manejo de una nueva conexion
				if (!connectedClients.containsKey(clientIp)) {
					connectedClients.put(clientIp, clientSocket);
					System.out.println("[" + clientIp + "] online");

					// Transmitir a los clientes conectados quien se ha conectado

					shippingDataPackage onlinePackage = new shippingDataPackage();
					onlinePackage.setMessage(" online");
					onlinePackage.getIps().add(clientIp);
					onlinePackage.setIps(new ArrayList<>(connectedClients.keySet()));
					broadcastMessage(onlinePackage, clientSocket, clientId);
				}
				if (receivedPackage.getMessage() != null && !receivedPackage.getMessage().equals(" online")) {
					textAr.append("\n[" + receivedPackage.getNick() + "]:" + message);
					broadcastMessage(receivedPackage,clientSocket,clientId);
				}
				else {
					textAr.append("\n[" + receivedPackage.getNick() + "]:");
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	private void broadcastMessage(shippingDataPackage packageToSend, Socket senderSocket, String clientId) throws IOException {
		for (Map.Entry<String, Socket> entry : connectedClients.entrySet()) {
			String currentClientId = entry.getKey();
			Socket clientSocket = entry.getValue();
			
			if (!currentClientId.equals(clientId)) {
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				out.writeObject(packageToSend);
				out.flush();
			}
		}
	}
}

