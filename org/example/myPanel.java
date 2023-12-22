package org.example;

import org.ietf.jgss.GSSContext;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Random;
public class myPanel extends JPanel implements KeyListener {
    //Image logs;  framtida implementering


    // Image variabler
    Image backgroundImage;
    Image playerImage;

    Image projectileImage;
    Image pileImage;
    // träd
    ImageIcon treeImage;


    BufferedImage altarImage;


    // Array som håller olika graphics baserat på väder
    ArrayList<BufferedImage> backgroundArray = new ArrayList<>();
    // ej implementerad ännu
    ArrayList<BufferedImage> projectileArray = new ArrayList<>();

    // Array som visar utveckling av hus/altare/antal träd i inventory
    ArrayList<BufferedImage> pileArray = new ArrayList<>();
    ArrayList<BufferedImage> altarArray = new ArrayList<>();
    ArrayList<BufferedImage> logArray = new ArrayList<>();


    // träd array här med som visar träd växa igen över tid


    // Storlek på frame = skärmens storlek, hämtar in igen för att kunna använda dimensioner
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // Storlek på höjd/bredd
    double screenRow = screenSize.getWidth();
    double screenCol = screenSize.getHeight();

    // på/av knapp för att kunna hugga ved, bygga, laga altare.
    boolean choppingWood = false;
    boolean building = false;


    // Spelare settings

    // spelare storlek
    int playerWidth = (int) (screenRow * 0.2);
    int playerHeight = (int) (screenCol * 0.3);

    // spelare hastighet
    int playerSpeed = 20;


    // om denna slås på kan spelaren vara odödlig tillfälligt (framtidga implementering)
    Boolean shielding = false;

    // 0 på X linje
    int playerX;

    // sätter spelaren längst ned i vänstra hörnet som startpunkt
    int playerY = (int) screenCol - playerHeight;


    // Träd / ved

    // Storlek
    int treeHeight = (int) ((int) screenCol * 0.6);
    // 20% av skärmbredd
    int treeWidth = (int) ((int) screenRow * 0.4);

    // håller ordning på antal träd samlat
    int treeScore = 0;

    // träd koordinater
    // längst ned på x linjen  ----  tar totala värdet av hela x linjen minus storleken på bilden så att den placeras i absolut högra hörnet
    int treeX = (int) screenRow - treeWidth;
    // längst ned på y linjen -- samma princip som ovan , tar absoluta värdet av Y linjen - höjden på objekt
    int treeY = (int) screenCol - treeHeight;


    // byggplats
    int pileIndex = 0;
    int pileHeight = (int) (screenCol * 0.2);
    int pileWidth = (int) (screenRow * 0.2);

    // sätter den på mitten av x
    int pileX = (int) (((int) screenRow * 0.5) - (pileWidth * 0.5));
    // längst ned på y linjen
    int pileY = (int) screenCol - pileHeight;


    // om träd är levande eller ej - används för att reglera mängd material som kan samlas från träd
    boolean treeAlive = true;
    boolean sacrificeAltar = false;


    // projektil koordinater

    int projectileX = 0;
    int projectileY = -700; // fixa senare

    int projectileSpeed = 10;


    // Altare
    int altarHeight = (int) ((int) screenCol * 0.4);
    int altarY = (int) (screenCol - altarHeight);
    int altarX = 0;
    int altarIndex;

    // Håller koll på material (gör om till mätare)
    JLabel treeScoreLabel = new JLabel(String.valueOf(treeScore));


