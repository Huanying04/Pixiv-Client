package net.nekomura.pixivclient.panels;

import net.nekomura.pixivclient.Main;
import net.nekomura.utils.jixiv.FollowingNewWork;
import net.nekomura.utils.jixiv.Pixiv;
import net.nekomura.utils.jixiv.Rank;
import net.nekomura.utils.jixiv.artworks.Illustration;
import net.nekomura.utils.jixiv.enums.artwork.PixivImageSize;
import net.sf.image4j.codec.ico.ICODecoder;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends PixivPagePanel {
    public static JFrame illustrationFrame;
    public MainPanel() throws IOException {
        int bookmarkUserLatestWorkPage = 1;

        JLabel bookmarkedUsersLatestArtworksLabel = new JLabel("已關注用戶的作品" + " (第" + bookmarkUserLatestWorkPage + "頁)");
        bookmarkedUsersLatestArtworksLabel.setBounds(20 + 25 + 164, 150, 600, 30);
        bookmarkedUsersLatestArtworksLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));

        FollowingNewWork bookmarkUserLatestArtworks = Pixiv.getFollowingNewWorks(bookmarkUserLatestWorkPage);

        List<byte[]> bookmarkUserLatestArtworkImages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bookmarkUserLatestArtworkImages.add(Illustration.getInfo(bookmarkUserLatestArtworks.get(i).getID()).getImage(0, PixivImageSize.Thumb));
        }

        List<JLabel> bookmarkUserLatestArtworkIllustrations = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bookmarkUserLatestArtworkIllustrations.add(new JLabel(new ImageIcon(new ImageIcon(bookmarkUserLatestArtworkImages.get(i)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));
            if (i < 5) {
                bookmarkUserLatestArtworkIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * i + 20 * i, 150 + 30 + 10, 200, 200);
            }else if (i < 10) {
                bookmarkUserLatestArtworkIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 5) + 20 * (i - 5), 150 + 30 + 10 + 200 + 10, 200, 200);
            }else if (i < 15) {
                bookmarkUserLatestArtworkIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 10) + 20 * (i - 10), 150 + 30 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else {
                bookmarkUserLatestArtworkIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 15) + 20 * (i - 15), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }
                        int finalI = i;
            bookmarkUserLatestArtworkIllustrations.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        illustrationFrame = new JFrame(String.valueOf(bookmarkUserLatestArtworks.get(finalI).getID()));
                        illustrationFrame.setSize(1280, 720);
                        illustrationFrame.setIconImage(new ImageIcon(ICODecoder.read(new File("assets/favicon.ico")).get(0)).getImage());
                        illustrationFrame.setResizable(false);
                        illustrationFrame.getContentPane().add(new IllustrationPagePanel(bookmarkUserLatestArtworks.get(finalI).getID()));
                        illustrationFrame.setVisible(true);
                        illustrationFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }catch (Throwable ignored) {}
                }
            });
            bookmarkUserLatestArtworkIllustrations.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        JLabel recommendArtworksLabel = new JLabel("推薦作品");
        recommendArtworksLabel.setBounds(20 + 25 + 164, 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 600, 30);
        recommendArtworksLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));

        JSONArray recommends = Pixiv.mainpageIllustrations().getJSONObject("page").getJSONObject("recommend").getJSONArray("ids");

        List<byte[]> recommendImages = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            recommendImages.add(Illustration.getInfo(Integer.parseInt(recommends.getString(i))).getImage(0, PixivImageSize.Thumb));
        }

        List<JLabel> recommendIllustrations = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            recommendIllustrations.add(new JLabel(new ImageIcon(new ImageIcon(recommendImages.get(i)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));
            if (i < 6) {
                recommendIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * i + 20 * i, 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10, 200, 200);
            }else if (i < 12) {
                recommendIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 6) + 20 * (i - 6), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10, 200, 200);
            }else {
                recommendIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 12) + 20 * (i - 12), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }
            int finalI = i;
            recommendIllustrations.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        illustrationFrame = new JFrame(recommends.getString(finalI));
                        illustrationFrame.setSize(1280, 720);
                        illustrationFrame.setIconImage(new ImageIcon(ICODecoder.read(new File("assets/favicon.ico")).get(0)).getImage());
                        illustrationFrame.setResizable(true);
                        illustrationFrame.getContentPane().add(new IllustrationPagePanel(Integer.parseInt(recommends.getString(finalI))));
                        illustrationFrame.setVisible(true);
                        illustrationFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }catch (Throwable ignored) {}
                }
            });
            recommendIllustrations.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        JButton refreshRecommendButton = new JButton(new ImageIcon(new ImageIcon("assets/refresh.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        refreshRecommendButton.setBounds(20 + 25 + 164 + recommendArtworksLabel.getPreferredSize().width + 10, 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 30, 30);
        refreshRecommendButton.setBackground(Color.WHITE);
        refreshRecommendButton.setBorderPainted(false);
        refreshRecommendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        refreshRecommendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONArray recommends;
                try {
                    recommends = Pixiv.mainpageIllustrations().getJSONObject("page").getJSONObject("recommend").getJSONArray("ids");
                    List<byte[]> recommendImages = new ArrayList<>();
                    for (int i = 0; i < 18; i++) {
                        recommendImages.add(Illustration.getInfo(Integer.parseInt(recommends.getString(i))).getImage(0, PixivImageSize.Thumb));
                    }
                    for (int i = 0; i < 18; i++) {
                        recommendIllustrations.get(i).setIcon(new ImageIcon(new ImageIcon(recommendImages.get(i)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                        int finalI = i;
                        recommendIllustrations.get(i).removeMouseListener(recommendIllustrations.get(i).getMouseListeners()[0]);
                        recommendIllustrations.get(i).addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                try {
                                    illustrationFrame = new JFrame(recommends.getString(finalI));
                                    illustrationFrame.setSize(1280, 720);
                                    illustrationFrame.setIconImage(new ImageIcon(ICODecoder.read(new File("assets/favicon.ico")).get(0)).getImage());
                                    illustrationFrame.setResizable(true);
                                    illustrationFrame.getContentPane().add(new IllustrationPagePanel(Integer.parseInt(recommends.getString(finalI))));
                                    illustrationFrame.setVisible(true);
                                    illustrationFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                }catch (IOException | ParseException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        });
                        recommendIllustrations.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        JLabel rankLabel = new JLabel("今日排行榜(前50)");
        rankLabel.setBounds(20 + 25 + 164, 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 600, 30);
        rankLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 30));

        Rank rank = Pixiv.rank(1);
        List<byte[]> rankImages = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            rankImages.add(Illustration.getInfo(rank.getInfo(i).getId()).getImage(0, PixivImageSize.Thumb));
        }

        List<JLabel> rankIllustrations = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            rankIllustrations.add(new JLabel(new ImageIcon(new ImageIcon(rankImages.get(i)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))));
            if (i < 5) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * i + 20 * i, 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10, 200, 200);
            }else if (i < 10) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 5) + 20 * (i - 5), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10, 200, 200);
            }else if (i < 15) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 10) + 20 * (i - 10), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else if (i < 20) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 15) + 20 * (i - 15), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else if (i < 25) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 20) + 20 * (i - 20), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else if (i < 30) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 25) + 20 * (i - 25), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else if (i < 35) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 30) + 20 * (i - 30), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else if (i < 40) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 35) + 20 * (i - 35), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else if (i < 45) {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 40) + 20 * (i - 40), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }else {
                rankIllustrations.get(i).setBounds(20 + 25 + 164 + 200 * (i - 45) + 20 * (i - 45), 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10, 200, 200);
            }
            int finalI = i;
            rankIllustrations.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        illustrationFrame = new JFrame(String.valueOf(rank.getInfo(finalI).getId()));
                        illustrationFrame.setSize(1280, 720);
                        illustrationFrame.setIconImage(new ImageIcon(ICODecoder.read(new File("assets/favicon.ico")).get(0)).getImage());
                        illustrationFrame.setResizable(true);
                        illustrationFrame.getContentPane().add(new IllustrationPagePanel(rank.getInfo(finalI).getId()));
                        illustrationFrame.setVisible(true);
                        illustrationFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    }catch (Throwable err) {}
                }
            });
            rankIllustrations.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        JPanel scrollPanel = new PixivPagePanel();

        scrollPanel.setLocation(0, 0);
        scrollPanel.setPreferredSize(new Dimension(Main.sc.width, 150 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 30 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10 + 200 + 10));

        scrollPanel.add(bookmarkedUsersLatestArtworksLabel);
        scrollPanel.add(recommendArtworksLabel);
        scrollPanel.add(rankLabel);
        scrollPanel.add(refreshRecommendButton);

        for (int i = 0; i < 20; i++) {
            scrollPanel.add(bookmarkUserLatestArtworkIllustrations.get(i));
        }

        for (int i = 0; i < 18; i++) {
            scrollPanel.add(recommendIllustrations.get(i));
        }

        for (int i = 0; i < 50; i++) {
            scrollPanel.add(rankIllustrations.get(i));
        }

        JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setSize(Main.sc.width, Main.sc.height - 100);
        pane.setLocation(0, 0);
        pane.getViewport().add(scrollPanel, BorderLayout.CENTER);

        pane.getVerticalScrollBar().setUnitIncrement(16);

        super.add(pane);
    }
}
