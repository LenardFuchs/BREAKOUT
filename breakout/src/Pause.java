import javax.swing.*;
import java.awt.*;


public class Pause {


    //public Rectangle playButton = new Rectangle(Commons.WIDTH/2 -50 ,150,100,50);
    public void render(Graphics g) {

        Image background;
        Graphics2D g2d = (Graphics2D) g;

        var ii = new ImageIcon("breakout/src/resources/paused.png");
        background = ii.getImage();

        g2d.drawImage(background, 0, 0, null);

        /*Font font0 = new Font("Arial", Font.BOLD, 30);
        g.setFont(font0);
        g.setColor(Color.BLACK);
        g.drawString("GAME PAUSED", Commons.WIDTH/2 -100, 300);
        g.drawString("Press SPACEBAR to return", 200, 400);

        Font font1 = new Font("Arial", Font.BOLD, 20);
        g.setFont(font1);
        g.setColor(Color.BLACK);
        g.drawString("EXIT", playButton.x + 20, playButton.y+30);

        g2d.draw(playButton);*/


    }


}