    public myPanel() throws IOException {


        //Bakgrunder
        BufferedImage frozenBackground = ImageIO.read(new File("org/example/snowyLandscapeOne.png"));
        BufferedImage sunnyBackground = ImageIO.read(new File("org/example/sunnyLevel.jpg"));
        BufferedImage stormyBackground = ImageIO.read(new File("org/example/stormyLevel.jpg"));
        BufferedImage stormGodBackground = ImageIO.read(new File("org/example/stormGodNewer.png"));
        backgroundArray.add(frozenBackground);
        backgroundArray.add(sunnyBackground);
        backgroundArray.add(stormyBackground);
        backgroundArray.add(stormGodBackground);


        BufferedImage firstHouse = ImageIO.read(new File("org/example/firstHouse.png"));
        BufferedImage secondHouse = ImageIO.read(new File("org/example/secondHouse.png"));
        BufferedImage thirdHouse = ImageIO.read(new File("org/example/thirdHouse.png"));
        BufferedImage fourthHouse = ImageIO.read(new File("org/example/fifthHouse.png"));
        BufferedImage fifthHouse = ImageIO.read(new File("org/example/sixthHouse.png"));
        pileArray.add(firstHouse);
        pileArray.add(secondHouse);
        pileArray.add(thirdHouse);
        pileArray.add(fourthHouse);
        pileArray.add(fifthHouse);

        // altare bilder
        BufferedImage firstAltar = ImageIO.read(new File("org/example/altar.png"));
        BufferedImage secondAltar = ImageIO.read(new File("org/example/steg1Altare.png"));
        BufferedImage thirdAltar = ImageIO.read(new File("org/example/steg4Altare.png"));
        BufferedImage fourthAltar = ImageIO.read(new File("org/example/steg5Altare.png"));


        altarArray.add(firstAltar);
        altarArray.add(secondAltar);
        altarArray.add(thirdAltar);
        altarArray.add(fourthAltar);

        // genererar bana baserat på väder
        generateLevel();


/*          framtida implementering - ska utöka antal logs för varje 10 trä:
        //logs
        BufferedImage logOne = ImageIO.read(new File("org/example/log1.png"));
        BufferedImage logTwo = ImageIO.read(new File("org/example/log2.png"));
        BufferedImage logThree = ImageIO.read(new File("org/example/log3.png"));
        logArray.add(logOne);
        logArray.add(logTwo);
        logArray.add(logThree);
*/




        // Altar som hela tiden blir sämre över tiden, om spelaren inte lagar det kommer en arg gud skicka ner instantdeath
        startAltar();

        // Spelare
         playerImage = new ImageIcon("org/example/playerStatic.jpg").getImage();

         // Altar
         altarImage = altarArray.getFirst();

         // material att samla
         treeImage = new ImageIcon("org/example/trädSunny.png");

         // projektil array ska vara här
         projectileImage = new ImageIcon(("org/example/lightningBolts.png")).getImage();
         pileImage = pileArray.getFirst();

         setFocusable(true);
        addKeyListener(this);

        //TreeScoreLabel  - skapa en metod som uppdaterar
        treeScoreLabel.setForeground(Color.WHITE);
        add(treeScoreLabel);

     }



     // ritar alla objekt i frame
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;


