
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

        if (mx > Commons.WIDTH / 2 - 120 && mx <= Commons.WIDTH / 2 + 100) {
            if (my >= 200 && my <= 280) {
                SoundHandler.runMusic("breakout/src/resources/confirm.wav");
                if (Board.state == State.MENU) {
                    Board.setState(State.INGAME);
                    System.out.println("click");

                } else if (Board.state == State.GAMEOVER) {
                    System.out.println("go back");
                    Board.setState(State.MENU);

                } else if (Board.state == State.PAUSE) {
                    System.out.println("go back");
                    Board.setState(State.MENU);
                }
            } else if(my >= 300 && my <= 380){
                if(Board.state== State.MENU){
                    Board.showHighScoreWindow();
                }
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
