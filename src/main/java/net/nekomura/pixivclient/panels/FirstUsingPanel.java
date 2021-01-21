package net.nekomura.pixivclient.panels;

import net.nekomura.pixivclient.Main;
import net.nekomura.utils.jixiv.Jixiv;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FirstUsingPanel extends JPanel {
    public FirstUsingPanel() {
        JLabel phpsessidLabel = new JLabel("請輸入你的PIXIV PHPSESSID：");
        phpsessidLabel.setBounds(Main.sc.width / 2 - 400, 325, 800, 40);
        phpsessidLabel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));

        JTextField phpsessidField = new JTextField();
        phpsessidField.setBounds(Main.sc.width / 2 - 400, 360, 800, 40);
        phpsessidField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));

        JLabel userAgentLebel = new JLabel("請輸入你的瀏覽器USER AGENT：");
        userAgentLebel.setBounds(Main.sc.width / 2 - 400, 405, 800, 40);
        userAgentLebel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 20));

        JTextField userAgentField = new JTextField();
        userAgentField.setBounds(Main.sc.width / 2 - 400, 440, 800, 40);
        userAgentField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));

        JButton okButton = new JButton("OK");
        okButton.setBounds(Main.sc.width / 2 - 150, 500, 300, 40);

        super.setLayout(null);
        super.add(phpsessidLabel);
        super.add(userAgentLebel);
        super.add(phpsessidField);
        super.add(userAgentField);
        super.add(okButton);

        okButton.addActionListener(e -> {
            String phpsessid = phpsessidField.getText();
            String userAgent = userAgentField.getText();

            File config = new File("./config/config.json");

            JSONObject configJson = new JSONObject();
            configJson.put("phpsessid", phpsessid);
            configJson.put("user-agent", userAgent);

            try {
                config.getParentFile().mkdirs();
                config.createNewFile();
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(config), StandardCharsets.UTF_8);
                out.write(configJson.toString());
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            Main.mainFrame.getContentPane().removeAll();
            Main.mainFrame.setVisible(false);
            Main.mainFrame.setVisible(true);
            try {
                Main.mainFrame.getContentPane().add(new MainPanel());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            Jixiv.loginByCookie(phpsessid);
            Jixiv.setUserAgent(userAgent);
        });
    }
}
