//https://zetcode.com/javagames/breakout/ 10.01.2023

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle extends Sprite  {

    private int dx;

    // direction variable

    public Paddle() {

        initPaddle();
    }

    private void initPaddle() {

        loadImage();
        getImageDimensions();
        resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("src/resources/star1.jpg");
        image = ii.getImage();
    }

    void move() {

        x += dx;

        if (x <= 0) {

            x = 0;
        }

        if (x >= Commons.WIDTH - imageWidth) {

            x = Commons.WIDTH - imageWidth;
        }
    }

    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) { // pressing the left arrow key

            dx = -1; // paddle moves to the left
        }

        if (key == KeyEvent.VK_RIGHT) { // pressing the right arrow key

            dx = 1; // paddle moves to the right
        }
    }

    void keyReleased(KeyEvent e) { //releasing the arrow keys, the paddle stops moving

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }

    }

    private void resetState() {

        x = Commons.INIT_PADDLE_X;
        y = Commons.INIT_PADDLE_Y;
    }
}
