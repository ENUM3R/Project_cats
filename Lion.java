package Cats;

import javax.swing.*;
import java.awt.*;

public class Lion extends Cat implements makeLabel  {
    public Lion(){
        strength=2;
        speed=1;
        name="Lion";
    }

    @Override
    public JLabel makeLabel(){
        JLabel lionLabel = new JLabel();
        lionLabel.setText(name);
        lionLabel.setBackground(Color.YELLOW);
        return lionLabel;
    }
}
