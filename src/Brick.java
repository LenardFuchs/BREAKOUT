import javax.swing.ImageIcon;

public class Brick extends Sprite {

    private boolean destroyed; // in this variable we keep the state of a brick

    public Brick(int x, int y) { //constructor

        initBrick(x, y);
    }

    private void initBrick(int x, int y) { //method to create the brick

        this.x = x;
        this.y = y;

        destroyed = false;

        loadImage();
        getImageDimensions();
    }

    private void loadImage() {  //method to load image file to a certain brick


        switch(Board.currentLevel){
            case Level1:
                var ii = new ImageIcon("src/resources/choco2.jpg");
                image = ii.getImage();
                break;
            case Level2:
                ii = new ImageIcon("src/resources/star1.jpg");
                image = ii.getImage();
                break;
            case Level3:
                ii = new ImageIcon("src/resources/choco2.jpg");
                image = ii.getImage();
                break;
        }


    }

    boolean isDestroyed() {  //method which asks what is the state of the brick

        return destroyed;
    }

    void setDestroyed(boolean val) {  //val is always true, this will set the bricks state to "destroyed"

        destroyed = val;
    }
}

