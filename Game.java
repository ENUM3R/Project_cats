import Methods.DrawFood;
import Methods.updateLabels;

public class Game extends BoardGame {
    protected Game(int row, int kol) {
        super(row, kol);
    }
    public void startGame(){
        DrawFood df = new DrawFood();
        for (int k = 0; k < 10; k++) {
            if (df.los_Food().equals("fish"))
                labels[df.los_Row()][df.los_Kol()] = fish.makeLabel();
            else
                labels[df.los_Row()][df.los_Kol()] = meat.makeLabel();
        }
        labels[0][0] = l1.makeLabel();
        labels[0][kol-1] = t1.makeLabel();
        labels[row-1][0] = p1.makeLabel();
        labels[row-1][kol-1] = j1.makeLabel();
        updateLabels ul = new updateLabels();
        ul.update_Labels();
    }
}
