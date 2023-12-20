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

        JPanel groundPanel = new JPanel();
        double groundRow = screenRow * 0.3;
        double groundCol = screenCol * 0.2;
        groundPanel.setPreferredSize(new Dimension((int) groundRow, (int) groundCol));
        groundPanel.setBackground(Color.black);
        add(groundPanel,BorderLayout.SOUTH);

        setVisible(true);
    }
}