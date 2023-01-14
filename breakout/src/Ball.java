//core code from https://zetcode.com/javagames/breakout/ 10.01.2023

import javax.swing.ImageIcon; //class Imageicon that paints Icons from Images
import java.awt.event.KeyEvent;

public class Ball extends Sprite {

    private int xdir; //variable  for the directions of the ball in the X coordinate -1 = left, 1 = right
    private int ydir; //variable for the directions of the ball in the Y coordinate, -1 = up , 1 = down

    private int dx;

    private boolean launched;

    public Ball() { //constructor

        initBall();
    }

    private void initBall() { //initial movement of the ball, up and to the right

        launched = false;
        xdir = 0;
        ydir = 0;

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("src/resources/goldball1.png");
        image = ii.getImage();
    }

    //Following 2 Methods are called when the ball hits a brick or the paddle
    void setXDir(int x) {
        xdir = x;
    }
    void setYDir(int y) {
        ydir = y;
    }
    int getYDir() {
        return ydir;
    }

    void move() { // this method controls how the ball should move

        if (launched){
            x += xdir;
            y += ydir;

            if (x == 0) { // if the ball hits the left border

                setXDir(1); // it changes its direction to right
            }

            if (x == Commons.WIDTH - imageWidth) { // if the ball hits the right border

                System.out.println(imageWidth);
                setXDir(-1); // it changes its direction to left

            }

            if (y == 0) { // if the ball hits the upper border,

                setYDir(1); //the ball goes down
            }}
    }

    private void resetState() {

        x = Commons.INIT_BALL_X;
        y = Commons.INIT_BALL_Y;
    }

    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();


        if (key == KeyEvent.VK_UP) {

            launchBall();

        }


    }

    void launchBall(){

        if(!launched){
            launched =true;
            xdir =1;
            ydir =-1;
        }

    }

    public boolean isLaunched(){
        return launched;
    }






}

