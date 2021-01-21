package net.nekomura.pixivclient.panels;

import net.nekomura.pixivclient.Main;

import javax.swing.*;
import java.awt.*;

public class PixivPagePanel extends JPanel {
    public PixivPagePanel() {
        ImageIcon pixivLogo = new ImageIcon("assets/pixiv-logo.png");

        JLabel pixivLogoLabel = new JLabel(new ImageIcon(pixivLogo.getImage().getScaledInstance(164, 64, java.awt.Image.SCALE_SMOOTH)));
        pixivLogoLabel.setBounds(25, 20, 164, 64);

        JTextField searchFiled = new JTextField();
        searchFiled.setBounds(Main.sc.width / 2 - 300, 20, 600, 64);
        searchFiled.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));

        super.setLayout(null);
        super.setBackground(Color.WHITE);
        super.add(searchFiled);
        super.add(pixivLogoLabel);
    }
}
