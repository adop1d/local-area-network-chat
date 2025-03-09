package org.beast.addpersonalb.gui;

import org.beast.addpersonalb.utils.actioColor;
import org.beast.addpersonalb.utils.actioMur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class guiPrincipal extends JFrame {
    public guiPrincipal() {
        Principal panelGui = new Principal();
        setBounds(600,280,600,364);
        setLocationRelativeTo(this);
        setResizable(false);
        add(panelGui);

    }
    public static class Principal extends JPanel {
        public Principal() {
            actioMur.setCustomCursor(this);
            addMouseMotionListener(new actioMur());

            setLayout(null);//No se, pq no?
            setBackground(new Color(70, 70, 70));//para cambiar el color a la monda esta q está muy trite
            JPopupMenu emergent = new JPopupMenu();
            JMenuItem opt1 = new JMenuItem("reload");
            JMenuItem opt2 = new JMenuItem("bermelho");
            JMenuItem opt3 = new JMenuItem("blu");
            opt1.addActionListener(new actioColor("Gris ", new ImageIcon("src/main/java/org/beast/addpersonalb/ico/Icono.jpg"), new Color(70, 70, 70),Principal.this));
            opt2.addActionListener(new actioColor("Bermejo ", new ImageIcon("src/main/java/org/beast/addpersonalb/ico/Icono.jpg"), new Color(103, 19, 27),Principal.this));
            opt3.addActionListener(new actioColor("Celeste grisaceo ", new ImageIcon("src/main/java/org/beast/addpersonalb/ico/Icono.jpg"), new Color(19, 78, 102),Principal.this));
            emergent.add(opt1);
            emergent.add(opt2);
            emergent.add(opt3);
            setComponentPopupMenu(emergent);

            //Oyentes
            //Botones
            //JButton boton6 = new JButton("future");
            JButton boton7 = new JButton(new ImageIcon("src/main/java/org/beast/addpersonalb/ico/SEGREDO.jpg"));
            JButton boton8 = new JButton(new ImageIcon("src/main/java/org/beast/addpersonalb/ico/log1n.jpg"));
            boton5 = new JButton("close");
            //AÑADIR BOTONES
            //add(boton5);boton5.setBounds(506, 220, 85, 20);
            //add(boton6);boton6.setBounds(506, 145, 85, 20);
            add(boton7);boton7.setBounds(576, 306, 20, 20);
            add(boton8);boton8.setBounds(496, 140, 100, 28);
            //añadir botones y oyente
            //boton6.addActionListener(new futureButtonListener());
            boton7.addActionListener(new personalButtonListener());
            boton8.addActionListener(new chatButtonListener());

            //instancia de Imagen - TryCatch de la IOException (obligatorio)
            try {imagen = ImageIO.read(new File("src/main/java/org/beast/addpersonalb/ico/RADAR3.png"));}
            catch (IOException e) {System.out.println("not found");}}

        public void paintComponent(Graphics g) {
            //imagen
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, null);
            //presentación
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.LIGHT_GRAY);
            Font font2 = new Font("Courier", Font.BOLD, 12);
            g2.setFont(font2);
            g2.drawString(":3", 560, 320);
            //fin
        }
        private Image imagen;

        //Lo dejé de usar por que lo consideré muy feo y ya, lo dejo de ejemplo
        private JMenuBar getMenus() {
            JMenu menuArch = new JMenu("File");
            JMenu menuPref = new JMenu("Preferences");
            JMenu menuOpc = new JMenu("Settings");
            JMenu menuCol = new JMenu("Color");

            menuCol.add(new actioColor("Bermejo ", new ImageIcon("src/main/java/org/beast/addpersonalb/ico/Icono.jpg"), new Color(103, 19, 27),Principal.this));
            menuCol.add(new actioColor("Celeste grisaceo ", new ImageIcon("src/main/java/org/beast/addpersonalb/ico/Icono.jpg"), new Color(19, 78, 102),Principal.this));
            menuCol.add(new actioColor("Gris ", new ImageIcon("src/main/java/org/beast/addpersonalb/ico/Icono.jpg"), new Color(70, 70, 70),Principal.this));
            menuOpc.add(menuCol);
            menuPref.add(menuOpc);

            JMenuBar menos = new JMenuBar();
            menos.add(menuArch);
            menos.add(menuPref);

            menos.setBounds(0, 0, 600, 20);
            return menos;
        }
        //                          Clases oyentes de distintas ventanas

        //#1 chat
        private class chatButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                guiLoginFrame letteraFrameBTN = new guiLoginFrame(); // de parametro pasar a futuro el boton 5
                letteraFrameBTN.setVisible(true);
                letteraFrameBTN.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        }
        //#2 mensaxe
        private class personalButtonListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                guiPersonalSharing personalFrameBTN = new guiPersonalSharing();//de parametro pasar a futuro el boton 5
                personalFrameBTN.setVisible(true);
                personalFrameBTN.setResizable(false);
                personalFrameBTN.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        }
        //#3  futuro
        private class futureButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                guiMethods methodFrame = new guiMethods();//de parametro pasar a futuro el boton 5
                methodFrame.setVisible(true);
                methodFrame.setResizable(false);
                methodFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        }

                //                               El boton de la salvación:
                                                    JButton boton5;
                //Ahora inutil... No recuerdo bien por qué lo puse en primer lugar...
    }
}
