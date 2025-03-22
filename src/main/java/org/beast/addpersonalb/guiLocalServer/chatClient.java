package org.beast.addpersonalb.guiLocalServer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.beast.addpersonalb.utils.actioMur;

public class chatClient extends JFrame {
	static String userNick;

	public chatClient(String userNick) {

		setTitle("client");
		setBounds(600, 300, 364, 300);
		panelFrameClient frame_a = new panelFrameClient(userNick);
		add(frame_a);
		setVisible(true);
		setResizable(false);

		this.userNick = userNick;
	}
}

class panelFrameClient extends JPanel implements Runnable {
	private static final Logger logger = Logger.getLogger(panelFrameClient.class.getName());
	private final String userNick;
	private HashMap<String, String> userMap;

	public panelFrameClient(String userNick) {
		this.userNick = userNick;
		this.userMap = new HashMap<>();

		setLayout(null);
		setBackground(new Color(210, 210, 210));
		actioMur.setCustomCursor(this);
		addMouseMotionListener(new actioMur());

		nick = new JLabel();
		nick.setText(userNick);
		nick.setForeground(new Color(90, 90, 90));
		add(nick);
		nick.setBounds(41, 4, 100, 20);

		ip = new JComboBox();
		ip.setBackground(new Color(90, 90, 90));
		ip.setForeground(new Color(255, 255, 255));
		add(ip);
		ip.setBounds(163, 4, 100, 28);

		JLabel text = new JLabel("[ip]:");
		JLabel text2 = new JLabel("[user]:");
		add(text);
		text.setBounds(140, 4, 80, 20);
		text.setForeground(new Color(90, 90, 90));
		add(text2);
		text2.setBounds(2, 4, 80, 20);
		text2.setForeground(new Color(90, 90, 90));

		chatField = new JTextArea();
		chatField.setLineWrap(true);
		chatField.setBackground(new Color(210, 210, 210));
		chatField.setEnabled(false);

		chatFieldScroll = new JScrollPane(chatField,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(chatFieldScroll);
		chatFieldScroll.setBounds(3, 30, 358, 220);

		field1 = new JTextField(20);
		field1.setBackground(new Color(90, 90, 90));
		field1.setForeground(new Color(255, 255, 255));
		field1.addKeyListener(new enterKeyListener(this));
		add(field1);
		field1.setBounds(0, 246, 364, 28);

		Thread thread_a = new Thread(this);
		thread_a.start();
	}

	public void run() {
		try {
			Socket clientSocket = new Socket("localhost", 5065); // Conéctate al servidor
			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream entryFlow = new ObjectInputStream(clientSocket.getInputStream());

			// a ver si con esto es suficiente
			shippingDataPackage onlinePackage = new shippingDataPackage();
			onlinePackage.setNick(userNick);
			onlinePackage.setMessage("online");

			out.writeObject(onlinePackage);
			out.flush();
			System.out.println("Sent online status for: " + userNick);

			while (true) {
				try {
					shippingDataPackage receivedPackage = (shippingDataPackage) entryFlow.readObject();

					if (receivedPackage.getMessage().equals("online")) {
						HashMap<String, String> receivedUserMap = receivedPackage.getUserMap();

						if (receivedUserMap != null && !receivedUserMap.isEmpty()) {
							this.userMap.clear();
							this.userMap.putAll(receivedUserMap);

							SwingUtilities.invokeLater(() -> {
								ip.removeAllItems();
								for (String user : userMap.keySet()) {
									ip.addItem(user);
								}
								System.out.println("Updated user list: " + userMap);
							});
						}
					}
				} catch (ClassNotFoundException e) {
					System.out.println("Error receiving package: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println(" Error connecting to server: " + e.getMessage());
		}
	}

	private boolean processIncomingPackages(ObjectInputStream entryFlow) {
		try {
			shippingDataPackage receivedPackage = (shippingDataPackage) entryFlow.readObject();

			if (receivedPackage.getMessage().equals("online")) {
				HashMap<String, String> receivedUserMap = receivedPackage.getUserMap();

				if (receivedUserMap != null && !receivedUserMap.isEmpty()) {
					this.userMap.clear();
					this.userMap.putAll(receivedUserMap);

					SwingUtilities.invokeLater(() -> {
						ip.removeAllItems();
						for (String user : userMap.keySet()) {
							ip.addItem(user);
						}
						logger.info("🔄 Updated user list: " + userMap);
					});
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			logger.severe("Error receiving package: " + e.getMessage());
			return false;
		}
		return true;
	}

	private class sendTxt extends Component implements ActionListener {
		public sendTxt(panelFrameClient panel) {
				this.panel = panel;
		}

		public void actionPerformed(ActionEvent e) {
				String selectedUser = (String) panel.ip.getSelectedItem();
				if (selectedUser != null) {
						try {
								String userIp = panel.userMap.get(selectedUser);
								if (userIp == null) {
										JOptionPane.showMessageDialog(null, "No se encontró la IP de " + selectedUser);
										return;
								}
								logger.info("Trying to connect to " + selectedUser + " (" + userIp + ")");
								Socket ticketDataEntry = new Socket(userIp, 5065);
								ObjectOutputStream dataPackage = new ObjectOutputStream(ticketDataEntry.getOutputStream());

								shippingDataPackage dataToSend = new shippingDataPackage();
								dataToSend.setNick(userNick);
								dataToSend.setIp(userIp);
								dataToSend.setMessage(panel.field1.getText());

								dataPackage.writeObject(dataToSend);
								dataPackage.flush();
								logger.info("Sending message: [" + userNick + "]: " + panel.field1.getText());

								panel.chatField.append("\n[" + userNick + "]:" + panel.field1.getText());

						} catch (IOException ex) {
								logger.severe("Error connecting to " + selectedUser + ": " + ex.getMessage());
								JOptionPane.showMessageDialog(null, "Error connecting to " + selectedUser);
						}
				} else {
						JOptionPane.showMessageDialog(null, "No user selected");
				}
				panel.field1.setText("");
		}

		private final panelFrameClient panel;
}
	private class enterKeyListener implements KeyListener {
		public enterKeyListener(panelFrameClient panel) {
			this.panel = panel;
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendTxt sendTextEvent = new sendTxt(panel);
				sendTextEvent.actionPerformed(null);
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}

		private final panelFrameClient panel;
	}

	private final JTextField field1;
	private JComboBox<String> ip = new JComboBox<>();
	private final JLabel nick;
	private final JTextArea chatField;
	private final JScrollPane chatFieldScroll;
}