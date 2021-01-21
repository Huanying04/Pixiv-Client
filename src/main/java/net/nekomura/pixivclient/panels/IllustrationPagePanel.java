package net.nekomura.pixivclient.panels;

import net.nekomura.pixivclient.Main;
import net.nekomura.utils.jixiv.IllustrationInfo;
import net.nekomura.utils.jixiv.Pixiv;
import net.nekomura.utils.jixiv.artworks.Illustration;
import net.nekomura.utils.jixiv.enums.artwork.PixivImageSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;

public class IllustrationPagePanel extends PixivPagePanel {
    public IllustrationPagePanel(int id) throws IOException, ParseException {
        final int[] page = {0};
        int width;
        final IllustrationInfo[] info = {Illustration.getInfo(id)};
        byte[] image = info[0].getImage(page[0], PixivImageSize.Original);
        ImageIcon illustration = new ImageIcon(image);

        final JLabel[] illustLabel = new JLabel[1];

        if (illustration.getIconWidth() >= Main.sc.width * 3 / 4) {
            width = Main.sc.width * 3 / 4;
            illustLabel[0] = new JLabel(new ImageIcon(illustration.getImage().getScaledInstance(Main.sc.width * 3 / 4, illustration.getIconHeight() * (Main.sc.width * 3 / 4) / illustration.getIconWidth(), java.awt.Image.SCALE_SMOOTH)));
            illustLabel[0].setBounds(20 + 25 + 120, 130, width, illustration.getIconHeight() * (Main.sc.width * 3 / 4) / illustration.getIconWidth());
        }else {
            width = illustration.getIconWidth();
            illustLabel[0] = new JLabel(illustration);
            illustLabel[0].setBounds(20 + 25 + 120, 130, width, illustration.getIconHeight());
        }

        JButton previousPage = new JButton("＜");
        JButton nextButton = new JButton("＞");

        previousPage.setBounds(20 + 25 + 120, 90, 50, 35);
        nextButton.setBounds(20 + 25 + 120 + 15 + width - 50, 90, 50, 35);

        previousPage.setEnabled(false);

        if (info[0].getPageCount() <= 1) {
            nextButton.setEnabled(false);
        }

        final JLabel[] pageLabel = {new JLabel("1 / " + info[0].getPageCount())};
        pageLabel[0].setBounds(20 + 25 + 120 + 50, 90, width, 35);
        pageLabel[0].setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel(info[0].getTitle());
        title.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30, Main.sc.width - (20 + 25 + 120 + 10), 42);
        title.setFont(new Font("MS PGothic", Font.PLAIN, 42));
        title.setHorizontalAlignment(JLabel.LEFT);

