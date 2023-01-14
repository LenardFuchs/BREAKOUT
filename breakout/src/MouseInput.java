import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {



    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        // public Rectangle playButton = new Rectangle(Commons.WIDTH/2 -50 ,150,100,50);
        if (mx >Commons.WIDTH/2 -50 && mx <= Commons.WIDTH/2 +50){
            if (my>= 150 && my<= 200 ){
                Board.setState(State.INGAME);
                System.out.println("click");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
