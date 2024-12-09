package view;

import model.AccountType;
import model.User;
import model.Exchange;
import controller.ParticipantsController;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ParticipantsView {

    JFrame frame = new JFrame("Participants View");

    JTable participantsTable;

    JPanel headerPanel = new JPanel();

    JPanel buttonsPanel = new JPanel();

    JLabel imageLabel;
    JLabel titleLabel;

    JLabel iconLabel;

    JButton backButton = new JButton("Back");
    JButton viewParticipantWishlist = new JButton("View Participant's Wishlist");
    JButton removeParticipant = new JButton("Remove Participant");
    JButton addParticipant = new JButton("Add Participant");

    User user;
    Exchange exchange;
    ParticipantsController participantsController;

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
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/elves.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newimg = myPicture.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
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

    private User getOwner(int selectedRow){
        int ID = (int) participantsTable.getValueAt(selectedRow, 0);
        String username = participantsTable.getValueAt(selectedRow, 1).toString();
        String password = participantsTable.getValueAt(selectedRow, 2).toString();
        String email = participantsTable.getValueAt(selectedRow, 3).toString();
        AccountType accountType = AccountType.PARTICIPANT;

        User u = new User(ID, username, password, email, accountType);
        return u;
    }

    public ParticipantsView(User user, Exchange exchange){
        this.user = user;
        this.exchange = exchange;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        participantsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(participantsTable);

        backButton.addActionListener(e->this.participantsController.changeToMyExchangeView(this.user, this.exchange));
        buttonsPanel.add(backButton);

        imageLabel = new JLabel(getImage());
        titleLabel = new JLabel(this.exchange.getName() + " participants");

        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        titleLabel.setForeground(new Color(74, 197, 114));
        //Cascadia Mono
        //MV Boli

        headerPanel.add(imageLabel);
        headerPanel.add(titleLabel);

        if(this.user.getAccountType() == AccountType.ORGANIZER){
            removeParticipant.addActionListener(e->{int selectedRow = participantsTable.getSelectedRow();
                                                    if(selectedRow != -1) {
                                                        int result = JOptionPane.showConfirmDialog(frame, "Sure you want to remove " + participantsTable.getValueAt(selectedRow, 1).toString() + " from this exchange?",
                                                                "Swing Tester",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (result == JOptionPane.YES_OPTION) {
                                                            int ID = (int) participantsTable.getValueAt(selectedRow, 0);
                                                            this.participantsController.removeParticipant(ID);
                                                        }
                                                    }
            });
            buttonsPanel.add(removeParticipant);
            addParticipant.addActionListener(e->this.participantsController.changeToAddParticipantView(this.user, this.exchange));
            buttonsPanel.add(addParticipant);
        }else{
            viewParticipantWishlist.addActionListener(e->{int selectedRow = participantsTable.getSelectedRow();
                                                          if(selectedRow != -1) {
                                                              User owner = getOwner(selectedRow);
                                                              this.participantsController.changeToWishlistView(this.user, this.exchange, owner);
                                                          }
            });
            buttonsPanel.add(viewParticipantWishlist);
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

    public void setParticipantsController(ParticipantsController participantsController) {
        this.participantsController = participantsController;
    }

    public void populateTable(DefaultTableModel tableModel) {
        participantsTable.setModel(tableModel);
    }
}
