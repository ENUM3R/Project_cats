package Food;


import javax.swing.*;
import java.awt.*;

public class Meat extends Food implements makeLabel {
    int upgradeStrength;
    public Meat(){
        name="Food.Meat";
        upgradeStrength=1;
    }

    @Override
    public JLabel makeLabel() {
        JLabel meatLabel = new JLabel();
        meatLabel.setText("Meat");
        meatLabel.setBackground(Color.RED);
        return meatLabel;
    }
}
