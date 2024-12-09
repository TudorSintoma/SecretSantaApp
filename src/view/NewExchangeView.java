package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.ExchangesController;
import controller.NewExchangeController;
import controller.UserController;
import model.AccountType;
import model.User;
import model.Exchange;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NewExchangeView {

    JFrame frame = new JFrame("Create New Exchange View");

    JPanel panelImage = new JPanel();

    JPanel panelName = new JPanel();
    JPanel panelDate = new JPanel();
    JPanel panelBudget = new JPanel();
    JPanel panelTitle = new JPanel();
    JPanel buttonsPanel = new JPanel();

    JLabel titleLabel = new JLabel("Create New Exchange");
    JLabel imageLabel = new JLabel();
    JLabel iconLabel;

    JLabel labelParam1 = new JLabel("Name: ");
    JTextField textFieldParam1 = new JTextField();
    JLabel labelParam2 = new JLabel("Date: ");
    JTextField textFieldParam2 = new JTextField();
    JLabel labelParam3 = new JLabel("Budget: ");
    JTextField textFieldParam3 = new JTextField();

    JButton createButton = new JButton("Create");
    JButton backButton = new JButton("Back");

    NewExchangeController newExchangeController;
    User user;


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

    public ImageIcon getImage() {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/santa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newimg = myPicture.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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

    public NewExchangeView(User user){
        this.user = user;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        textFieldParam1.setPreferredSize(new Dimension(100, 20));

        textFieldParam2.setPreferredSize(new Dimension(100, 20));

        textFieldParam3.setPreferredSize(new Dimension(100, 20));

        imageLabel = new JLabel(getImage());
        panelImage.add(imageLabel);
        panelImage.setBounds(0, 0, 200, 200);

        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        //Cascadia Mono
        titleLabel.setForeground(new Color(255,102,102));
        panelTitle.add(titleLabel);
        panelTitle.setBounds(200, 0, 250, 50);

        panelName.add(labelParam1);
        panelName.add(textFieldParam1);

        panelDate.add(labelParam2);
        panelDate.add(textFieldParam2);

        panelBudget.add(labelParam3);
        panelBudget.add(textFieldParam3);

        panelName.setBounds(200, 50, 250, 50);
        panelDate.setBounds(200, 100, 250, 50);
        panelBudget.setBounds(200, 150, 250, 50);

        createButton.addActionListener(e->newExchangeController.createExchange());
        backButton.addActionListener(e->newExchangeController.changeToExchangesView(this.user));

        iconLabel = new JLabel(getIcon(this.user.getAccountType()));
        positionIcon();
        buttonsPanel.add(backButton);
        buttonsPanel.add(createButton);
        buttonsPanel.add(iconLabel, BorderLayout.EAST);
        buttonsPanel.setBounds(200, 200, 500, 100);

        JPanel panel = new JPanel();

        frame.add(panelImage);
        frame.add(panelTitle);
        frame.add(panelName);
        frame.add(panelDate);
        frame.add(panelBudget);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.add(panel);
    }

    public String getParam1() {
        return textFieldParam1.getText();
    }

    public String getParam2() {
        return textFieldParam2.getText();
    }

    public String getParam3() {
        return textFieldParam3.getText();
    }

    public void setVisibility(boolean isVisible){
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

    public void setNewExchangeController(NewExchangeController newExchangeController) {
        this.newExchangeController = newExchangeController;
    }
}
