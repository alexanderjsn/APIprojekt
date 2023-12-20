package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
public class myPanel extends JPanel {

    Image backgroundImage;
    Image playerImage;

    Image projectileImage;
    Image groundImage;

    Image enemyImage;

    ArrayList<BufferedImage> backgroundArray = new ArrayList<>();
    ArrayList<BufferedImage> projectileArray = new ArrayList<>();
    ArrayList<BufferedImage> groundArray = new ArrayList<>();
    ArrayList<BufferedImage> playerArray = new ArrayList<>();


    // Storlek på frame = skärmens storlek
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // Storlek på höjd/bredd
    double screenRow = screenSize.getWidth();
    double screenCol = screenSize.getHeight();

    // spelare storlek
    int playerWidth = (int) (screenRow * 0.1);
    int playerHeight = (int) (screenCol * 0.2);

    // positioner på banan

    //högra hörnan (nere)




     public myPanel() throws IOException {


        //Bakgrunder
        BufferedImage frozenBackground = ImageIO.read(new File("org/example/snowyLevel.png"));
        BufferedImage sunnyBackground = ImageIO.read(new File("org/example/sunnyLevel.png"));
        BufferedImage stormyBackground = ImageIO.read(new File("org/example/stormyLevel.png"));
        // Finns bättre sätt att hämta nivåer?
        backgroundArray.add(frozenBackground);
        backgroundArray.add(sunnyBackground);
        backgroundArray.add(stormyBackground);

        // Spelare
         playerImage = new ImageIcon("org/example/playerStatic.png").getImage();


        // Mark
        JPanel groundPanel = new JPanel();
        // 30% längd, 20% höjd av skärmen
        double groundRow = screenRow * 0.5;
        double groundCol = screenCol * 0.2;
        groundPanel.setPreferredSize(new Dimension((int) groundRow, (int) groundCol));
        groundPanel.setBackground(Color.black);
        add(groundPanel, BorderLayout.SOUTH);
     }
    // gör sen till en väder baserad background
    public void weatherBackground() {
        Random random = new Random();
        int randomBackground = random.nextInt(backgroundArray.size());
        backgroundImage = new ImageIcon(backgroundArray.get(randomBackground)).getImage();
        repaint();
     }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        // nedre högra hörn - sätts här eftersom frame storlek har beräknats redan nät metod kallad
        int x = 0;
        int y = getHeight() - playerHeight;

        graphics2D.drawImage(backgroundImage, 0,0, getWidth(),getHeight(), this); //bakgrund
        g.drawImage(playerImage, x,y,playerWidth,playerHeight,null); // mark
        /*g.drawImage(); // spelare
        g.drawImage(); // fiende
        g.drawImage(); // projektil*/

    }


}