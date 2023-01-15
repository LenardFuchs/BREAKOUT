//https://zetcode.com/javagames/breakout/ 10.01.2023

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.getKeyText;


//This is where the magic happens, aka where the logic is written
public class Board extends JPanel {


    private Timer timer;
    private String gameover = "Game Over";

    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private Obstacle[] obstacle;
    private Menu menu = new Menu();
    //private boolean inGame = true;


    private int lives = 3;//implement lives in the game


    private static int score = 0;
    private static int finalscore = 0;
    public static State state;
    public static GameLevel currentLevel;


    public Board() {

        initBoard();
    }

    private void initBoard() {

        setState(State.MENU);
        setCurrentLevel(GameLevel.Level3);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        addMouseListener(new MouseInput());
        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();

        gameInit();


    }

    private void gameInit() { // we create a ball, a paddle and bricks (number of which we set in Commons class)

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
            case Level1:
                obstacle = new Obstacle[0];
                break;
            case Level2:
                obstacle = new Obstacle[1];
                obstacle[0] = new Obstacle(30, 300);
                //obstacle[1] = new Obstacle(Commons.WIDTH - 150, 300);
                break;
            case Level3:
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


        if (state == State.MENU) {
            menu.render(g);

        } else if (state == State.PAUSE){
            drawObjects(g2d);

            var font = new Font("Comic Sans", Font.BOLD, 18);
            FontMetrics fontMetrics = this.getFontMetrics(font);

            g2d.setColor(Color.BLACK);
            g2d.setFont(font);
            g2d.drawString("GAME PAUSED",
                    (Commons.WIDTH - fontMetrics.stringWidth("GAME PAUSED")) / 2,
                    Commons.WIDTH / 2);






        } else if (state == State.INGAME) {

            drawObjects(g2d);

        } else if (state == State.GAMEOVER) {

            gameOver(g2d);
            //drawObjects(g2d); //shows the bricks when game is over

        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) { // draw all the objects of the game ie ( ball, paddle , bricks, drawImage method draws the Sprites

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

        Font font = new Font("Comic Sans", Font.PLAIN, 11);

        g2d.setFont(font);
        g2d.setColor(Color.blue);
        g2d.drawString("score:", 30, 20);
        g2d.drawString(Integer.toString(score), 80, 20);
        g2d.drawString("lives:", 220, 20);
        g2d.drawString(Integer.toString(lives), 260, 20);
    }

    private void gameOver(Graphics2D g2d) { // draws Game over or Victory

        Rectangle restartButton = new Rectangle(Commons.WIDTH / 2 - 50, 150, 100, 50);

        var font = new Font("Comic Sans", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(gameover,
                (Commons.WIDTH - fontMetrics.stringWidth(gameover)) / 2,
                Commons.WIDTH / 2);
        g2d.drawString("Final Score ", (Commons.WIDTH - fontMetrics.stringWidth("Final Score")) / 2, 230);

        g2d.drawString(Integer.toString(finalscore), (Commons.WIDTH - fontMetrics.stringWidth(Integer.toString(score))) / 2, 270);

        g2d.draw(restartButton);


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
                }}

                checkCollision();
                repaint();

        }
        if (state== State.PAUSE){
            repaint();
        }
    }

    private void stopGame() {

        state = State.GAMEOVER;
        timer.stop();
    }

    private void restartBall() {


        timer.stop();
        ball = new Ball();
        paddle = new Paddle();
        timer.start();
    }

    private void pauseGame(){
        state = State.PAUSE;
        timer.stop();
    }

    private void checkCollision() {

        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {  //when the ball hits the bottom,
            lives--; //deduct a life

            if (lives != 0) { //
                restartBall();

            } else { //no more lives left
                stopGame();  // we stop the game
            }


        }


        for (int i = 0, j = 0; i < Commons.N_OF_BRICKS; i++) {


            if (bricks[i].isDestroyed()) { //whenever we destroy a brick
                j++; //add 1 to j
                score = j * 50;
            }

            if (j == Commons.N_OF_BRICKS) { // we check how many bricks are destroyed, if it is equal to initial number of bricks
                switch (currentLevel) {
                    case Level1:
                        finalscore = finalscore + score;
                        setCurrentLevel(GameLevel.Level2);
                        gameInit();
                        break;
                    case Level2:
                        finalscore = finalscore + score;
                        setCurrentLevel(GameLevel.Level3);
                        gameInit();
                        break;
                    case Level3:
                        finalscore = finalscore + score;
                        gameover = "Victory";
                        stopGame();
                }
                //gameover = "Victory"; // we win

                //new Board();
            }
        }

        if ((ball.getRect()).intersects(paddle.getRect())) {

            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            // divide the paddle into parts. these are the cuts of the parts
            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) { // ball hits the first part of the paddle

                ball.setXDir(-1); // ball moves to the left
                ball.setYDir(-1); // ball moves upwards
            }

            if (ballLPos >= first && ballLPos < second) { // ball hits second part of the paddle

                ball.setXDir(-1); // ball moves to the left
                ball.setYDir(-1 * ball.getYDir()); // !!!
            }

            if (ballLPos >= second && ballLPos < third) { // ball hits the third part of the paddle

                ball.setXDir(0); // ball doesn't move left or right
                ball.setYDir(-1); // ball moves upwards
            }

            if (ballLPos >= third && ballLPos < fourth) { // ball hits fourth part of the paddle

                ball.setXDir(1); // ball  moves to the right
                ball.setYDir(-1 * ball.getYDir()); // !!!
            }

            if (ballLPos > fourth) { // ball hits the fifth part of the paddle

                ball.setXDir(1); // ball moves to the right
                ball.setYDir(-1); // ball moves upwards
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
}
