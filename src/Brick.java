//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.swing.ImageIcon;

public class Brick extends Sprite {
    private boolean destroyed;

    public Brick(int x, int y) {
        this.initBrick(x, y);
    }

    private void initBrick(int x, int y) {
        this.x = x;
        this.y = y;
        this.destroyed = false;
        this.loadImage();
        this.getImageDimensions();
    }

    private void loadImage() {
        ImageIcon ii;
        switch (Board.currentLevel) {
            case LEVEL_1:
                ii = new ImageIcon("src/resources/bluebelt.png");
                this.image = ii.getImage();
                break;
            case LEVEL_2:
                ii = new ImageIcon("src/resources/redbelt.png");
                this.image = ii.getImage();
                break;
            case LEVEL_3:
                ii = new ImageIcon("src/resources/goldbelt.png");
                this.image = ii.getImage();
        }

    }

    boolean isDestroyed() {
        return this.destroyed;
    }

    void setDestroyed(boolean val) {
        this.destroyed = val;
    }
}
