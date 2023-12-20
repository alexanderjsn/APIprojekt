package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class GUI extends JFrame {

    Image backgroundImage;
    Image playerImage;

    Image projectileImage;
    Image groundImage;

    Image enemyImage;

    public GUI(){


        // Storlek på frame = skärmens storlek
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Storlek på höjd/bredd
        double screenRow = screenSize.getWidth();
        double screenCol = screenSize.getHeight();
        setSize(screenSize);


        // Mark
        JPanel groundPanel = new JPanel();
        // 30% längd, 20% höjd av skärmen
        double groundRow = screenRow * 0.3;
        double groundCol = screenCol * 0.2;
        groundPanel.setPreferredSize(new Dimension((int) groundRow, (int) groundCol));
        groundPanel.setBackground(Color.black);
        add(groundPanel,BorderLayout.SOUTH);

            setVisible(true);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D graphics2D = (Graphics2D) g;
        g.drawImage(); //bakgrund
        g.drawImage(); // mark
        g.drawImage(); // spelare
        g.drawImage(); // fiende
        g.drawImage(); // projektil

    }
}