package Cats;

import javax.swing.*;
import java.awt.*;

public class Jaguar extends Cat implements makeLabel{
    public Jaguar(){
        name="Jaguar";
        speed=2;
        strength=1;
    }
    @Override
    public JLabel makeLabel() {
        JLabel jaguarLabel = new JLabel();
        jaguarLabel.setText("Jaguar");
        jaguarLabel.setBackground(Color.cyan);
        return jaguarLabel;
    }
}
