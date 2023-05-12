import Cats.*;
import Food.Fish;
import Food.Meat;



import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class BoardGame extends JFrame {
    private final int row; //liczba wierszy
    private final int kol; //liczba kolumn
    JPanel panel;
    JLabel[][] labels;
    JLabel[] opis;
    final String[] kierunki = {"N", "W", "S", "E"}; //kierunki w jakie mogą się poruszać koty
    String[][] cats;
    JPanel legenda;
    Lion l1 = new Lion();
    final String lion = "Lion";
    Tiger t1 = new Tiger();
    final String tiger = "Tiger";
    Panther p1 = new Panther();
    final String panther = "Panther";
    Jaguar j1=  new Jaguar();
    final String jaguar = "Jaguar";
    final String grass = "";

    Fish fish = new Fish();
    final String f1 = "Fish";
    Meat meat = new Meat();
    final String m1 = "Meat";
    private final Timer timer;

    public BoardGame(int row,int kol) throws IOException { //konstruktor w którym wywoływana jest funkcja zaczynająca grę
        this.setTitle("Cat Game");
        this.setSize(1500, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Ustawienie panelu planszy kotów
        this.row = row;
        this.kol = kol;
        cats = new String[row][kol];
        labels = new JLabel[row][kol];
        panel = new JPanel(new GridLayout(row, kol));
        panel.setPreferredSize(new Dimension(1300,700));
        this.add(panel, BorderLayout.CENTER);
        //utworzenie i ustawienie bazowych danych dla labelów planszy kotów
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                labels[i][j] = new JLabel();
                labels[i][j].setOpaque(true);
                labels[i][j].setBackground(Color.green);
                labels[i][j].setHorizontalAlignment(JLabel.CENTER);
                labels[i][j].setFont(new Font("Arial", Font.BOLD, 8));
                Border line = BorderFactory.createLineBorder(Color.black);
                labels[i][j].setBorder(line);
                panel.add(labels[i][j]);
            }
        }
        //Opis atrybutow na biezaco, legenda
        legenda = new JPanel();
        legenda.setLayout(new BoxLayout(legenda, BoxLayout.Y_AXIS));
        legenda.setMaximumSize(new Dimension(200,700));
        opis = new JLabel[9];
        for(int k=0;k<9;k++) {
            opis[k] = new JLabel();
            opis[k].setOpaque(true);
            opis[k].setForeground(Color.BLACK);
            //opis[k].setBackground(Color.BLACK);
            opis[k].setMaximumSize(new Dimension(200,100));
            opis[k].setHorizontalAlignment(JLabel.CENTER);
            opis[k].setFont(new Font("Arial", Font.BOLD, 12));
            legenda.add(opis[k], BorderLayout.EAST);
        }
        opis[0].setText("Atrybuty Kotów");                         opis[0].setBackground(Color.RED);
        opis[1].setText("Predkość lwa: " + l1.speed);       opis[1].setBackground(Color.yellow);
        opis[2].setText("Siła lwa: " + l1.strength);        opis[2].setBackground(Color.yellow);
        opis[3].setText("Predkość tygrysa: " + t1.speed);   opis[3].setBackground(Color.orange);
        opis[4].setText("Siła tygrysa: " + t1.strength);    opis[4].setBackground(Color.orange);
        opis[5].setText("Predkość jaguara: " + j1.speed);   opis[5].setBackground(Color.lightGray);
        opis[6].setText("Siła jaguara: " + j1.strength);    opis[6].setBackground(Color.lightGray);
        opis[7].setText("Predkość pantery: " + p1.speed);   opis[7].setBackground(new Color(150,75,0));
        opis[8].setText("Siła pantery: " + p1.strength);    opis[8].setBackground(new Color(150,75,0));
        this.add(legenda,BorderLayout.EAST);
        //Obsługiwanie startu, ruchów i sprawdzanie czy gra się kończy
        startGame();
        timer = new Timer(2000, e -> {
            try {
                move_Cats();

            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }
            checkGameOver();
        });
        timer.start();
    }

    private void startGame(){ //Funkcja ustawiająca podstawowe parametry gry i zaczynająca ją
        String start = "Start!";
        JOptionPane.showMessageDialog(this,start, "Start Simulation", JOptionPane.INFORMATION_MESSAGE);
        for(int i=0;i<row;i++){
            for(int j=0;j<kol;j++)
            {
                cats[i][j] = grass;
            }
        }
        //Bazowe ustawienie kotów
        cats[0][0] = lion;
        cats[0][kol-1] = tiger;
        cats[row-1][0] = panther;
        cats[row-1][kol-1] = jaguar;
        DrawFood df = new DrawFood();
        for (int k = 0; k < kol+row*3; k++) { //Losowanie i ustawienie pożywienia na planszy
            if (df.los_Food().equals("fish"))
                cats[df.los_Row()][df.los_Kol()] = f1;
            else
                cats[df.los_Row()][df.los_Kol()] = m1;
        }
        update_Labels();
    }
    private void update_Labels(){ //Funkcja obsługująca zmiane labelów symulacji
        for(int i=0;i<row;i++){
            for(int j=0;j<kol;j++){
                String cat = cats[i][j];
                JLabel label = labels[i][j];
                switch (cat){
                    case lion -> {
                        label.setText(l1.name);
                        label.setBackground(Color.yellow);
                    }
                    case tiger-> {
                        label.setText(t1.name);
                        label.setBackground(Color.ORANGE);
                    }
                    case panther-> {
                        label.setText(p1.name);
                        label.setBackground(Color.lightGray);
                    }
                    case jaguar-> {
                        label.setText(j1.name);
                        label.setBackground(new Color(150,75,0));
                    }
                    case grass -> {
                        label.setText("");
                        label.setBackground(Color.green);
                    }
                    case f1 -> {
                        label.setText(f1);
                        label.setBackground(Color.blue);
                    }
                    case m1 -> {
                        label.setText(m1);
                        label.setBackground(Color.red);
                    }
                }
            }
        }
    }
    private void move_Cats() throws IOException, InterruptedException{//Funkcja która obsługuje poruszanie się kotów
        Random losuj = new Random();
        PrintWriter writer = new PrintWriter("Atrybuty.txt"); //zapis do pliku zmiany atrybutów kotów
        //Zmienne opisujące czy kot żyje i się poruszył
        int ruchT=0;
        int ruchL=0;
        int ruchP=0;
        int ruchJ=0;
        boolean[] isALive = {false,false,false,false};// 0->Lew, 1->Tygrys, 2->Pantera, 3->Jaguar
        int suma;
        //Sprawdzanie czy dany kot żyje
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                if(cats[i][j].equals(lion))
                    isALive[0] = true;
                if(cats[i][j].equals(tiger))
                    isALive[1] = true;
                if(cats[i][j].equals(panther))
                    isALive[2] = true;
                if(cats[i][j].equals(jaguar))
                    isALive[3] = true;
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < kol; j++) {
                //Sprawdzanie czy dany kot się już poruszył w danej turze
                suma=ruchL+ruchJ+ruchP+ruchT;
                if(suma==4){
                    ruchL=0;
                    ruchT=0;
                    ruchP=0;
                    ruchJ=0;
                }
                //Sprawdzanie czy dane pole jest kotem, jeśli tak to losowanie kierunku i poruszenie kotem
                if (!cats[i][j].equals(grass) && !cats[i][j].equals(f1) && !cats[i][j].equals(m1)){
                    int index = losuj.nextInt(kierunki.length);
                    String kierunek = kierunki[index];
                    switch (cats[i][j]) {
                        case lion -> {
                            if(ruchL==0 && isALive[0]) {
                                ruchL=1;
                                moveCat(i, j, l1.speed, kierunek);
                                writer.write("Predkość lwa: " + l1.speed);
                                writer.println();
                                opis[1].setText("Predkość lwa: " + l1.speed);
                                writer.write("Siła lwa: " + l1.strength);
                                writer.println();
                                opis[2].setText("Siła lwa: " + l1.strength);
                                update_Labels();
                            }
                        }
                        case tiger -> {
                            if(ruchT==0 && isALive[1]) {
                                ruchT=1;
                                moveCat(i, j, t1.speed, kierunek);
                                writer.write("Predkość tygrysa: " + t1.speed);
                                writer.println();
                                opis[3].setText("Predkość tygrysa: " + t1.speed);
                                writer.write("Siła tygrysa: " + t1.strength);
                                writer.println();
                                opis[4].setText("Siła tygrysa: " + t1.strength);
                                update_Labels();
                            }
                        }
                        case panther -> {
                            if(ruchP==0 && isALive[2]) {
                                ruchP=1;
                                moveCat(i, j, p1.speed, kierunek);
                                writer.write("Predkość pantery: " + p1.speed);
                                writer.println();
                                opis[5].setText("Predkość pantery: " + p1.speed);
                                writer.write("Siła pantery: " + p1.strength);
                                writer.println();
                                opis[6].setText("Siła pantery: " + p1.strength);
                                update_Labels();
                            }
                        }
                        case jaguar -> {
                            if(ruchJ==0 && isALive[3]) {
                                ruchJ=1;
                                moveCat(i, j, j1.speed, kierunek);
                                writer.write("Predkość jaguara: " + j1.speed);
                                writer.println();
                                opis[7].setText("Predkość jaguara: " + j1.speed);
                                writer.write("Siła jaguara: " + j1.strength);
                                writer.println();
                                opis[8].setText("Siła jaguara: " + j1.strength);
                                update_Labels();
                            }
                        }
                    }
                }
            }
        }
        for(int k=0;k<4;k++)
            isALive[k] = false;
        writer.close();
    }
    private void moveCat(int row,int kol,int ile_ruchu,String kierunek) throws FileNotFoundException{//Funkcja poruszająca kot
        String cat = cats[row][kol];
        int newRow = row; //nowy wiersz
        int newKol = kol; //nowa kolumna
        PrintWriter pw = new PrintWriter("koty.txt"); //zapis do pliku jak i w jakim kierunku porusza się dany kot
        pw.write("Kot: " + cat );
        pw.println();
        pw.write("Kierunek ruchu: " + kierunek);
        pw.println();
        pw.write("Ile ruchu: " + ile_ruchu);
        pw.println();
        switch (kierunek){
            case "N"-> newKol = kol-ile_ruchu;
            case "S"-> newKol = kol+ile_ruchu;
            case "W"-> newRow = row-ile_ruchu;
            case "E"-> newRow = row+ile_ruchu;
        }
        //Badanie czy nie przekroczono tablicy
        if(newKol>=40)
            newKol=39;
        if (newKol<0)
            newKol=0;
        if(newRow>=40)
            newRow=39;
        if(newRow<0)
            newRow=0;
        //Badanie czy nowe pole jest trawą:
        if(cats[newRow][newKol].equals(grass)){//Tak, przesunięcie kota na nowe miejsce i ustawienie trawy na starym
            cats[newRow][newKol] = cats[row][kol];
            cats[row][kol] = grass;
        } else if (cats[newRow][newKol].equals(fish.name) || cats[newRow][newKol].equals(meat.name) ) {
            //Nie, pole jest jedzeniem
            if(cats[newRow][newKol].equals(fish.name)){
                cat = cats[row][kol];
                switch (cat){
                    case lion->{
                        l1.speed+=2;
                        l1.strength++;
                    }
                    case tiger->{
                        t1.speed+=2;
                        t1.strength++;
                    }
                    case panther->{
                        p1.speed+=2;
                        p1.strength++;
                    }
                    case jaguar->{
                        j1.speed+=2;
                        j1.strength++;
                    }
                }
            }else {
                cat = cats[row][kol];
                switch (cat) {
                    case lion -> {
                        l1.speed++;
                        l1.strength += 2;
                    }
                    case tiger -> {
                        t1.speed++;
                        t1.strength += 2;
                    }
                    case panther -> {
                        p1.speed++;
                        p1.strength += 2;
                    }
                    case jaguar -> {
                        j1.speed++;
                        j1.strength += 2;
                    }
                }
            }
            cats[newRow][newKol] = cats[row][kol];
            cats[row][kol] = grass;
        }
        //Badanie czy nowe pole jest innym kotem
        else if(!Objects.equals(cats[row][kol], cats[newRow][newKol])) {
            String cat1 = cats[row][kol];
            String cat2 = cats[newRow][newKol];
            PrintWriter fights = new PrintWriter("Walki.txt");//Zapis do pliku walki kotów
            fights.write("Walka kotow: " + cat1 + " " + cat2);
            fights.println();
            int wygral = fight(cat1, cat2);
            //Badanie który kot wygrał walkę
            if (wygral == 1) {//wygrał kot atakujący (zmieniający pole)
                cats[newRow][newKol] = cats[row][kol];
                cats[row][kol] = grass;
                fights.write("Wygral walke:" + cats[row][kol]);
                fights.println();
            } else if (wygral == 0) {//wygrał kot broniący się
                cats[row][kol] = grass;
                fights.write("Wygral walke:" + cats[newRow][newKol]);
                fights.println();
            }
            fights.close();
        }
        pw.close();
    }
    private int fight(String cat1, String cat2) throws FileNotFoundException {//Funkcja obsługująca walkę kotów
        Random szansa = new Random();
        ArrayList<Integer> wygrana = new ArrayList<>();
        PrintWriter writer = new PrintWriter("kotely.txt");//Zapis do pliku, który kot wygrał
        int i;
        switch(cat1) {
            case lion-> {
                switch (cat2) {
                    case tiger -> {
                        for (i = 0; i < l1.strength; i++)//szansa wygrania lwa
                            wygrana.add(1);
                        for (i = 0; i < t1.strength; i++)//szansa wygrania tygrysa
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Lew wygrał z tygrysem");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Tygrys wygrał z lwem");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                    case panther -> {
                        for (i = 0; i < l1.strength; i++)//szansa wygrania lwa
                            wygrana.add(1);
                        for (i = 0; i < p1.strength; i++)//szansa wygrania pantery
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Lew wygrał z panterą");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Pantera wygrała z lwem");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                    case jaguar -> {
                        for (i = 0; i < l1.strength; i++)//szansa wygrania lwa
                            wygrana.add(1);
                        for (i = 0; i < j1.strength; i++)//szansa wygrania jaguara
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Lew wygrał z jaguarem");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Jaguar wygrał z lwem");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                }
            }
            case tiger->{
                switch (cat2) {
                    case lion -> {
                        for (i = 0; i < l1.strength; i++)//szansa wygrania lwa
                            wygrana.add(1);
                        for (i = 0; i < t1.strength; i++)//szansa wygrania tygrysa
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Lew wygrał z tygrysem");
                            writer.println();
                            writer.close();
                            return 0;
                        } else {
                            writer.write(" Tygrys wygrał z lwem");
                            writer.println();
                            writer.close();
                            return 1;
                        }
                    }
                    case panther -> {
                        for (i = 0; i < t1.strength; i++)//szansa wygrania tygrysa
                            wygrana.add(1);
                        for (i = 0; i < p1.strength; i++)//szansa wygrania pantery
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Tygrys wygrał z panterą");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Pantera wygrała z tygrysem");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                    case jaguar -> {
                        for (i = 0; i < t1.strength; i++)//szansa wygrania tygrysa
                            wygrana.add(1);
                        for (i = 0; i < j1.strength; i++)//szansa wygrania jaguara
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Tygrys wygrał z jaguarem");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Jaguar wygrał z tygrysem");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                }
            }
            case panther->{
                switch (cat2) {
                    case tiger -> {
                        for (i = 0; i < p1.strength; i++)//szansa wygrania pantery
                            wygrana.add(1);
                        for (i = 0; i < t1.strength; i++)//szansa wygrania tygrysa
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Pantera wygrała z tygrysem");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Tygrys wygrał z panterą");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                    case lion -> {
                        for (i = 0; i < l1.strength; i++)//szansa wygrania lwa
                            wygrana.add(1);
                        for (i = 0; i < p1.strength; i++)//szansa wygrania pantery
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Lew wygrał z panterą");
                            writer.println();
                            writer.close();
                            return 0;
                        } else {
                            writer.write(" Pantera wygrała z lwem");
                            writer.println();
                            writer.close();
                            return 1;
                        }
                    }
                    case jaguar -> {
                        for (i = 0; i < p1.strength; i++)//szansa wygrania pantery
                            wygrana.add(1);
                        for (i = 0; i < j1.strength; i++)//szansa wygrania jaguara
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Pantera wygrała z jaguarem");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Jaguar wygrał z panterą");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                }
            }
            case jaguar->{
                switch (cat2) {
                    case tiger -> {
                        for (i = 0; i < j1.strength; i++)//szansa wygrania jaguara
                            wygrana.add(1);
                        for (i = 0; i < t1.strength; i++)//szansa wygrania tygrysa
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Jaguar wygrał z tygrysem");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Tygrys wygrał z jaguarem");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                    case panther -> {
                        for (i = 0; i < j1.strength; i++)//szansa wygrania jaguara
                            wygrana.add(1);
                        for (i = 0; i < p1.strength; i++)//szansa wygrania pantery
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Jaguar wygrał z panterą");
                            writer.println();
                            writer.close();
                            return 1;
                        } else {
                            writer.write(" Pantera wygrała z jaguarem");
                            writer.println();
                            writer.close();
                            return 0;
                        }
                    }
                    case lion -> {
                        for (i = 0; i < l1.strength; i++)//szansa wygrania lwa
                            wygrana.add(1);
                        for (i = 0; i < j1.strength; i++)//szansa wygrania jaguara
                            wygrana.add(2);
                        int index = szansa.nextInt(wygrana.size());
                        if (wygrana.get(index).equals(1)) {
                            writer.write(" Lew wygrał z jaguarem");
                            writer.println();
                            writer.close();
                            return 0;
                        } else {
                            writer.write(" Jaguar wygrał z lwem");
                            writer.println();
                            writer.close();
                            return 1;
                        }
                    }
                }
            }
        }
        writer.close();
        return 0;
    }
    private void  checkGameOver(){//Funkcja badająca czy symulacja się skończyła
        int licznik=0;
        int wygrana=0;
        boolean isAliveLion = true;
        int lionAlive=0;
        boolean isAliveTiger = true;
        int tigerAlive=0;
        boolean isAlivePanther = true;
        int pantherAlive=0;
        boolean isAliveJaguar = true;
        int jaguarAlive=0;
        for(int i=0;i<row;i++){
            if(licznik==3)
                break;
            for(int j=0;j<kol;j++){
                if(!cats[i][j].equals(lion)){
                    if(lionAlive==0) {
                        licznik++;
                        lionAlive=1;
                        isAliveLion = false;
                    }
                }
                if(!cats[i][j].equals(tiger)){
                    if(tigerAlive==0){
                        licznik++;
                        tigerAlive=1;
                        isAliveTiger=false;
                    }
                }
                if(!cats[i][j].equals(panther)){
                    if(pantherAlive==0){
                        licznik++;
                        pantherAlive=1;
                        isAlivePanther=false;
                    }
                }
                if(!cats[i][j].equals(jaguar)){
                    if(jaguarAlive==0){
                        licznik++;
                        jaguarAlive=1;
                        isAliveJaguar=false;
                    }
                }
            }
        }
        if(licznik==3)//Gra wygrana bo żyje tylko jeden kot
            wygrana = 3;
        //Gra wygrana przez osiągnięcie zestawu 8,10/10,8 siły,szybkości
        if(checkState() == 5 || checkState() == 6 || checkState() == 7 || checkState() == 8)
            wygrana=2;
        switch(wygrana){
            case 3 -> {
                timer.stop();
                String end = "Game over: ";
                if (isAliveLion)
                    end += " Lion is dead! ";
                else
                    end += " Lion is winner!";
                if (isAliveTiger)
                    end += " Tiger is dead";
                else
                    end += " Tiger is winner!";
                if (isAlivePanther)
                    end += " Panther is dead!";
                else
                    end += " Panther is winner!";
                if (isAliveJaguar)
                    end += " Jaguar is dead!";
                else
                    end += " Jaguar is winner!";
                JOptionPane.showMessageDialog(this, end, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            case 2->{
                timer.stop();
                String end = "Game over: ";
                switch (checkState()){
                    case 5->{
                        end += "Lion is winner!";
                        if(isAliveJaguar)
                            end += " Jaguar is dead!";
                        if(isAliveTiger)
                            end += " Tiger is dead!";
                        if(isAlivePanther)
                            end += " Panther is dead!";
                    }
                    case 6->{
                        end+= "Tiger is winner!";
                        if(isAliveJaguar)
                            end += " Jaguar is dead!";
                        if(isAliveLion)
                            end += " Lion is dead!";
                        if(isAlivePanther)
                            end += " Panther is dead!";
                    }
                    case 7->{
                        end+= "Panther is winner!";
                        if(isAliveJaguar)
                            end += " Jaguar is dead!";
                        if(isAliveTiger)
                            end += " Tiger is dead!";
                        if(isAliveLion)
                            end += " Lion is dead!";
                    }
                    case 8->{
                        end += "Jaguar is winner!";
                        if(isAliveLion)
                            end += " Lion is dead!";
                        if(isAliveTiger)
                            end += " Tiger is dead!";
                        if(isAlivePanther)
                            end += " Panther is dead!";
                    }
                }
                JOptionPane.showMessageDialog(this, end, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }
    private int checkState() {//funkcja sprawdzająca czy wygrano symulację przez atrybuty
        int lstr = l1.strength;
        int lsp = l1.speed;
        int tstr = t1.strength;
        int tsp = t1.speed;
        int pstr = p1.strength;
        int psp = p1.speed;
        int jstr = j1.strength;
        int jsp = j1.speed;
        int isWinner=0;
        //Czy wygrał Lew->5
        if(lstr >= 10){
            if(lsp >= 8){
                isWinner = 5;
            }
        }
        if(lstr >= 8){
            if(lsp >= 10){
                isWinner = 5;
            }
        }
        //Czy wygrał Tygrys->6
        if(tstr >= 10){
            if(tsp >= 8){
                isWinner = 6;
            }
        }
        if(tstr >= 8){
            if(tsp >= 10){
                isWinner = 6;
            }
        }
        //Czy wygrała Pantera->7
        if(pstr >= 10){
            if(psp >= 8){
                isWinner = 7;
            }
        }
        if(pstr >= 8){
            if(psp >= 10){
                isWinner = 7;
            }
        }
        //Czy wygrał Jagur->8
        if(jstr >= 10){
            if(jsp >= 8){
                isWinner = 8;
            }
        }
        if(jstr >= 8){
            if(jsp >= 10){
                isWinner = 8;
            }
        }
        return isWinner;
    }
}
