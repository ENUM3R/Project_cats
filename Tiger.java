package Cats;

import javax.swing.*;
import java.awt.*;

public class Tiger extends Cat implements makeLabel{

    public Tiger(){
        name="Tiger";
        speed=1;
        strength=2;
    }

    @Override
    public JLabel makeLabel() {
        JLabel tigerLabel = new JLabel();
        tigerLabel.setText(name);
        tigerLabel.setBackground(Color.ORANGE);
        return tigerLabel;
    }
}
