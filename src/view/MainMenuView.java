package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import controller.MainMenuController;
import model.AccountType;
import model.User;

public class MainMenuView {
    JFrame frame = new JFrame("Main Menu View");

    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    JLabel titleLabel;

    JLabel iconLabel;

    JButton myExchanges = new JButton("My Exchanges");
    JButton myAccount = new JButton("My Account");
    JButton logout = new JButton("Logout");

    User user;
    MainMenuController mainMenuController;

    public ImageIcon getIcon(AccountType accountType) {
        BufferedImage myPicture = null;
        try {
            if(accountType == AccountType.ORGANIZER){
                myPicture = ImageIO.read(new File("E:/Java/OOP-App/organizer.png"));
            }else{
                myPicture = ImageIO.read(new File("E:/Java/OOP-App/participant.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newimg = myPicture.getScaledInstance(75, 50, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(newimg);
        return img;
    }

    private ImageIcon getImage() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newimg = myPicture.getScaledInstance(300, 120, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(newimg);
        return img;
    }

    private void positionIcon(){
        int iconWidth = 75;
        int iconHeight = 50;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = frameWidth - iconWidth - 5; // 5 pixels from the right
        int y = frameHeight - iconHeight - 5; // 5 pixels from the bottom

        iconLabel.setBounds(x, y, iconWidth, iconHeight);
    }

    public MainMenuView(User user) {
        this.user = user;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        myExchanges.addActionListener(e -> mainMenuController.changeToExchangesView(this.user));
        myAccount.addActionListener(e -> mainMenuController.changeToMyAccountView(this.user));
        logout.addActionListener(e -> mainMenuController.changeToLoginView());

        titleLabel = new JLabel(getImage());

        panel1.add(titleLabel, BorderLayout.CENTER);
        panel2.add(myExchanges, BorderLayout.CENTER);
        panel3.add(myAccount, BorderLayout.CENTER);
        panel4.add(logout, BorderLayout.CENTER);

        iconLabel = new JLabel(getIcon(this.user.getAccountType()));
        positionIcon();

        JPanel panel = new JPanel();
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        panel.add(iconLabel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public void setVisibility(boolean isVisible) {
        frame.setVisible(isVisible);
    }
}