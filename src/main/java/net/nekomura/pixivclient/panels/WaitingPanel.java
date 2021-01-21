package net.nekomura.pixivclient.panels;

import net.nekomura.pixivclient.Main;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JPanel {
    public WaitingPanel() {
        JLabel waitingLabel = new JLabel("PLEASE WAIT, IT WILL BE SOON :D");
        waitingLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        waitingLabel.setForeground(Color.GRAY);
        waitingLabel.setBounds(Main.sc.width / 2 - 300, Main.sc.height / 2 - 15, 600, 30);

        super.add(waitingLabel);
    }
}
