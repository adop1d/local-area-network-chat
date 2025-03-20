package org.beast.addpersonalb.guiLocalServer;

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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class chatServer {
	public static void main(String[] args) {
		serverFrame server = new serverFrame();
		server.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
		Thread serverThread = new Thread(this);
		serverThread.start();
	}

	private final ConcurrentHashMap<String, Socket> connectedClients = new ConcurrentHashMap<>();
	private final JTextArea textAr;

	public void run() {
			try (ServerSocket serverSocket = new ServerSocket(5065)) {
					System.out.println("server started on port: " + serverSocket.getLocalPort());

					while (true) {
							Socket clientSocket = serverSocket.accept();
							InetAddress clientAddress = clientSocket.getInetAddress();
							String clientIp = clientAddress.getHostAddress();
							String clientId = UUID.randomUUID().toString();

							System.out.println(clientIp + " has connected.");

							// Crear un hilo para manejar este cliente
							new Thread(() -> handleClient(clientSocket, clientId)).start();
					}
			} catch (IOException e) {
					System.out.println("server error: " + e.getMessage());
			}
	}

	private void handleClient(Socket clientSocket, String clientId) {
			try {
					ObjectInputStream dataIn = new ObjectInputStream(clientSocket.getInputStream());

					while (true) {
							shippingDataPackage receivedPackage = (shippingDataPackage) dataIn.readObject();
							String senderNick = receivedPackage.getNick();
							String message = receivedPackage.getMessage();
							System.out.println("message received from [" + senderNick + "]: " + message);

							// Guardar cliente si es nuevo
							if (!connectedClients.containsValue(clientSocket)) {
									connectedClients.put(clientId, clientSocket);
									System.out.println("client added to the list: " + clientId);

									// Enviar lista de clientes conectados
									shippingDataPackage onlinePackage = new shippingDataPackage();
									onlinePackage.setMessage("online");
									onlinePackage.setIps(new ArrayList<>(connectedClients.keySet()));
									broadcastMessage(onlinePackage, clientSocket);
							}

							// Si es un mensaje normal, retransmitir
							if (message != null && !message.equals("online")) {
									textAr.append("\n[" + senderNick + "]: " + message);
									broadcastMessage(receivedPackage, clientSocket);
							}
					}
			} catch (IOException | ClassNotFoundException e) {
					System.out.println("Client disconnected: " + clientId);
					connectedClients.remove(clientId);
			}
	}

	private void broadcastMessage(shippingDataPackage packageToSend, Socket senderSocket) {
			ArrayList<String> disconnectedClients = new ArrayList<>();

			for (Map.Entry<String, Socket> entry : connectedClients.entrySet()) {
					Socket clientSocket = entry.getValue();

					if (!clientSocket.equals(senderSocket)) {
							try {
									ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
									out.writeObject(packageToSend);
									out.flush();
									System.out.println("message sent to " + entry.getKey());
							} catch (IOException e) {
									System.out.println("error sending message to " + entry.getKey() + ": " + e.getMessage());
									disconnectedClients.add(entry.getKey()); // Marcar clientes desconectados
							}
					}
			}

			// Eliminar clientes desconectados
			for (String client : disconnectedClients) {
					connectedClients.remove(client);
					System.out.println(client + " has disconnected");
			}
	}
}