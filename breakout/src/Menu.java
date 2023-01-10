import java.awt.*;


public class Menu {

    public Rectangle playButton = new Rectangle(Commons.WIDTH/2 -50 ,150,100,50);


    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font font0 = new Font("Arial", Font.BOLD, 30);
        g.setFont(font0);
        g.setColor(Color.BLUE);
        g.drawString("BREAK OUT", Commons.WIDTH/2 -90, 100);

        Font font1 = new Font("Arial", Font.BOLD, 14);
        g.setFont(font1);
        g.drawString("PLAY", playButton.x + 30, playButton.y+30);

        g2d.draw(playButton);

    }
}
