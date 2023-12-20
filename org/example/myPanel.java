package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;
public class myPanel extends JPanel implements KeyListener {

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
    /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // Storlek på höjd/bredd
    double screenRow = screenSize.getWidth();
    double screenCol = screenSize.getHeight();*/
    int screenRow = 500;
    int screenCol = 500;

    // spelare storlek
    int playerWidth = (int) (screenRow * 0.1);
    int playerHeight = (int) (screenCol * 0.2);

    // spelare hastighet

    int playerSpeed = 60;
    int playerJump = 100;


    int playerX;
    int playerY;




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

        setFocusable(true);
        addKeyListener(this);



        /*// Mark
        JPanel groundPanel = new JPanel();
        // 30% längd, 20% höjd av skärmen
        double groundRow = screenRow * 0.5;
        double groundCol = screenCol * 0.2;
        groundPanel.setPreferredSize(new Dimension((int) groundRow, (int) groundCol));
        add(groundPanel, BorderLayout.SOUTH);*/


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


        graphics2D.drawImage(backgroundImage, 0,0, getWidth(),getHeight(), this); //bakgrund
        g.drawImage(playerImage, playerX, playerY,playerWidth,playerHeight,null); // mark
        /*g.drawImage(); // spelare
        g.drawImage(); // fiende
        g.drawImage(); // projektil*/

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
         int keyCode = e.getKeyCode();
    if(keyCode == KeyEvent.VK_D){
        playerX = playerX + playerSpeed;
        playerImage = new ImageIcon("org/example/playerRun.png").getImage();
        repaint();
    }
        if(keyCode == KeyEvent.VK_A){
            playerX = playerX - playerSpeed;
            playerImage = new ImageIcon("org/example/playerLeft.png").getImage();
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_D){
            playerX = playerX + playerSpeed;
            playerImage = new ImageIcon("org/example/playerStatic.png").getImage();
            repaint();
        }

        if(keyCode == KeyEvent.VK_A){
            playerX = playerX - playerSpeed;
            playerImage = new ImageIcon("org/example/playerStaticLeft.png").getImage();
            repaint();
        }

    }
}