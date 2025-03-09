package org.beast.addpersonalb.guiLocalServer;

import org.beast.addpersonalb.utils.actioMur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class chatClient extends JFrame {
	final String userNick;
	public chatClient(String userNick){

		setTitle("client");
		setBounds(600,300,364,300);
		panelFrameClient frame_a =new panelFrameClient(userNick);
		add(frame_a);
		setVisible(true);
		setResizable(false);

		this.userNick = userNick;
	}
}
class panelFrameClient extends JPanel implements Runnable{
	private final String userNick;

	public panelFrameClient(String userNick){
		this.userNick = userNick;

		setLayout(null);
		setBackground(new Color(210,210,210));
		actioMur.setCustomCursor(this);
		addMouseMotionListener(new actioMur());

		nick=new JLabel();
		nick.setText(userNick);
		nick.setForeground(new Color(90,90,90));
		add(nick);nick.setBounds(41,4,100,20);

		ip=new JComboBox();
		ip.setBackground(new Color(90,90,90));
		ip.setForeground(new Color(255,255,255));
		add(ip);ip.setBounds(163,4,100,28);
	
		JLabel text =new JLabel("[ip]:");
		JLabel text2 =new JLabel("[user]:");
		add(text);
		text.setBounds(140,4,80,20);
		text.setForeground(new Color(90,90,90));
		add(text2);
		text2.setBounds(2,4,80,20);
		text2.setForeground(new Color(90,90,90));

		chatField=new JTextArea();
		chatField.setLineWrap(true);
		chatField.setBackground(new Color(210,210,210));
		chatField.setEnabled(false);

		chatFieldScroll=new JScrollPane(chatField,
										JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
										JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(chatFieldScroll);chatFieldScroll.setBounds(3,30,358,220);

		field1 =new JTextField(20);
		field1.setBackground(new Color(90,90,90));
		field1.setForeground(new Color(255,255,255));
		field1.addKeyListener(new enterKeyListener(this));
		add(field1);field1.setBounds(0,246,364,28);

		Thread thread_a =new Thread(this);
		thread_a.start();
	}

	public void run() {
			try (ServerSocket clientServer = new ServerSocket(9090)) {
				Socket client;
				shippingDataPackage receivedPackage;
				while (true) {
					client = clientServer.accept();

                    ObjectInputStream entryFlow = new ObjectInputStream(client.getInputStream());
                    receivedPackage = (shippingDataPackage)entryFlow.readObject();

                    if (!receivedPackage.getMessage().equals("online")) {
                        shippingDataPackage finalReceivedPackage = receivedPackage;
                        SwingUtilities.invokeLater(() -> chatField.append("\n[" + finalReceivedPackage.getNick() + "]:" + finalReceivedPackage.getMessage()));
                    }
                    if (receivedPackage.getMessage().equals("online")) {
                        ArrayList<String> IpsMenu = receivedPackage.getIps();
                        SwingUtilities.invokeLater(() -> {
                            ip.removeAllItems();
                            for (String z : IpsMenu) ip.addItem(z);
                        });
                    }
                }
        } catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			// Optionally display an error dialog to the user
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + e.getMessage());
		}
	}
	private class sendTxt extends Component implements ActionListener {
		public sendTxt(panelFrameClient panel) {
			this.panel = panel;
		}
		public void actionPerformed(ActionEvent e) {
			String selectedIp = (String) panel.ip.getSelectedItem();
			if (selectedIp != null) {
				try {
					Socket ticketDataEntry = new Socket(selectedIp, 9999);
					shippingDataPackage dataToSend = new shippingDataPackage();

					shippingDataPackage.setNick(userNick);
					dataToSend.setIp(selectedIp);
					dataToSend.setMessage(field1.getText());
					dataToSend.setClientId(clientId);

					ObjectOutputStream dataPackage = new ObjectOutputStream(ticketDataEntry.getOutputStream());
					dataPackage.writeObject(dataToSend);
					dataPackage.flush(); // Ensure data is sent before closing the socket
					chatField.append("\n[" + userNick + "]:" + field1.getText());

					ticketDataEntry.close();

				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "connection error");
                }
			}else {
				JOptionPane.showMessageDialog(null, "please, select an IP before *-*");
			}
			panel.field1.setText("");
		}
		private final panelFrameClient panel;
		private String clientId;

	}
	private class enterKeyListener implements KeyListener {
		public enterKeyListener(panelFrameClient panel) {this.panel = panel;}
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendTxt sendTextEvent = new sendTxt(panel);
				sendTextEvent.actionPerformed(null);
			}
		}
		public void keyReleased(KeyEvent e){}
		public void keyTyped(KeyEvent e){}
		private final panelFrameClient panel;
	}
	private final JTextField field1;
	private final JComboBox ip;
	private final JLabel nick;
	private final JTextArea chatField;
	private final JScrollPane chatFieldScroll;
}
