package org.example;

import javax.swing.*;
import java.awt.*;
public class GUI extends JFrame {

    public GUI(){


        // Storlek på frame = skärmens storlek
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Storlek på höjd/bredd
        double screenRow = screenSize.getWidth();
        double screenCol = screenSize.getHeight();
        setSize(screenSize);

        setVisible(true);
    }
}