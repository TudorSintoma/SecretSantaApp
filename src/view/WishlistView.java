package view;

import controller.ParticipantsController;
import controller.WishlistController;
import model.AccountType;
import model.User;
import model.Exchange;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WishlistView {

    JFrame frame  = new JFrame("Wishlist View");
    JTable productsTable;

    JPanel headerPanel = new JPanel();

    JPanel buttonsPanel = new JPanel();

    JLabel imageLabel;
    JLabel titleLabel;

    JLabel totalPricePanel = new JLabel("Total Price: ");
    JLabel priceLabel;

    JLabel iconLabel;

    JButton backButton = new JButton("Back");
    JButton removeProduct = new JButton("Remove Product");
    JButton addProduct = new JButton("Add Product");

    User user;
    User owner;
    Exchange exchange;

    WishlistController wishlistController;

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
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/wishlist.png"));
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

    public WishlistView(User user, Exchange exchange, User owner, String wishlistName, int totalPrice){
        this.user = user;
        this.owner = owner;
        this.exchange = exchange;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        productsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productsTable);

        backButton.addActionListener(e->this.wishlistController.changeView(this.user, this.exchange, this.owner));

        imageLabel = new JLabel(getImage());
        titleLabel = new JLabel(wishlistName);

        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        titleLabel.setForeground(new Color(37, 152, 152));
        //Cascadia Mono
        //MV Boli

        headerPanel.add(imageLabel);
        headerPanel.add(titleLabel);

        JPanel pricePanel = new JPanel();
        pricePanel.add(totalPricePanel);
        priceLabel = new JLabel(String.valueOf(totalPrice));
        priceLabel.setForeground(new Color(37, 152, 152));
        pricePanel.add(priceLabel);

        buttonsPanel.add(pricePanel, BorderLayout.WEST);
        buttonsPanel.add(backButton);

        if(this.user.getID() == this.owner.getID()){
            removeProduct.addActionListener(e->{int selectedRow = productsTable.getSelectedRow();
                                                if(selectedRow != -1) {
                                                    int result = JOptionPane.showConfirmDialog(frame, "Sure you want to remove " + productsTable.getValueAt(selectedRow, 1).toString() + " from this wishlist?",
                                                            "Swing Tester",
                                                            JOptionPane.YES_NO_OPTION,
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    if (result == JOptionPane.YES_OPTION) {
                                                        int id_entry = (int) productsTable.getValueAt(selectedRow, 5);
                                                        int id_product = (int) productsTable.getValueAt(selectedRow, 0);
                                                        this.wishlistController.removeProduct(id_entry, id_product);
                                                    }
                                                }
            });
            buttonsPanel.add(removeProduct);
            addProduct.addActionListener(e->this.wishlistController.changeToAddProductView(user, exchange));
            buttonsPanel.add(addProduct);
        }

        iconLabel = new JLabel(getIcon(this.user.getAccountType()));
        positionIcon();
        buttonsPanel.add(iconLabel, BorderLayout.EAST);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
    }

    public void setVisibility(boolean isVisible){
        frame.setVisible(isVisible);
    }

    public void setWishlistController(WishlistController wishlistController) {
        this.wishlistController = wishlistController;
    }

    public void populateTable(DefaultTableModel tableModel) {
        System.out.println(1);
        productsTable.setModel(tableModel);
    }

}
