package org.beast.addpersonalb.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class actioMur implements MouseMotionListener{
    public static void setCustomCursor(JComponent window) {
        Toolkit murToolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = murToolkit.getImage("src/main/java/org/beast/addpersonalb/ico/sight.png");

        Point hotSpot = new Point(0, 0); // Punto caliente del cursor (normalmente la esquina superior izquierda)
        Cursor customCursor = murToolkit.createCustomCursor(cursorImage, hotSpot, "cursor");

        window.setCursor(customCursor);
    }
    public void mouseDragged(MouseEvent e){
            System.out.println("arrastrando en: "+"x:"+e.getX()+"y:"+e.getY());
    }
    public void mouseMoved(MouseEvent e)  {
        //System.out.println("x:"+e.getX()+"y:"+e.getY());  //ESTA MIERDA ME ENCANTA, PUEDO VER LA POSICION
                                                            //DEL MOUSE EN TIEMPO REAL MEDIANTE CONSOLA
    }
}
