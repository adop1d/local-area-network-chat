package org.beast.addpersonalb.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class actioColor extends AbstractAction {
    public actioColor(String nom, Icon icono, Color colorBKGD, JComponent componente) {
        putValue(Action.NAME, nom);
        putValue(Action.SMALL_ICON, icono);
        putValue(Action.SHORT_DESCRIPTION, "mudar color a: ");
        putValue("Color", colorBKGD);
        this.componente = componente;
    }
    public void actionPerformed(ActionEvent e) {
        Color c = (Color) getValue("Color");
        componente.setBackground(c);
        System.out.println("apoplexia. " + getValue(SHORT_DESCRIPTION) + getValue(Action.NAME));
    }
    private final JComponent componente;
}