        // bakgrund först - har skärmens höjd/bredd
        graphics2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); //bakgrund
        // träd
        g.drawImage(treeImage.getImage(), treeX, treeY, treeWidth, treeHeight, null);
        // altar
        g.drawImage(altarImage, altarX, altarY, playerWidth, altarHeight, this);
        // hög/hus
        g.drawImage(pileImage, pileX, pileY, pileWidth, pileHeight, this);
        // spelare
        g.drawImage(playerImage, playerX, playerY, playerWidth, playerHeight, this);
        // projektil
        g.drawImage(projectileImage, projectileX, projectileY, getWidth(), (int) (getHeight() * 0.2), null);
        //g.drawImage(logs, 0, 0, playerWidth, playerHeight, this);

        // Rektanglar som används för obstruction handling och intersects
        Rectangle projectileRect = new Rectangle(projectileX, projectileY, getWidth(), (int) (getHeight() * 0.2));
        Rectangle playerRect = new Rectangle(playerX, playerY, playerWidth, playerHeight);
        Rectangle altarRect = new Rectangle(altarX, altarY, playerWidth, playerHeight);
        Rectangle treeRect = new Rectangle(treeX, treeY, treeWidth, treeHeight);
        Rectangle pileRect = new Rectangle(pileX, pileY, pileWidth, pileHeight);




        // Obstruction handling

        // sänker index värdet på altar array så den återställer
        if (playerRect.intersects(altarRect) && sacrificeAltar){
            altarIndex = 0;
            treeScore = 0;//fixa
            treeScoreLabel.setText(String.valueOf(treeScore));
            repaint();
        }



        // om huset är nästan färdigbyggt kan spelaren skyddas intuti
        if (playerRect.intersects(pileRect) && building){
            // ökar index med 1 tills building = avstängt (inget mer trä)
                pileIndex++;
                treeScore--;
               // System.out.println("Building house!   " +" tree     "  + treeScore + " pile     "   + pileIndex);
                // ökar index med 1 vart 5 sekund och hämtar varje bild tills det inte finns kvar mer i listan
                if (pileIndex < pileArray.size()){
                    pileImage = pileArray.get(pileIndex);
                    // öka bounds, öka storlek
            } else {
                System.out.println("Not enough wood");
                building = false;
            }


//***********WORK IN PROGRESSS*************************
           /* buildingMethod();
            pileIndex++;
            treeScore--;*/
            // om spelaren har mer än 3 trä, kan de bygga på plats
           /* if (building) {  //fixa så building bygger - ju längre denna mer desto mer byggs

                // stannar vid mindre än 3
            } else {
                lessTreeTimer.stop();
            }*/
        }//***********WORK IN PROGRESSS*************************


        // om spelaren blir träffad av attack och är skyddad
        if (playerRect.intersects(projectileRect) && !shielding) {
            playerDeath();
        }


        // interaktion med träd - endast möjligt om träd är aktivt för att förhindra fusk

            if (playerRect.intersects(treeRect) && treeAlive && choppingWood) {

                playerX = playerX - playerSpeed;

                // ökar antal trä samlade
                treeScore++;
                System.out.println(treeScore);
                //uppdaterar trä ikon (framtida implementering)
                treeScoreLabel.setText(String.valueOf(treeScore)); //byt ut till metod

                // om träscore blir 5 ( max antral samlat ) dör träd i 20 sek
                if (treeScore > 10 && treeAlive){
                    treeAlive  = false;
                    resetTree();
                    treeImage = new ImageIcon((String) null);
                    repaint();
                }
            }


            //************WORK IN PROGRESS***********************
             /*   if (treeScore > 10) {
                    treeImage = new ImageIcon("org/example/treeChopped.psd");
                    treeAlive = false;
                    treeTimer.start();
                } else {
                    moreTree.stop();
                }*/

        // framtida sköldmekanik:
       /* if (shielding) {
            Rectangle buildRect = new Rectangle(playerX + 10, playerY + 10, playerWidth + 10, playerHeight + 10);
            if(buildRect.intersects(pileRect)){
                shielding = false;
                lessTreeTimer.start();
            }
        }*/

     /*   // Uppdaterar antal trä spelaren har i inventory
        //kolla sen
        int treeIndex = 0;
        // kollar modulus. För varje 10 trä, uppdateras tree index med 1 ((uppdaterar bilder)
        if (treeScore % 10 == 0){
             treeIndex++;

           *//* // ökar index med 1 vart 5 sekund och hämtar varje bild tills det inte finns kvar mer i listan
            if (treeIndex < logArray.size()){
                logs = logArray.get(treeIndex);
            }*/

        //************WORK IN PROGRESS***********************
        }


    // används ej

    // keyListeners för rörelser
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        // hämtar tryckt tangent
         int keyCode = e.getKeyCode();


    if(keyCode == KeyEvent.VK_D && playerSpeed > 0){
        // adderar spelarens hastighet med position
        playerX = playerX + playerSpeed;
        playerImage = new ImageIcon("org/example/playerRunningTwo.png").getImage();
        repaint();
    }
        if(keyCode == KeyEvent.VK_A&& playerSpeed > 0){
            playerX = playerX - playerSpeed;
            playerImage = new ImageIcon("org/example/playerRunningLeftTwo.png").getImage();
            repaint();
        }


        // bygg funktion
        if(keyCode == KeyEvent.VK_B){
            // om spelaren har mer än 10 trä kan de bygga
            if (treeScore >= 10){
                // sätter på building
                building = true;
                //System.out.println("building      " + "treeScore     " + treeScore + "pileindex      "+ pileIndex); //animation
                treeScore--;
                treeScoreLabel.setText(String.valueOf(treeScore)); // byt ut mot optimerad lösning

            }
        }
        // hugger ved
        if(keyCode == KeyEvent.VK_C){
            playerImage = new ImageIcon("org/example/choppingWood.png").getImage();
            repaint();
            choppingWood = true;

            // lagar altare
        }
        if(keyCode == KeyEvent.VK_R){
            playerImage = new ImageIcon("org/example/choppingWood.png").getImage();
            repaint();
            sacrificeAltar = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_D && playerSpeed > 0){
            playerX = playerX + playerSpeed;
            playerImage = new ImageIcon("org/example/playerStandingTrue.png").getImage();
            repaint();
        }

        if(keyCode == KeyEvent.VK_A && playerSpeed > 0){
            playerX = playerX - playerSpeed;
            playerImage = new ImageIcon("org/example/playerStandingTrue.png").getImage();
            repaint();
        }
        if(keyCode == KeyEvent.VK_B){
            building = false;
        }
        if(keyCode == KeyEvent.VK_C){
            playerImage = new ImageIcon("org/example/playerStandingTrue.png").getImage();
            repaint();
            choppingWood = false;
        }
        if(keyCode == KeyEvent.VK_R){
            playerImage = new ImageIcon("org/example/choppingWood.png").getImage();
            repaint();
            sacrificeAltar = false;
        }
    }


    // Backup ifall api krånglar
    public void weatherBackground() {
        Random random = new Random();
        int randomBackground = random.nextInt(backgroundArray.size());
        backgroundImage = new ImageIcon(backgroundArray.get(randomBackground)).getImage();
        repaint();
        System.out.println("Api invalid");

    }
    public void generateLevel(){

        // Genererar en random plats i världen (random väder)
        // framtida implementering är att denna ska kallas en gång vart 10 min och ändra väder dynamiskt
        // för att väder ska påverka spelet mer
        Random newRandom = new Random();
        double randomLat = -90.0 + (90.0 - (-90.0)) * newRandom.nextDouble();
        double randomLon = -180.0 + (180.0 - (-180.0)) * newRandom.nextDouble();


        HttpClient client = HttpClient.newHttpClient();

        // HTTP builder
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + randomLat + "&lon=" + randomLon + "&appid=51b63e86e7c31d25c02aa8899720bc20"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                weatherAPI weatherAPI = gson.fromJson(response.body(), org.example.weatherAPI.class);

                for (weatherAPI.Weather weather : weatherAPI.getWeather()) {
                    System.out.println("Weather is: " + weather.getMain() + " Description: " + weather.getDescription());
                    // kanske ta temperature istället?

                    if (weather.getMain().equals("Clouds")) {
                        backgroundImage = new ImageIcon(backgroundArray.get(2)).getImage();
                        repaint();
                    }

                    if (weather.getMain().equals("Clear Sky")) {
                        backgroundImage = new ImageIcon(backgroundArray.get(1)).getImage();
                        repaint();
                    }
                    if (weather.getMain().equals("Snow")) {
                        backgroundImage = new ImageIcon(backgroundArray.get(0)).getImage();
                        repaint();
                    }
                    if (weather.getMain().equals("Rain")) {
                        backgroundImage = new ImageIcon(backgroundArray.get(2)).getImage();
                        repaint();
                        System.out.println("Rainy!!");
                    } else {
                        weatherBackground();
                        System.out.println("Random background");
                    }
                    break;
                }
            } else {
                System.out.println("Error - exiting loop");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void resetTree(){
        // Timer som återställer träd
        Timer treeTimer = new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treeAlive = true;
                treeImage = new ImageIcon("org/example/trädSunny.png");
                repaint();
            }
        });
    }
    //förhindrar spelaren från att röra sig vid död. Visar grav.
    public void playerDeath(){
        playerImage = new ImageIcon("org/example/gravePile.png").getImage();
        playerSpeed = 0;
    };

    public void instantKill(){
        // instant kill om spelaren inte är inuti hus

        Timer sendProjectileTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectileY = projectileY + projectileSpeed;
                repaint();
            }
        });
    }
    public void startAltar(){
    }    // sänker altar score i intervaller - ändrar bild med
    Timer altarTimer = new Timer(10000, new ActionListener() {
        int altarIndex = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            altarIndex++;

            // ökar index med 1 vart 5 sekund och hämtar varje bild tills det inte finns kvar mer i listan
            if (altarIndex < altarArray.size()){
                altarImage = altarArray.get(altarIndex);
            }
            // skickar ned instant kill om index når över 3
            if (altarIndex > 3){
                instantKill();
            }
        }
    });}

