package org.beast.addpersonalb.gui;

import org.beast.addpersonalb.utils.actioMur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guiMethods extends JFrame {
    public guiMethods() {
        setBounds(560,320, 450, 300);
        setLocationRelativeTo(this);

        methods pane = new methods();
        add(pane);
        closeActio closeListener = new closeActio();
        //closeButton.addActionListener(closeListener);
    }

    private class closeActio implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    public class methods extends JPanel {
        public methods() {
            actioMur.setCustomCursor(this);
            addMouseMotionListener(new actioMur());

        }
    }
}