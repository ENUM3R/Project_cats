package Food;

import javax.swing.*;
import java.awt.*;

public class Fish extends Food implements makeLabel{
    int upgradeSpeed;
    public Fish(){
        name="Fish";
        upgradeSpeed=1;
    }

    @Override
    public JLabel makeLabel() {
        JLabel fishLabel = new JLabel();
        fishLabel.setText("Fish");
        fishLabel.setBackground(Color.blue);
        return fishLabel;
    }
}