        JLabel description = new JLabel("<html>" + info[0].getDescription() + "</html>");
        description.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 20, Main.sc.width * 3 / 4, 600);
        description.setFont(new Font("MS PGothic", Font.PLAIN, 18));
        description.setHorizontalAlignment(JLabel.LEFT);
        description.setVerticalAlignment(JLabel.TOP);

        String[] tags = info[0].getTags();
        JLabel tagsLabel = new JLabel("#" + tags[0]);
        tagsLabel.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height, Main.sc.width * 3 / 4, 200);
        tagsLabel.setFont(new Font("MS PGothic", Font.PLAIN, 18));
        tagsLabel.setHorizontalAlignment(JLabel.LEFT);
        tagsLabel.setVerticalAlignment(JLabel.TOP);
        for (int i = 1; i < tags.length; i++) {
            tagsLabel.setText(tagsLabel.getText() + " #" + tags[i]);
        }

        JLabel uploadDateLabel = new JLabel();
        Calendar uploadDate = info[0].getUploadDateCalendar();
        String hour;
        String minute;
        if (uploadDate.get(Calendar.HOUR_OF_DAY) < 10) {
            hour = "0" + uploadDate.get(Calendar.HOUR_OF_DAY);
        }else {
            hour = "" + uploadDate.get(Calendar.HOUR_OF_DAY);
        }
        if (uploadDate.get(Calendar.MINUTE) < 10) {
            minute = "0" + uploadDate.get(Calendar.MINUTE);
        }else {
            minute = "" + uploadDate.get(Calendar.MINUTE);
        }
        uploadDateLabel.setText(uploadDate.get(Calendar.YEAR) + "年" + (uploadDate.get(Calendar.MONTH) + 1) + "月" + uploadDate.get(Calendar.DAY_OF_MONTH) + "日 " + hour + ":" + minute + " JST");
        uploadDateLabel.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + tagsLabel.getPreferredSize().height + 10, Main.sc.width * 3 / 4, 50);
        uploadDateLabel.setHorizontalAlignment(JLabel.LEFT);
        uploadDateLabel.setVerticalAlignment(JLabel.TOP);

        JButton likeButton = new JButton();
        if (info[0].isLiked()) {
            likeButton.setEnabled(false);
            likeButton.setText("已喜歡");
        }else {
            likeButton.setEnabled(true);
            likeButton.setText("喜歡");
        }
        likeButton.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20, 80, 35);

        JButton bookmarkButton = new JButton();
        if (info[0].isBookmarked()) {
            bookmarkButton.setText("已收藏");
        }else {
            bookmarkButton.setText("收藏");
        }
        bookmarkButton.setBounds(20 + 25 + 120 + 80 + 10, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20, 80, 35);

        JButton saveThisButton = new JButton("儲存這張");
        saveThisButton.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + likeButton.getPreferredSize().height + 20, 100, 50);

        JButton saveAllButton = new JButton("儲存全部");
        saveAllButton.setBounds(20 + 25 + 120 + 100 + 10, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + likeButton.getPreferredSize().height + 20, 100, 50);

        JButton openInBrowserButton = new JButton("在瀏覽器中開啟");
        openInBrowserButton.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20, 150, 50);

        JLabel authorIcon = new JLabel(new ImageIcon(new ImageIcon(Pixiv.getUserInfo(info[0].getAuthorID()).getAvatarSmall()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        authorIcon.setBounds(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20, 80, 80);

        JLabel authorName = new JLabel(info[0].getAuthorName());
        authorName.setBounds(20 + 25 + 120 + 80 + 20, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20, 800, 80);
        authorName.setFont(new Font("MS PGothic", Font.PLAIN, 40));
        authorName.setHorizontalAlignment(JLabel.LEFT);
        authorName.setVerticalAlignment(JLabel.CENTER);

        JButton addAuthorBookmarkButton = new JButton();
        if (Pixiv.getUserInfo(info[0].getAuthorID()).isFollowed()) {
            addAuthorBookmarkButton.setText("取消關注");
        }else {
            addAuthorBookmarkButton.setText("關注");
        }
        addAuthorBookmarkButton.setBounds(20 + 25 + 120 + 80 + 20 + authorName.getPreferredSize().width + 20, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20 + 20, 120, 40);

        JPanel scrollPanel = new PixivPagePanel();

        scrollPanel.setLocation(0, 0);
        scrollPanel.setPreferredSize(new Dimension(Main.sc.width, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + openInBrowserButton.getPreferredSize().height + 20 + authorIcon.getPreferredSize().height + 10 + 200 + 30));

        scrollPanel.add(illustLabel[0]);
        scrollPanel.add(previousPage);
        scrollPanel.add(pageLabel[0]);
        scrollPanel.add(nextButton);
        scrollPanel.add(title);
        scrollPanel.add(description);
        scrollPanel.add(tagsLabel);
        scrollPanel.add(uploadDateLabel);
        scrollPanel.add(likeButton);
        scrollPanel.add(bookmarkButton);
        scrollPanel.add(saveThisButton);
        scrollPanel.add(saveAllButton);
        scrollPanel.add(openInBrowserButton);
        scrollPanel.add(authorIcon);
        scrollPanel.add(authorName);
        scrollPanel.add(addAuthorBookmarkButton);

        JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setSize(Main.sc.width, Main.sc.height - 100);
        pane.setLocation(0, 0);
        pane.getViewport().add(scrollPanel, BorderLayout.CENTER);

        pane.getVerticalScrollBar().setUnitIncrement(16);

        super.add(pane);

        nextButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page[0]++;
                try {
                    byte[] image = info[0].getImage(page[0], PixivImageSize.Original);

                    ImageIcon illustration = new ImageIcon(image);

                    if (illustration.getIconWidth() >= Main.sc.width * 3 / 4) {
                        illustLabel[0].setIcon(new ImageIcon(illustration.getImage().getScaledInstance(Main.sc.width * 3 / 4, illustration.getIconHeight() * (Main.sc.width * 3 / 4) / illustration.getIconWidth(), java.awt.Image.SCALE_SMOOTH)));
                        illustLabel[0].setBounds(20 + 25 + 120, 130, Main.sc.width * 3 / 4, illustration.getIconHeight() * (Main.sc.width * 3 / 4) / illustration.getIconWidth());
                    }else {
                        illustLabel[0].setIcon(illustration);
                        illustLabel[0].setBounds(20 + 25 + 120, 130, illustration.getIconWidth(), illustration.getIconHeight());
                    }

                    title.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30);
                    description.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 20);
                    tagsLabel.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height);
                    uploadDateLabel.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + tagsLabel.getPreferredSize().height + 10);
                    likeButton.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20);
                    bookmarkButton.setLocation(20 + 25 + 120 + 80 + 10, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20);
                    saveThisButton.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + likeButton.getPreferredSize().height + 20);
                    saveAllButton.setLocation(20 + 25 + 120 + 100 + 10, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + likeButton.getPreferredSize().height + 20);
                    openInBrowserButton.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20);
                    authorIcon.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20);
                    authorName.setLocation(20 + 25 + 120 + 80 + 20, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20);
                    addAuthorBookmarkButton.setLocation(20 + 25 + 120 + 80 + 20 + authorName.getPreferredSize().width + 20, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20 + 20);

                    scrollPanel.setPreferredSize(new Dimension(Main.sc.width, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + openInBrowserButton.getPreferredSize().height + 20 + authorIcon.getPreferredSize().height + 10 + 200 + 30));

                    if (page[0] < info[0].getPageCount() - 1) {
                        nextButton.setEnabled(true);
                    }else {
                        nextButton.setEnabled(false);
                    }

                    if (page[0] > 0) {
                        previousPage.setEnabled(true);
                    }else {
                        previousPage.setEnabled(false);
                    }

                    pageLabel[0].setText((page[0] + 1) + " / " + info[0].getPageCount());
                    pageLabel[0].setBounds(20 + 25 + 120 + 50, 90, width, 35);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        previousPage.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                page[0]--;
                try {
                    byte[] image = info[0].getImage(page[0], PixivImageSize.Original);

                    ImageIcon illustration = new ImageIcon(image);

                    if (illustration.getIconWidth() >= Main.sc.width * 3 / 4) {
                        illustLabel[0].setIcon(new ImageIcon(illustration.getImage().getScaledInstance(Main.sc.width * 3 / 4, illustration.getIconHeight() * (Main.sc.width * 3 / 4) / illustration.getIconWidth(), java.awt.Image.SCALE_SMOOTH)));
                        illustLabel[0].setBounds(20 + 25 + 120, 130, Main.sc.width * 3 / 4, illustration.getIconHeight() * (Main.sc.width * 3 / 4) / illustration.getIconWidth());
                    }else {
                        illustLabel[0].setIcon(illustration);
                        illustLabel[0].setBounds(20 + 25 + 120, 130, illustration.getIconWidth(), illustration.getIconHeight());
                    }

                    title.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30);
                    description.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 20);
                    tagsLabel.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height);
                    uploadDateLabel.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + tagsLabel.getPreferredSize().height + 10);
                    likeButton.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20);
                    bookmarkButton.setLocation(20 + 25 + 120 + 80 + 10, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20);
                    saveThisButton.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + likeButton.getPreferredSize().height + 20);
                    saveAllButton.setLocation(20 + 25 + 120 + 100 + 10, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + likeButton.getPreferredSize().height + 20);
                    openInBrowserButton.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20);
                    authorIcon.setLocation(20 + 25 + 120, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20);
                    authorName.setLocation(20 + 25 + 120 + 80 + 20, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20);
                    addAuthorBookmarkButton.setLocation(20 + 25 + 120 + 80 + 20 + authorName.getPreferredSize().width + 20, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + 50 + 20 + openInBrowserButton.getPreferredSize().height + 20 + 20);

                    scrollPanel.setPreferredSize(new Dimension(Main.sc.width, 130 + illustLabel[0].getHeight() + 30 + 42 + 50 + description.getPreferredSize().height + 10 + 30 + tagsLabel.getPreferredSize().height + 20 + 35 + 20 + saveThisButton.getPreferredSize().height + 20 + openInBrowserButton.getPreferredSize().height + 20 + authorIcon.getPreferredSize().height + 10 + 200 + 30));

                    if (page[0] < info[0].getPageCount() - 1) {
                        nextButton.setEnabled(true);
                    }else {
                        nextButton.setEnabled(false);
                    }

                    if (page[0] > 0) {
                        previousPage.setEnabled(true);
                    }else {
                        previousPage.setEnabled(false);
                    }

                    pageLabel[0].setText((page[0] + 1) + " / " + info[0].getPageCount());
                    pageLabel[0].setBounds(20 + 25 + 120 + 50, 90, width, 35);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        likeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Illustration.like(id);
                    likeButton.setText("已喜歡");
                    likeButton.setEnabled(false);
                    info[0] = Illustration.getInfo(id);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        bookmarkButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (info[0].isBookmarked()) {
                    try {
                        Illustration.removeBookmark(id);
                        bookmarkButton.setText("收藏");
                        info[0] = Illustration.getInfo(id);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else {
                    try {
                        Illustration.addBookmark(id);
                        bookmarkButton.setText("已收藏");
                        info[0] = Illustration.getInfo(id);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        saveThisButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    info[0].download("./saves/" + id + "_p" + page[0] + "." + info[0].getImageFileFormat(page[0]), page[0], PixivImageSize.Original);
                    JOptionPane.showMessageDialog(null, "儲存完畢", "儲存完畢", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        saveAllButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    info[0].downloadAll("./saves", PixivImageSize.Original);
                    JOptionPane.showMessageDialog(null, "儲存完畢", "儲存完畢", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        openInBrowserButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URL("https://www.pixiv.net/artworks/" + id).toURI());
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            }
        });

        addAuthorBookmarkButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Pixiv.getUserInfo(info[0].getAuthorID()).isFollowed()) {
                        Pixiv.removeBookmarkUser(info[0].getAuthorID());
                        addAuthorBookmarkButton.setText("關注");
                    }else {
                        Pixiv.addBookmarkUser(info[0].getAuthorID());
                        addAuthorBookmarkButton.setText("取消關注");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
