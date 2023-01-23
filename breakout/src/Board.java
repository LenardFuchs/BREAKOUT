//https://zetcode.com/javagames/breakout/ 10.01.2023

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;


//This is where the magic happens, aka where the logic is written
public class Board extends JPanel {


    private Timer timer;
    private String gameover = "GAME OVER";

    public static HighScoreManager highScoreMngr;

    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private Obstacle[] obstacle;
    private Menu menu = new Menu();
    private Pause pause = new Pause();
    public static State state = State.MENU;
    public static GameLevel currentLevel;



    private int lives = 3;//implement lives in the game
    private static int points = 0;
    private static int finalScore = 0;
    private boolean gamewon;




    public Board() {

        initBoard();
    }

    public void initBoard() {

        //setState(State.MENU);
        setCurrentLevel(GameLevel.LEVEL_1);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        addMouseListener(new MouseInput());
        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
        initHighScoreManager();
        gameInit();



    }

    private void gameInit() { // we create a ball, a paddle and bricks (number of which we set in Commons class)
        gamewon = false;
        bricks = new Brick[Commons.N_OF_BRICKS]; // brick array, length is the number we set in commons
        ball = new Ball();
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 120 + 30, i * 35 + 50); // the value after * is the scale of the img being loaded. // value after is the distance from lef and top borders
                k++;
            }
        }

        switch (currentLevel) {
            case LEVEL_1:
                obstacle = new Obstacle[0];
                //obstacle[0] = new Obstacle(30, 300);
                break;
            case LEVEL_2:
                obstacle = new Obstacle[1];
                obstacle[0] = new Obstacle(30, 300);
                //obstacle[1] = new Obstacle(Commons.WIDTH - 150, 300);
                break;
            case LEVEL_3:
                obstacle = new Obstacle[2];
                obstacle[0] = new Obstacle(30, 300);
                //obstacle[1] = new Obstacle(Commons.WIDTH - 150, 300);
                //obstacle[2] = new Obstacle(Commons.WIDTH /2 -150, 300);
                obstacle[1] = new Obstacle (Commons.WIDTH-150,450);
                break;
        }

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);


        switch(state){
            case MENU:
                //menu.loadImage();
                menu.render(g2d);
                break;

            case INGAME:
                drawObjects(g2d);
                break;

            case PAUSE:
                //drawObjects(g2d);
                pause.render(g);

                break;

            case GAMEOVER:
                gameOver(g2d);
                break;

        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {// draw all the objects of the game ie ( ball, paddle , bricks, drawImage method draws the Sprites
        Image bg;
        var ii = new ImageIcon("breakout/src/resources/bg.png");
        bg = ii.getImage();
        g2d.drawImage(bg,0,0, null);

        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (int i = 0; i < obstacle.length; i++) {
            g2d.drawImage(obstacle[i].getImage(), obstacle[i].getX(),
                    obstacle[i].getY(), obstacle[i].getImageWidth(),
                    obstacle[i].getImageHeight(), this);
        }

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) { // draw if brick is not destroyed

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }

        Font font = new Font("ARIAL", Font.PLAIN, 20);

        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        //g2d.drawString("POINTS:", 30, 800);
        g2d.drawString(Integer.toString(points), 160, 780);
        //g2d.drawString("LIVES:", 630, 800);
        g2d.drawString(Integer.toString(lives), 700, 780);
        g2d.drawString(currentLevel.toString(), 350, 780);
    }

    private void gameOver(Graphics2D g2d) {// draws Game over or Victory
        Image background;

        if (!gamewon){
            var ii = new ImageIcon("breakout/src/resources/gameoversad.png");
            background = ii.getImage();

            g2d.drawImage(background, 0, 0, null);
        } else {
            var ii = new ImageIcon("breakout/src/resources/victory.png");
            background = ii.getImage();

            g2d.drawImage(background, 0, 0, null);
        }

        var font = new Font("Comic Sans", Font.BOLD, 50);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        g2d.drawString(Integer.toString(finalScore), (Commons.WIDTH - fontMetrics.stringWidth(Integer.toString(finalScore))) / 2, 600);


    }

    public static void setState(State s) {
        state = s;
    }

    public static State getState() {
        return state;
    }

    public static void setCurrentLevel(GameLevel level) {
        currentLevel = level;
    }


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            if (state == State.INGAME) {
                paddle.keyReleased(e);
                ball.keyReleased(e);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {


            if (state == State.INGAME) {
                paddle.keyPressed(e);
                ball.keyPressed(e);
            }

            else if (state == State.PAUSE){
                paddle.keyPressed(e);
            }

        }

    }


    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {
        //moves the ball, the paddle. check for possible collisions . then repaint the screen

        if (state==State.INGAME) {
            ball.move();
            if (ball.isLaunched()){
                paddle.move();
                for (int i = 0; i < obstacle.length; i++) {
                    obstacle[i].move();
                }}else{
                ball.aimBall();
                paddle.move();
            }
            checkCollision();
            repaint();

        }
        if (state== State.PAUSE){
            repaint();
        }
        if (state == State.MENU){
            repaint();
            gameInit();
        }
    }

    private void stopGame() {
        switch(currentLevel){
            case LEVEL_1:
                finalScore = points;
                break;
            case LEVEL_2:
                finalScore = points + 1500;
                break;
            case LEVEL_3:
                finalScore = points + 3000;
                break;
        }setState(State.GAMEOVER);
        handleNewHighScore();



        //timer.stop();
    }

    private void restartBall() {


        timer.stop();
        ball = new Ball();
        paddle = new Paddle();
        timer.start();
    }


    private void resetPoints(){
        points = 0;

    }

    private void createHighScores(){
        //scoreTable =

    }



    private void checkCollision() {


        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {  //when the ball hits the bottom,
            lives--; //deduct a life

            if (lives != 0 ) {
                restartBall();

            } else if (lives <= 0) { //no more lives left
                stopGame();  // we stop the game
            }
        }


        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS; i++) {


            if (bricks[i].isDestroyed()) {
                //whenever we destroy a brick
                j++; //add 1 to j
                points = j * 50;

            }

            if (j == Commons.N_OF_BRICKS) { // we check how many bricks are destroyed, if it is equal to initial number of bricks
                switch (currentLevel) {
                    case LEVEL_1:
                        resetPoints();
                        setCurrentLevel(GameLevel.LEVEL_2);
                        gameInit();
                        break;
                    case LEVEL_2:
                        resetPoints();
                        setCurrentLevel(GameLevel.LEVEL_3);
                        gameInit();
                        break;
                    case LEVEL_3:
                        gamewon = true;
                        gameover = "YOU WIN!";
                        stopGame();
                        break;

                }


            }

        }


        if ((ball.getRect()).intersects(paddle.getRect())) {

            SoundHandler.runMusic("breakout/src/resources/Kick.wav");

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            // divide the paddle into parts. these are the cuts of the parts
            int first = paddleLPos + 24;
            int second = paddleLPos + 48;
            int third = paddleLPos + 72;
            int fourth = paddleLPos + 96;

            if (ballLPos < first) { // ball hits the first part of the paddle

                ball.setXDir(-1); // ball moves to the left
                ball.setYDir(-1 ); // ball moves upwards
            }

            if (ballLPos >= first && ballLPos < second) { // ball hits second part of the paddle

                ball.setXDir(-1); // ball moves to the left
                ball.setYDir(-1 *ball.getYDir()); // !!!
            }

            if (ballLPos >= second && ballLPos < third) { // ball hits the third part of the paddle

                ball.setXDir(0); // ball doesn't move left or right
                ball.setYDir(-1 ); // ball moves upwards
            }

            if (ballLPos >= third && ballLPos < fourth) { // ball hits fourth part of the paddle

                ball.setXDir(1); // ball  moves to the right
                ball.setYDir(-1 * ball.getYDir()); // !!!
            }

            if (ballLPos > fourth) { // ball hits the fifth part of the paddle

                ball.setXDir(1); // ball moves to the right
                ball.setYDir(-1 ); // ball moves upwards
            }
        }




        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if ((ball.getRect()).intersects(bricks[i].getRect())) { //check if the ball hits a brick

                int ballLeft = (int) ball.getRect().getMinX(); // left side of the ball
                int ballHeight = (int) ball.getRect().getHeight(); //height of the ball
                int ballWidth = (int) ball.getRect().getWidth(); // width of the ball
                int ballTop = (int) ball.getRect().getMinY(); // top side of the ball

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop); //point right side of the ball
                var pointLeft = new Point(ballLeft - 1, ballTop); //point left  side of the ball
                var pointTop = new Point(ballLeft, ballTop - 1); // point top side of the ball
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1); // point bottom part of the ball

                if (!bricks[i].isDestroyed()) {

                    SoundHandler.runMusic("breakout/src/resources/Puff.wav");

                    if (bricks[i].getRect().contains(pointRight)) { // brick is hit by the right side of the ball; brick is hit from the left side

                        ball.setXDir(-1); //ball goes left

                    } else if (bricks[i].getRect().contains(pointLeft)) { // brick is hit by  the left side of the ball ; brick  is hit from the right

                        ball.setXDir(1); //ball goes right
                    }

                    if (bricks[i].getRect().contains(pointTop)) { // brick is hit by the top of the ball;  brick is hit fromm the bottom

                        ball.setYDir(1);  //ball goes down

                    } else if (bricks[i].getRect().contains(pointBottom)) { // brick is hit by the bottom part of the ball ; brick is hit  from the top

                        ball.setYDir(-1);  //ball goes up
                    }

                    bricks[i].setDestroyed(true); // brick state is set to destroyed
                }

            }
        }

        for (int i = 0; i < obstacle.length; i++) {

            if ((ball.getRect()).intersects(obstacle[i].getRect())) { //check if the ball hits a brick

                int ballLeft = (int) ball.getRect().getMinX(); // left side of the ball
                int ballHeight = (int) ball.getRect().getHeight(); //height of the ball
                int ballWidth = (int) ball.getRect().getWidth(); // width of the ball
                int ballTop = (int) ball.getRect().getMinY(); // top side of the ball

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop); //point right side of the ball
                var pointLeft = new Point(ballLeft - 1, ballTop); //point left  side of the ball
                var pointTop = new Point(ballLeft, ballTop - 1); // point top side of the ball
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1); // point bottom part of the ball

                if (obstacle[i].getRect().contains(pointRight)) { // brick is hit by the right side of the ball; brick is hit from the left side

                    ball.setXDir(-1); //ball goes left

                } else if (obstacle[i].getRect().contains(pointLeft)) { // brick is hit by  the left side of the ball ; brick  is hit from the right

                    ball.setXDir(1); //ball goes right
                }


                if (obstacle[i].getRect().contains(pointTop)) { // brick is hit by the top of the ball;  brick is hit fromm the bottom

                    ball.setYDir(1);  //ball goes down

                } else if (obstacle[i].getRect().contains(pointBottom)) { // brick is hit by the bottom part of the ball ; brick is hit  from the top

                    ball.setYDir(-1);  //ball goes up
                }



            }
        }




    }

    private void initHighScoreManager()
    {
        highScoreMngr = new HighScoreManager();

        try
        {
            highScoreMngr.loadHighScores();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "An error occured while trying to load the high score file (" + highScoreMngr.HIGH_SCORE_FILE_PATH + ").\n\nError Message: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleNewHighScore()
    {
        if (finalScore>0)
        {
            int rank = highScoreMngr.getHighScoreRank(finalScore);

            if (rank != -1)
            {
                String name;
                boolean cancelled = false;
                boolean invalidName = false;

                while (true) // Loop forever until a valid name is entered
                {
                    name = JOptionPane.showInputDialog(null, "You achieved a high score! Enter your name to be displayed on the high score board:", "Congratulations", JOptionPane.INFORMATION_MESSAGE);

                    if (name == null) // User pressed cancel
                    {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel? Your high score will not be saved.", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                        if (result == JOptionPane.YES_OPTION)
                        {
                            cancelled = true;

                            break;
                        }
                    }
                    else // User entered data in the text field
                    {
                        if (highScoreMngr.isValidName(name))
                        {
                            break;
                        }
                        else
                        {
                            invalidName = true;
                        }
                    }

                    if (invalidName)
                    {
                        JOptionPane.showMessageDialog(null, "The name you entered is invalid. A valid name cannot contain \"" + highScoreMngr.getDataDelimiter() + "\" and also it must be between " + highScoreMngr.MIN_NAME_LENGTH + " and " + highScoreMngr.MAX_NAME_LENGTH + " characters in length.", "Error", JOptionPane.ERROR_MESSAGE);

                        invalidName = false; // Reset this for the next iteration of the while loop
                    }
                } // End while

                if (!cancelled)
                {
                    highScoreMngr.updateHighScore(rank, name, finalScore);

                    try
                    {
                        highScoreMngr.saveHighScores();

                        showHighScoreWindow();
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "An error occured while trying to save the high score file (" + highScoreMngr.HIGH_SCORE_FILE_PATH + ").\n\nError Message: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }


    public static void showHighScoreWindow() {

        HighScoresWindow hsWindow = new HighScoresWindow(new Board(), highScoreMngr);
        hsWindow.setVisible(true);
    }
}
