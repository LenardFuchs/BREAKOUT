//https://zetcode.com/javagames/breakout/ 10.01.2023

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
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
    private Menu menu = new Menu();
    private boolean inGame = true;
    //public static int state =0;

    private int lives = 3;//implement lives in the game
    //String life = lives.Integer.toString;

    private int score = 0;
    public static State state;
    //boolean spacebar = false;



    public Board() {

        initBoard();
    }

    private void initBoard() {

        setState(State.MENU);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        addMouseListener(new MouseInput());


        gameInit();




    }

    private void gameInit() { // we create a ball, a paddle and bricks (number of which we set in Commons class)

        bricks = new Brick[Commons.N_OF_BRICKS]; // brick array, length is the number we set in commons
        ball = new Ball();
        paddle = new Paddle();

        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }
        //  we create and start a timer


        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

       /* Font font = new Font("Verdana", Font.BOLD, 11);
        g.setFont(font);
        g.setColor(Color.blue);
        g.drawString("score:", 30, 20);
        g.drawString(Integer.toString(score), 80, 20);
        g.drawString("lives:", 220, 20);
        g.drawString(Integer.toString(lives), 260, 20);*/

        if (state == State.MENU){
            menu.render(g);

        } else if (state == State.INGAME) {

            drawObjects(g2d);

            Font font = new Font("Comic Sans", Font.PLAIN,  11);
            g.setFont(font);
            g.setColor(Color.blue);
            g.drawString("score:", 30, 20);
            g.drawString(Integer.toString(score), 80, 20);
            g.drawString("lives:", 220, 20);
            g.drawString(Integer.toString(lives), 260, 20);

        } else if (state == State.GAMEOVER){

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

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {

            if (!bricks[i].isDestroyed()) { // draw if brick is not destroyed

                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getImageWidth(),
                        bricks[i].getImageHeight(), this);
            }
        }
    }

    private void gameOver(Graphics2D g2d) { // draws Game over or Victory

        var font = new Font("Comic Sans", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(gameover,
                (Commons.WIDTH - fontMetrics.stringWidth(gameover)) / 2,
                Commons.WIDTH / 2);
        g2d.drawString("Final Score ",(Commons.WIDTH - fontMetrics.stringWidth(gameover)) / 2,  230);

        g2d.drawString(Integer.toString(score),125,  270);





    }

    public static void setState(State s){
        state = s;

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            if (state == State.INGAME) {
            paddle.keyReleased(e);}
        }

        @Override
        public void keyPressed(KeyEvent e) {



            if (state ==State.INGAME){
                paddle.keyPressed(e);}

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

        ball.move();
        paddle.move();
        checkCollision();
        repaint();
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

    private void checkCollision() {

        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE)  {  //when the ball hits the bottom,
            lives--; //deduct a life



            if (lives !=0 ){ //
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

                gameover = "Victory"; // we win
                //stopGame();
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
    }
}
