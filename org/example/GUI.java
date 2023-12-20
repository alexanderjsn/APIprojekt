package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JFrame {
    GUI() throws IOException {
        myPanel panel = new myPanel();
        panel.weatherBackground();

        setSize(500,500);
        // Storlek på frame = skärmens storlek
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);*/
        this.add(panel);
        setVisible(true);

    }
}
