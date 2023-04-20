import Cats.*;
import Food.Fish;
import Food.Meat;
import Methods.CheckGameOver;
import Methods.moveCats;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class BoardGame extends JFrame {
    int row;
    int kol;
    JPanel panel;
    JLabel[][] labels;
    String[] kierunki = {"N", "W", "S", "E"};

    Lion l1 = new Lion();
    Tiger t1 = new Tiger();
    Panther p1 = new Panther();
    Jaguar j1=  new Jaguar();

    Fish fish = new Fish();
    Meat meat = new Meat();

    protected BoardGame(int row,int kol) {
        setTitle("Cat Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.row = row;
        this.kol = kol;


        labels = new JLabel[row][kol];
        panel = new JPanel(new GridLayout(row, kol));
        add(panel, BorderLayout.CENTER);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                labels[i][j].setBackground(Color.green);
                labels[i][j].setHorizontalAlignment(JLabel.CENTER);
                labels[i][j].setFont(new Font("Arial", Font.BOLD, 12));
                Border line = BorderFactory.createLineBorder(Color.black);
                labels[i][j].setBorder(line);
                panel.add(labels[i][j]);
            }
        }
        Game g1 = new Game(row, kol);
        g1.startGame();
        moveCats mc = new moveCats();
        CheckGameOver cgo = new CheckGameOver();
        Timer timer = new Timer(2000, e -> {
            mc.moveCat();
            cgo.checkGameOver();
        });
        timer.start();
    }
}
