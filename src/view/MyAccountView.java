package view;

import controller.MainMenuController;
import model.User;
import controller.MyAccountController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyAccountView {

    JFrame frame = new JFrame("My Account View");

    JPanel panelAccount = new JPanel();

    JPanel panelUsername = new JPanel();
    JPanel panelPassword = new JPanel();
    JPanel panelEmail = new JPanel();
    JPanel panelAccountType = new JPanel();
    JPanel buttonsPanel = new JPanel();
    User user;


    JButton backButton = new JButton("Back");
    JButton updateUserInfoButton = new JButton("Update User Info");

    JLabel accountLabel;

    JLabel usernameLabel = new JLabel("   User Name:");
    JTextField textUsername = new JTextField();

    JLabel passwordLabel = new JLabel("   Password:");
    JTextField textPassword = new JTextField();

    JLabel emailLabel = new JLabel("          Email:");
    JTextField textEmail = new JTextField();

    JLabel accountTypeLabel = new JLabel("Account Type:  ");
    JLabel labelAccountType;

    MyAccountController myAccountController;

    public ImageIcon getImage() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/account.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newimg = myPicture.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(newimg);
        return img;
    }
    public MyAccountView(User user){
        this.user = user;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        textUsername.setPreferredSize(new Dimension(150, 20));
        textPassword.setPreferredSize(new Dimension(150, 20));
        textEmail.setPreferredSize(new Dimension(150, 20));

        labelAccountType = new JLabel(this.user.getAccountType().toString());

        backButton.addActionListener(e->this.myAccountController.changeToMainMenuView(this.user));
        updateUserInfoButton.addActionListener(e->this.myAccountController.updateUserInfo(this.user));

        accountLabel = new JLabel(getImage());
        panelAccount.add(accountLabel);
        panelAccount.setBounds(0, 0, 200, 200);
        //panelAccount.setBackground(Color.BLUE);

        panelUsername.add(usernameLabel);
        panelUsername.add(textUsername);
        panelUsername.setBounds(200, 0, 250, 50);
        //panelUsername.setBackground(Color.RED);

        panelPassword.add(passwordLabel);
        panelPassword.add(textPassword);
        panelPassword.setBounds(200, 50, 250, 50);
        //panelPassword.setBackground(Color.GREEN);

        panelEmail.add(emailLabel);
        panelEmail.add(textEmail);
        panelEmail.setBounds(200, 100, 250, 50);
        //panelEmail.setBackground(Color.YELLOW);

        panelAccountType.add(accountTypeLabel);
        panelAccountType.add(labelAccountType);
        panelAccountType.setBounds(200, 150, 250, 50);
        //panelAccountType.setBackground(Color.PINK);

        buttonsPanel.add(backButton, BorderLayout.SOUTH);
        buttonsPanel.add(updateUserInfoButton, BorderLayout.SOUTH);
        buttonsPanel.setBounds(200, 200, 250, 50);
        //buttonsPanel.setBackground(Color.ORANGE);


        JPanel panel = new JPanel();

        frame.add(panelAccount);
        frame.add(panelUsername);
        frame.add(panelPassword);
        frame.add(panelEmail);
        frame.add(panelAccountType);
        frame.add(buttonsPanel);
        frame.add(panel);
    }

    public String getUsername(){
        return textUsername.getText();
    }
    public String getPassword(){
        return textPassword.getText();
    }
    public String getEmail(){
        return textEmail.getText();
    }

    public void setUsername(String username){
        textUsername.setText(username);
    }

    public void setPassword(String password){
        textPassword.setText(password);
    }

    public void setEmail(String email){
        textEmail.setText(email);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMyAccountController(MyAccountController myAccountController) {
        this.myAccountController = myAccountController;
    }

    public void setVisibility(boolean isVisible) {
        frame.setVisible(isVisible);
    }

    public void showMessage(String message, int option){
        if( option == 0) {
            JOptionPane.showMessageDialog(frame, message, "Swing Tester", JOptionPane.ERROR_MESSAGE);
        }
        if(option ==1){
            JOptionPane.showMessageDialog(frame, message, "Swing Tester", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
