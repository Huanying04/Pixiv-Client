package net.nekomura.pixivclient;

import net.nekomura.pixivclient.panels.FirstUsingPanel;
import net.nekomura.pixivclient.panels.MainPanel;
import net.nekomura.utils.jixiv.Jixiv;
import net.sf.image4j.codec.ico.ICODecoder;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static Toolkit kit = Toolkit.getDefaultToolkit();
    public static Dimension sc = kit.getScreenSize();
    public static JFrame mainFrame = new JFrame("Pixiv Client");
    public static FirstUsingPanel firstUsingPanel = new FirstUsingPanel();
    public static void main(String[] args) throws IOException {
        File config = new File("config/config.json");

        mainFrame.setIconImage(new ImageIcon(ICODecoder.read(new File("assets/favicon.ico")).get(0)).getImage());
        mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        mainFrame.setResizable(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setVisible(true);

        if (config.exists()) {
            JSONObject configJson = new JSONObject(FileUtils.readFileToString(config, StandardCharsets.UTF_8));
            Jixiv.loginByCookie(configJson.getString("phpsessid"));
            Jixiv.setUserAgent(configJson.getString("user-agent"));

            mainFrame.getContentPane().add(new MainPanel());
        }else {
            mainFrame.add(firstUsingPanel);
        }

        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
