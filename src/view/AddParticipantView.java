package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import controller.AddParticipantController;
import model.AccountType;
import model.User;
import model.Exchange;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddParticipantView {

    JFrame frame = new JFrame("Add Participant View");

    JPanel headerPanel = new JPanel();

    JPanel searchableListPanel;

    JList<String> userList;

    JPanel buttonsPanel = new JPanel();

    JLabel imageLabel;
    JLabel titleLabel;
    JLabel iconLabel;

    JButton backButton = new JButton("Back");
    JButton addParticipantButton = new JButton("Add Participant");

    User user;
    Exchange exchange;

    AddParticipantController addParticipantController;

    private DefaultListModel<String> listModel;

    public ImageIcon getIcon(AccountType accountType) {
        BufferedImage myPicture = null;
        try {
            if(accountType == AccountType.ORGANIZER){
                myPicture = ImageIO.read(new File("E:/Java/OOP-App/organizer.png"));
            } else {
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
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/part.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newimg = myPicture.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(newimg);
        return img;
    }

    private void positionIcon() {
        int iconWidth = 75;
        int iconHeight = 50;
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int x = frameWidth - iconWidth - 5; // 5 pixels from the right
        int y = frameHeight - iconHeight - 5; // 5 pixels from the bottom

        iconLabel.setBounds(x, y, iconWidth, iconHeight);
    }

    public JPanel createSearchableListPanel(DefaultListModel<String> listModel) {
        JPanel panel = new JPanel(new BorderLayout());

        userList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(userList);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(100, 20));
        JButton searchButton = new JButton("Search");
        JButton orderASC = new JButton("Order ASC");
        JButton orderDSC = new JButton("Order DESC");

        orderASC.addActionListener(e->addParticipantController.order(1));
        orderDSC.addActionListener(e->addParticipantController.order(2));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().toLowerCase();
                filterList(listModel, searchText);
            }
        });

        JPanel searchPanel = new JPanel();  // Create a new JPanel to hold the search components
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(orderASC);
        searchPanel.add(orderDSC);

        panel.add(searchPanel, BorderLayout.NORTH);  // Add the searchPanel to the NORTH
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }


    private void filterList(DefaultListModel<String> listModel, String searchText) {
        DefaultListModel<String> filteredModel = new DefaultListModel<>();
        for (int i = 0; i < listModel.size(); i++) {
            String item = listModel.getElementAt(i).toLowerCase();
            if (item.contains(searchText)) {
                filteredModel.addElement(listModel.getElementAt(i));
            }
        }
        listModel.clear();
        for (int i = 0; i < filteredModel.size(); i++) {
            listModel.addElement(filteredModel.getElementAt(i));
        }
    }

    public AddParticipantView(User user, Exchange exchange, AddParticipantController addParticipantController, int order) {
        this.user = user;
        this.exchange = exchange;
        this.addParticipantController = addParticipantController;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        imageLabel = new JLabel(getImage());

        titleLabel = new JLabel("Add Participant To " + this.exchange.getName());
        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        titleLabel.setForeground(new Color(74, 197, 114));

        headerPanel.add(imageLabel);
        headerPanel.add(titleLabel);

        backButton.addActionListener(e -> addParticipantController.changeToParticipantsView(this.user, this.exchange));
        buttonsPanel.add(backButton);

        addParticipantButton.addActionListener(e->{ int selectedIndex = userList.getSelectedIndex();
                                                    if(selectedIndex != -1){
                                                        int result = JOptionPane.showConfirmDialog(frame, "Sure you want to add " + listModel.getElementAt(selectedIndex).toString() + " to this exchange?",
                                                                "Swing Tester",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE);
                                                        if (result == JOptionPane.YES_OPTION) {
                                                            String username = listModel.getElementAt(selectedIndex).toString();
                                                            this.addParticipantController.addParticipant(username, this.exchange.getID());
                                                        }
                                                    }
        });
        buttonsPanel.add(addParticipantButton);

        iconLabel = new JLabel(getIcon(this.user.getAccountType()));
        positionIcon();
        buttonsPanel.add(iconLabel, BorderLayout.EAST);

        frame.add(headerPanel, BorderLayout.NORTH);
        // Initialize the listModel here
        listModel = new DefaultListModel<>();
        populateList(listModel, order);
        searchableListPanel = createSearchableListPanel(listModel);
        frame.add(searchableListPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

    //listModel.getElementAt(selectedIndex);

    public void populateList(DefaultListModel<String> listModel, int order) {
        addParticipantController.populateList(listModel, exchange.getID(), order);
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

    public void setAddParticipantController(AddParticipantController addParticipantController) {
        this.addParticipantController = addParticipantController;
    }
}
