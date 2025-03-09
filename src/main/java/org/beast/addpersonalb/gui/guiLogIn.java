package org.beast.addpersonalb.gui;

import org.beast.addpersonalb.guiLocalServer.chatClient;
import org.beast.addpersonalb.utils.actioMur;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class guiLoginFrame extends JFrame {
    public guiLoginFrame(){
        setBounds(600,200,280,180);
        setLocationRelativeTo(this);
        loginPanel aPanel =new loginPanel();
        add(aPanel);

        setVisible(true);
        setResizable(true);

        disposeAction Actio=new disposeAction();
        //close.addActionListener(Actio);
    }
    private class disposeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
class loginPanel extends JPanel{
    public loginPanel() {

        actioMur.setCustomCursor(this);
        addMouseMotionListener(new actioMur());

        setLayout(null);
        setBackground(new Color(60,60,60));
        setBackground(new Color(60,60,60));
        setBounds(0,0,600,60);

        resultant =new JLabel();

        JLabel text1 =new JLabel("password");
        JLabel text2 =new JLabel("user");
        text1.setForeground(new Color(234,234,234));
        text2.setForeground(new Color(234,234,234));

        textFieldLog = new JTextField("",10);

        passwordReport Evvt1=new passwordReport();

        passKey =new JPasswordField(10);
        passKey.getDocument().addDocumentListener(Evvt1);

        emailField reportLogin =new emailField();
        JButton sendButton =new JButton(new ImageIcon("src/main/java/org/beast/addpersonalb/ico/log1n.jpg"));
        sendButton.addActionListener(reportLogin);

        text2.setBounds(56,22,80,20);
        text1.setBounds(30,50,80,20);

        textFieldLog.setBounds(90,20,130,30);
        passKey.setBounds(90,48,130,30);
        resultant.setBounds(80,80,180,40);
        sendButton.setBounds(95,120,80,28);

        textFieldLog.setForeground(new Color(200,200,200));
        passKey.setForeground(new Color(200,200,200));
        textFieldLog.setBackground(new Color(90,90,90));
        passKey.setBackground(new Color(90,90,90));
        add(text2);
        add(textFieldLog);
        add(text1);
        add(passKey);
        add(sendButton);
        add(resultant);
    }

    private class passwordReport implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            char[] contrasegna;
            contrasegna = passKey.getPassword();
            if(contrasegna.length<4|| contrasegna.length>16){
                passKey.setBackground(new Color(206,100,100));
            }else{
                passKey.setBackground(new Color(90,90,90));
            }if(contrasegna.length == 0){
                passKey.setBackground(new Color(90,90,90));
            }
        }
        public void removeUpdate(DocumentEvent e) {
            char[] contrasegna;
            contrasegna = passKey.getPassword();
            if(contrasegna.length<4|| contrasegna.length>16){
                passKey.setBackground(new Color(206,100,100));
            }else{
                passKey.setBackground(new Color(90,90,90));
            }if(contrasegna.length == 0){
                passKey.setBackground(new Color(90,90,90));
            }
        }
        public void changedUpdate(DocumentEvent e) {
        }
    }
    JPasswordField passKey;

    private class emailField implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String email = textFieldLog.getText().trim();
            if (email.length() <= 3||email.length()>=100) {
                resultant.setForeground(new Color(234, 234, 234));
                resultant.setText("incorrecto *-*");
                textFieldLog.setText("");
                passKey.setText("");
                try {
                    throw new LongitudMailError("longitud inaceptable, supera o no entra en limites de caracteres en uso");
                } catch (LongitudMailError ex) {
                    throw new RuntimeException(ex);
                }
            }
            onLoginSuccess();
            /*else {
                for (int i = 0; i < email.length(); i++) {
                    if (email.charAt(i) == '@') {
                        correcto++;
                    }
                    if (email.charAt(i) == '.') {
                        correcto++;
                    }
                }
                if (correcto != 2) {
                    resultant.setText("incorrecto U.U");
                } else {
                    System.out.println("email correcto");
                    resultant.setText("email iniciado ¬¬");

                }
                textFieldLog.setText("");
                passKey.setText("");
                resultant.setForeground(new Color(234, 234, 234));

            }*/
        }
        private void onLoginSuccess() {
            String userNick = textFieldLog.getText();
            ((JFrame) textFieldLog.getTopLevelAncestor()).dispose();

            chatClient client = new chatClient(userNick);
            client.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            client.setVisible(true);
        }
    }
    private final JTextField textFieldLog;
    private final JLabel resultant;

}
//IGNORAR, pequeño detalle solo por si las moscas ¬¬
class LongitudMailError extends Exception{
    public LongitudMailError(String MSJErr){super(MSJErr);}}


