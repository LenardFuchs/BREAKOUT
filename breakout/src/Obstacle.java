import javax.swing.*;

public class Obstacle extends Sprite{

    private int xdir;

    public Obstacle(int x, int y) { //constructor

        initObstacle(x, y);
    }

    private void initObstacle(int x, int y) { //method to create the brick

        xdir = 1;

        this.x = x;
        this.y = y;


        loadImage();
        getImageDimensions();
    }

    private void loadImage() {  //method to load image file to a certain brick


        switch(Board.currentLevel){
            case LEVEL_1:
                var ii = new ImageIcon("src/resources/hart.png");
                image = ii.getImage();
                break;
            case LEVEL_2:
                ii = new ImageIcon("src/resources/undertaker.png");
                image = ii.getImage();
                break;
            case LEVEL_3:
                ii = new ImageIcon("src/resources/cena.png");
                image = ii.getImage();
                break;
        }


    }

    void setXDir(int x) {
        xdir = x;
    }

    void move() { // this method controls how the ball should move

        x += xdir;

        if (x == 0) { // if the ball hits the left border

            setXDir(1); // it changes its direction to right
        }

        if (x == Commons.WIDTH - imageWidth) { // if the ball hits the right border

            System.out.println(imageWidth);
            setXDir(-1); // it changes its direction to left

        }

    }





}
