//core code from https://zetcode.com/javagames/breakout/ 10.01.2023
public interface Commons { //interface, like a container for all common constants

        int WIDTH = 780;//300*3; // width of the game board
        int HEIGHT = 400*2; // height of the game board
        int BOTTOM_EDGE = 780; //390*2; // guideline, when the ball passes this, game is over /lose a life
        int N_OF_BRICKS = 30; // number of bricks
        //int N_OF_OBSTACLES;
        int INIT_PADDLE_X = WIDTH/2-60; //200*3; //starting position of paddle in X-coordinate
        int INIT_PADDLE_Y = 360*2; //starting position of paddle in Y-coordinate
        int INIT_BALL_X = WIDTH/2-15;//230*3; //starting position of ball in X-coordinate
        int INIT_BALL_Y = 690;//355*2; //starting position of ball in Y-coordinate
        int PERIOD = 5; // time in ms between task executions
    }


