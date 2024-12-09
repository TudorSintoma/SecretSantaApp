package view;

import model.User;
import model.Exchange;
import model.AccountType;
import controller.MyExchangeController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

public class MyExchangeView {

    JFrame frame = new JFrame("My Exchange View");

    JPanel panelImage = new JPanel();
    JPanel panelTitle = new JPanel();

    JPanel panelName = new JPanel();
    JPanel panelDate = new JPanel();
    JPanel panelBudget = new JPanel();
    JPanel buttonsPanel = new JPanel();

    JLabel nameLabel = new JLabel("Name:");
    JLabel dateLabel = new JLabel("Date:");
    JLabel budgetLabel = new JLabel("Budget:");

    JLabel iconLabel;

    JLabel titleLabel;
    JLabel imageLabel;

    JButton backButton = new JButton("Back");
    JButton viewParticipants = new JButton("View Participants");

    JButton viewMyWishlist = new JButton("View My Wishlist");

    JButton updateExchangeInfo = new JButton("Update Exchange Info");
    User user;
    Exchange exchange;
    MyExchangeController myExchangeController;

    JTextField textName = new JTextField();
    JTextField textDate = new JTextField();
    JTextField textBudget = new JTextField();

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
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/gift3.png"));
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


    public MyExchangeView(User user, Exchange exchange){

        this.user = user;
        this.exchange = exchange;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        backButton.addActionListener(e->this.myExchangeController.changeToExchangesView(this.user));
        viewParticipants.addActionListener(e->this.myExchangeController.changeToParticipantsView(this.user, this.exchange));

        imageLabel = new JLabel(getImage());
        titleLabel = new JLabel(this.exchange.getName());
        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        //Cascadia Mono
        titleLabel.setForeground(new Color(255,102,102));

        panelImage.add(imageLabel);
        panelImage.setBounds(0, 0, 200, 200);

        panelTitle.add(titleLabel);
        panelTitle.setBounds(200, 0, 250, 50);

        panelName.add(nameLabel);
        panelDate.add(dateLabel);
        panelBudget.add(budgetLabel);

        buttonsPanel.add(backButton);
        buttonsPanel.add(viewParticipants);

        if(this.user.getAccountType() == AccountType.ORGANIZER){

            textName.setPreferredSize(new Dimension(150, 20));
            textDate.setPreferredSize(new Dimension(150, 20));
            textBudget.setPreferredSize(new Dimension(150, 20));

            textName.setText(this.exchange.getName());
            textDate.setText(this.exchange.getDate().toString());
            textBudget.setText(String.valueOf(this.exchange.getBudget()));

            panelName.add(textName);
            panelDate.add(textDate);
            panelBudget.add(textBudget);

            updateExchangeInfo.addActionListener(e->myExchangeController.updateExchangeInfo(this.exchange));
            buttonsPanel.add(updateExchangeInfo);
        }else
        {
            JLabel infoName = new JLabel(this.exchange.getName());
            JLabel infoDate = new JLabel(this.exchange.getDate().toString());
            JLabel infoBudget = new JLabel(String.valueOf(this.exchange.getBudget()));

            panelName.add(infoName);
            panelDate.add(infoDate);
            panelBudget.add(infoBudget);

            viewMyWishlist.addActionListener(e->myExchangeController.changeToWishlistView(this.user, this.exchange, this.user));
            buttonsPanel.add(viewMyWishlist);
        }
        iconLabel = new JLabel(getIcon(this.user.getAccountType()));
        positionIcon();
        buttonsPanel.add(iconLabel, BorderLayout.EAST);
        buttonsPanel.setBounds(200, 200, 500, 100);


        panelName.setBounds(200, 50, 250, 50);
        panelDate.setBounds(200, 100, 250, 50);
        panelBudget.setBounds(200, 150, 250, 50);

        JPanel panel = new JPanel();

        frame.add(panelImage);
        frame.add(panelTitle);
        frame.add(panelName);
        frame.add(panelDate);
        frame.add(panelBudget);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
        frame.add(panel);
    }

    public String getName(){
        return textName.getText();
    }

    public String getDate(){
        return textDate.getText();
    }

    public String getBudget(){
        return textBudget.getText();
    }

    public void setVisibility(boolean isVisible){
        frame.setVisible(isVisible);
    }

    public void setMyExchangeController(MyExchangeController myExchangeController) {
        this.myExchangeController = myExchangeController;
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

