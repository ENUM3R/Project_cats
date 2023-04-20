package Cats;

import javax.swing.*;
import java.awt.*;

public class Panther extends Cat implements makeLabel{
    public Panther(){
        name="Panther";
        speed =2;
        strength=1;
    }
    @Override
    public JLabel makeLabel() {
        JLabel pantherLabel = new JLabel();
        pantherLabel.setText(name);
        pantherLabel.setBackground(Color.DARK_GRAY);
        return pantherLabel;
    }
}
