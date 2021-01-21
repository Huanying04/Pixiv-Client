package net.nekomura.pixivclient.panels;

import net.nekomura.pixivclient.Main;
import net.nekomura.utils.jixiv.artworks.Illustration;
import net.nekomura.utils.jixiv.exception.PixivException;
import net.sf.image4j.codec.ico.ICODecoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class PixivPagePanel extends JPanel {
    public PixivPagePanel() {
        ImageIcon pixivLogo = new ImageIcon("assets/pixiv-logo.png");

        JLabel pixivLogoLabel = new JLabel(new ImageIcon(pixivLogo.getImage().getScaledInstance(164, 64, java.awt.Image.SCALE_SMOOTH)));
        pixivLogoLabel.setBounds(25, 20, 164, 64);

        JTextField searchFiled = new JTextField();
        searchFiled.setBounds(Main.sc.width / 2 - 300, 20, 600, 64);
        searchFiled.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));

        JTextField idField = new JTextField();
        idField.setBounds(Main.sc.width - 300 - 50 - 10, 20, 300, 64);
        idField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));

        idField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "請輸入id以便查詢插畫！", "錯誤", JOptionPane.WARNING_MESSAGE);
                }else {
                    int id = Integer.parseInt(idField.getText());
                    try {
                        Illustration.getInfo(id);  //檢測插畫存不存在
                        JFrame illustrationFrame;
                        illustrationFrame = new JFrame("" + id);
                        illustrationFrame.setSize(1280, 720);
                        illustrationFrame.setIconImage(new ImageIcon(ICODecoder.read(new File("assets/favicon.ico")).get(0)).getImage());
                        illustrationFrame.setResizable(true);
                        illustrationFrame.getContentPane().add(new IllustrationPagePanel((id)));
                        illustrationFrame.setVisible(true);
                        illustrationFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }catch (PixivException | IOException | ParseException err) {
                        JOptionPane.showMessageDialog(null, err.toString(), "錯誤", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        super.setLayout(null);
        super.setBackground(Color.WHITE);
        super.add(searchFiled);
        super.add(pixivLogoLabel);
        super.add(idField);
    }
}
