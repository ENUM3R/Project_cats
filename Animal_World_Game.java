//siema

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

public class Animal_World_Game extends JFrame {
    String[][] animals;
    final String lion = "Lion";
    final String eagle = "Eagle";
    final String mouse = "Mouse";
    final String elephant = "Elephant";
    final String grass = "";
    final String[] kierunki = {"N", "W", "S", "E"}; //kierunki w jaki mogą poruszać się zwierzęta

    Random losuj = new Random();
    int ile_ruchu;//zmienna przechowująca liczbę ruchu zwierząt
    int row;//wiersze
    int kol;//kolumnt

    JLabel[][] labels;
    JPanel panel;
    Timer timer;

    public Animal_World_Game(int row, int kol){
        setTitle("Animal_World_Game");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.row = row;
        this.kol = kol;
        animals = new String[row][kol];
        labels = new JLabel[row][kol];
        panel = new JPanel(new GridLayout(row,kol));
        add(panel,BorderLayout.CENTER);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                labels[i][j].setBackground(Color.green);
                labels[i][j].setHorizontalAlignment(JLabel.CENTER);
                labels[i][j].setFont(new Font("Arial",Font.BOLD,12));
                Border line = BorderFactory.createLineBorder(Color.black);
                labels[i][j].setBorder(line);
                panel.add(labels[i][j]);
            }
        }
        startGame();
        timer = new Timer(2000, e -> {
            try {
                moveAnimals();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            checkGameOver();
        });
        timer.start();
    }
    private void startGame(){
        //animals = new String[row][kol];
        for(int i=0;i<row;i++){
            for(int j=0;j<kol;j++)
            {
                animals[i][j] = grass;
            }
        }
        animals[0][0] = lion;
        animals[0][kol-1] = mouse;
        animals[row-1][kol-1] = elephant;
        animals[row-1][0] = eagle;
        updateLabels();
    }
    private void updateLabels(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                String  animal = animals[i][j];
                JLabel label = labels[i][j];
                switch (animal) {
                    case lion -> {
                        label.setBackground(Color.yellow);
                        label.setText("Lion");
                    }
                    case eagle -> {
                        label.setBackground(Color.white);
                        label.setText("Eagle");
                    }
                    case mouse -> {
                        label.setBackground(Color.DARK_GRAY);
                        label.setText("Mouse");
                    }
                    case elephant -> {
                        label.setBackground(Color.lightGray);
                        label.setText("Elephant");
                    }
                    case grass -> {
                        label.setBackground(Color.green);
                        label.setText("");
                    }
                }
            }
        }
    }
    private void moveAnimals() throws InterruptedException {
        //String[] animal = {lion,mouse,elephant,eagle};
        //int los=0;
        boolean [] isMove = {false,false,false,false};
        //0-lion 1-mouse 2-elphant 3-eagle
        int kolejnosc=0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                /*
                String animal = animals[i][j];
                if(!isMove[0] || !isMove[1] || !isMove[2] || !isMove[3]) {
                    switch (animal) {
                        case lion -> {
                            if (kolejnosc == 0) {
                                isMove[0] = true;
                                kolejnosc++;
                            }
                        }
                        case mouse -> {
                            if (kolejnosc == 1) {
                                isMove[1] = true;
                                kolejnosc++;
                            }
                        }
                        case elephant -> {
                            if (kolejnosc == 2) {
                                isMove[2] = true;
                                kolejnosc++;
                            }
                        }
                        case eagle -> {
                            if (kolejnosc == 3) {
                                isMove[3] = true;
                                kolejnosc=0;
                            }
                        }
                    }*/
                if(!animals[i][j].equals(grass)) {
                    //if(los == 4)
                    //los=0;
                    ile_ruchu = losuj.nextInt(6) + 1;
                    int index = losuj.nextInt(kierunki.length);
                    String kierunek = kierunki[index];
                    System.out.println(animals[i][j]);
                    System.out.println(ile_ruchu);
                    System.out.println(kierunek);
                    switch (animals[i][j]) {
                        case lion, eagle, mouse, elephant -> {
                            moveAnimal(i, j, ile_ruchu, kierunek);
                            updateLabels();
                            Thread.sleep(1000);
                        }
                    }

                    //los++;
                    //animal = animals[i][j];
                }
                }
            }
        }
        //updateLabels();
   //    }
    public void moveAnimal(int row,int kol,int ile_ruchu,String kierunek) throws InterruptedException {
        String animal = animals[row][kol];
        int newRow = row;
        int newKol = kol;
        int ruch=0;
        int k=kol;
        int r=row;
        Thread.sleep(1000);
            switch (kierunek) {
                case "N" -> {
                    while (k!= 0) {
                        k--;
                        ruch++;
                    }
                    if(ruch<ile_ruchu)
                        newKol = kol - ruch;
                    else
                        newKol = kol - ile_ruchu;
                }
                case "S" -> {
                    while (k != 9) {
                        k++;
                        ruch++;
                    }
                    if(ruch<ile_ruchu)
                        newKol = kol + ruch;
                    else
                        newKol = kol + ile_ruchu;
                }
                case "W" -> {
                    while (r != 0) {
                        r--;
                        ruch++;
                    }
                    if(ruch<ile_ruchu)
                        newRow = row - ruch;
                    else
                        newRow = row - ile_ruchu;
                }
                case "E" -> {
                    while (r != 9) {
                        r++;
                        ruch++;
                    }
                    if(ruch<ile_ruchu)
                        newRow = row + ruch;
                    else
                        newRow = row + ile_ruchu;
                }
            }
            if(!animals[newRow][newKol].equals(animals[row][kol])) {
                animals[newRow][newKol] = animal;
                animals[row][kol] = grass;

            }

    }
    /*
    public boolean isValidMove(int rows, int kolu){
        boolean bool = rows >= 0 && row > rows && kolu >= 0 && kol > kolu && animals[rows][kolu].equals(grass);
        return bool;
    }*/
    public void checkGameOver(){
        boolean foundLion = true;
        boolean foundEagle = true;
        boolean foundMouse= true;
        boolean foundElephant= true;
        int licznik = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                if(!animals[i][j].equals("Lion")){
                    foundLion = false;
                    licznik++;
                }
                if(!animals[i][j].equals("Eagle")){
                    foundEagle = false;
                    licznik++;
                }
                if(!animals[i][j].equals("Mouse")){
                    foundMouse= false;
                    licznik++;
                }
                if(!animals[i][j].equals("Elephant")){
                    foundElephant = false;
                    licznik++;
                }
            }
        }
        if(licznik == 3){
            timer.stop();
            String end = "Game over: ";
            if(!foundLion)
                end+=" Lion is dead! ";
            else
                end+= " Lion is winner!";
            if(!foundEagle)
                end+=" Eagle is dead";
            else
                end+= " Eagle is winner!";
            if(!foundMouse)
                end+=" Mouse is dead!";
            else
                end+= " Mouse is winner!";
            if(!foundElephant)
                end+=" Elephant is dead!";
            else
                end+= " Elephant is winner!";
            /*
            textField = new JTextField();
            textField.setText(end);
            panel.add(textField,"North");
            pack();
            setLocationRelativeTo(null);
             */
            JOptionPane.showMessageDialog(this, end, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

    }

}
