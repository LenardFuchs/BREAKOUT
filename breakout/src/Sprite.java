import java.awt.*;


//This is the base class for all the objects in the board,
//methods and variables that are in Ball, Brick, Paddle objects
public class Sprite {

        int x;
        int y;
        int imageWidth;
        int imageHeight;
        Image image;

        protected void setX(int x) {

            this.x = x;
        }

        int getX() {

            return x;
        }

        protected void setY(int y) {

            this.y = y;
        }

        int getY() {

            return y;
        }

        int getImageWidth() {

            return imageWidth;
        }

        int getImageHeight() {

            return imageHeight;
        }

        Image getImage() {

            return image;
        }

        Rectangle getRect() {

            return new Rectangle(x, y,
                    image.getWidth(null), image.getHeight(null));
        }

        void getImageDimensions() {

            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
        }
    }


