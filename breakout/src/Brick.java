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

        var ii = new ImageIcon("src/resources/cigar(1).jpg");
        image = ii.getImage();
    }

    boolean isDestroyed() {  //method which asks what is the state of the brick

        return destroyed;
    }

    void setDestroyed(boolean val) {  //val is always true, this will set the bricks state to "destroyed"

        destroyed = val;
    }
}

