package org.beast.addpersonalb.gui;

import org.beast.addpersonalb.utils.actioMur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guiPersonalSharing extends JFrame {
    public guiPersonalSharing(){
        setTitle("vete u.u");
        setBounds(600,280,180,100);
        setLocationRelativeTo(this);

        PersonalSharing panel = new PersonalSharing();
        add(panel);
        setVisible(true);

        MCAR ButtFechar =new MCAR();
        //MCAR.addActionListener(ButtFechar);
    }
    private class MCAR implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
class PersonalSharing extends JPanel{
    public PersonalSharing(){
        actioMur.setCustomCursor(this);
        addMouseMotionListener(new actioMur());

        setBackground(new Color(60,60,60));
        setLayout(null);

        JLabel text =new JLabel(">>>");text.setBounds(0,20,100,20);
        JLabel text1 =new JLabel("<<<");text1.setBounds(150,20,100,20);
        JLabel text2 =new JLabel("(ﾐ^ᆽ^ﾐ)");text2.setBounds(65,50,100,20);
        text2.setForeground(new Color(200,200,200));
        text1.setForeground(new Color(200,200,200));
        text.setForeground(new Color(200,200,200));

        JPopupMenu Description = new JPopupMenu();
        JMenuItem a1 = new JMenuItem("no...");
        Description.add(a1);

        JButton Boton = new JButton("✖ no tocar ✖");Boton.setBounds(40,20,100,20);
        Boton.addActionListener(new Boton());

        add(Boton);
        add(text);
        add(text1);
        add(text2);
    }
    private class Boton implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showConfirmDialog(
                    PersonalSharing.this,
                    "te amo ^^ ♥",
                    "(ﾐ●ﻌ●ﾐ)",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
        }
    }
}
