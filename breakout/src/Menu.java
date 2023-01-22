import javax.swing.*;
import java.awt.*;


public class Menu {


    /*public Rectangle playButton = new Rectangle(Commons.WIDTH/2 -120 ,200,220,80);
    String launch = "Press  UP  to release the ball.";
    String movement = "Press / Hold   LEFT and RIGHT  to move the paddle.";
    String pause = "Press SPACEBAR to pause the game.";*/







    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Image background;

        var ii = new ImageIcon("breakout/src/resources/menu.png");
        background = ii.getImage();

        g.drawImage(background, 0, 0, null);

        /*Font font0 = new Font("Arial", Font.BOLD, 50);
        g.setFont(font0);
        g.setColor(Color.BLACK);
        g.drawString("BREAKOUT", Commons.WIDTH/2 -150, 100);

        Font font1 = new Font("Arial", Font.BOLD, 20);
        g.setFont(font1);
        g.setColor(Color.BLACK);
        g.drawString("PLAY", playButton.x + 20, playButton.y+30);

        g.drawString(launch, 245, 400 );
        g.drawString(movement, 130, 480 );
        g.drawString(pause, 220, 560 );

        g2d.draw(playButton);*/

    }
}
