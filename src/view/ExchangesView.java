package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

import controller.ExchangesController;
import model.AccountType;
import model.User;
import model.Exchange;

public class ExchangesView {

    private JFrame frame;
    private JTable exchangesTable;
    private JButton backButton;
    private JButton viewExchange;
    private JButton createNewExchange;
    private JButton deleteExchange;

    JLabel myExchanges;

    JLabel iconLabel;

    JPanel imagePanel = new JPanel();

    private ExchangesController exchangesController;
    private User user;

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

    public ImageIcon getImage(AccountType accountType) {
        BufferedImage myPicture = null;
        try {
            if(accountType == AccountType.ORGANIZER) {
                myPicture = ImageIO.read(new File("E:/Java/OOP-App/MyExchanges.png"));
            }else {
                myPicture = ImageIO.read(new File("E:/Java/OOP-App/MyExchanges1.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newimg = myPicture.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
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

    private Exchange getExchange(){
        Exchange exchange = null;
        int selectedRow = exchangesTable.getSelectedRow();
        if(selectedRow != -1){
            int ID = (int) exchangesTable.getValueAt(selectedRow, 0);
            String name = exchangesTable.getValueAt(selectedRow, 4).toString();
            int budget = (int) exchangesTable.getValueAt(selectedRow, 3);
            Date date = Date.valueOf(exchangesTable.getValueAt(selectedRow, 2).toString());

            exchange = new Exchange(ID, name, budget, date);
        }
        return exchange;
    }

    public ExchangesView(User user) {
        this.user = user;

        frame = new JFrame("Exchanges view");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        exchangesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(exchangesTable);

        myExchanges = new JLabel(getImage(this.user.getAccountType()));
        imagePanel.add(myExchanges);

        backButton = new JButton("Back");
        viewExchange = new JButton("View Exchange");

        backButton.addActionListener( e->exchangesController.changeToMainMenuView(this.user) );
        viewExchange.addActionListener( e->{Exchange exchange = getExchange();
                                            if(exchange != null){
                                               this.exchangesController.changeToMyExchangeView(this.user, exchange);
                                            }});

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        imagePanel.setBackground(Color.WHITE);
        panel.add(imagePanel, BorderLayout.NORTH);
        //scrollPane.setBounds(0, 200, 300, 300);
        panel.add(scrollPane, BorderLayout.CENTER);

        iconLabel = new JLabel(getIcon(this.user.getAccountType()));
        positionIcon();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(viewExchange);

        // Only add "Create New Exchange" and "Delete Exchange" buttons for ORGANIZER
        if (user.getAccountType() == AccountType.ORGANIZER) {
            createNewExchange = new JButton("Create New Exchange");
            deleteExchange = new JButton("Delete Exchange");

            createNewExchange.addActionListener(e->this.exchangesController.changeToNewExchangeView(this.user));
            deleteExchange.addActionListener( e->{int selectedRow = exchangesTable.getSelectedRow();
                                                  if(selectedRow != -1) {
                                                      int result = JOptionPane.showConfirmDialog(frame, "Sure you want to delete " + exchangesTable.getValueAt(selectedRow, 4).toString() + " exchange?",
                                                              "Swing Tester",
                                                              JOptionPane.YES_NO_OPTION,
                                                              JOptionPane.QUESTION_MESSAGE);
                                                      if (result == JOptionPane.YES_OPTION) {
                                                          int ID = (int) exchangesTable.getValueAt(selectedRow, 0);
                                                          this.exchangesController.deleteExchange(ID, this.user);
                                                      }
                                                  }
                                                });

            buttonPanel.add(deleteExchange);
            buttonPanel.add(createNewExchange);
        }
        buttonPanel.add(iconLabel, BorderLayout.EAST);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public void setExchangesController(ExchangesController exchangesController) {
        this.exchangesController = exchangesController;
    }

    public void populateTable(DefaultTableModel tableModel) {
        exchangesTable.setModel(tableModel);
    }

    public void setVisibility(boolean isVisible){
        frame.setVisible(isVisible);
    }
}