//https://zetcode.com/javagames/breakout/ 10.01.2023

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
public class Breakout extends JFrame {


    public Breakout() {

        initUI();
    }

    private void initUI() {

        add(new Board());
        setTitle("Breakout");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
    }

    public static void main(String[] args) {
        while (spielLäuft) {


            if (pauseGedrückt) {
                int option = JOptionPane.showOptionDialog(
                        null, "Spiel pausiert", "Pausenmenü",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{ "Fortsetzen", "Neustarten", "Beenden" },
                        "Fortsetzen");

                if (option == 1) {

                } else if (option == 2) {
                    spielLäuft = false;
                }

            }
        }
        EventQueue.invokeLater(() -> {

            var game = new Breakout();
            game.setVisible(true);
        });
    }
}