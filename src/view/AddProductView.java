package view;

import controller.AddProductController;
import model.AccountType;
import model.Exchange;
import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AddProductView {

    JFrame frame = new JFrame("Add Product View");

    JPanel headerPanel = new JPanel();

    JPanel searchableListPanel;

    JList<String> productList;
    JPanel buttonsPanel = new JPanel();

    JLabel imageLabel;
    JLabel titleLabel;
    JLabel iconLabel;

    JLabel categoryLabel = new JLabel("Category:");
    JLabel supplierLabel = new JLabel("Supplier:");

    JComboBox<String> categoryBox = new JComboBox<>();

    JComboBox<String> supplierBox = new JComboBox<>();

    JButton backButton = new JButton("Back");
    JButton addProductButton = new JButton("Add Product");

    User user;
    Exchange exchange;

    int id_category;
    int id_supplier;

    AddProductController addProductController;

    private DefaultListModel<String> listModel;
    private JList<String> jList;
    private JTextField searchField;

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
            myPicture = ImageIO.read(new File("E:/Java/OOP-App/present.png"));
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

        productList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(productList);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(100, 20));
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Category " + categoryBox.getSelectedIndex());
                //System.out.println("Supplier " + supplierBox.getSelectedIndex());
                if(categoryBox.getSelectedIndex() != id_category || supplierBox.getSelectedIndex() != id_supplier){
                    //System.out.println("search");
                    addProductController.search(categoryBox.getSelectedIndex(), supplierBox.getSelectedIndex());
                }else {
                    String searchText = searchField.getText().toLowerCase();
                    filterList(listModel, searchText);
                }
            }
        });

        JPanel searchPanel = new JPanel();  // Create a new JPanel to hold the search components
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(categoryLabel);
        categoryBox.setPreferredSize((new Dimension(100, 20)));
        supplierBox.setPreferredSize((new Dimension(100, 20)));
        searchPanel.add(categoryBox);
        searchPanel.add(supplierLabel);
        searchPanel.add(supplierBox);

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

    public AddProductView(User user, Exchange exchange, AddProductController addProductController, String name_wishlist, int id_wishlist, int id_category, int id_supplier) {
        this.user = user;
        this.exchange = exchange;
        this.addProductController = addProductController;
        this.id_category = id_category;
        this.id_supplier = id_supplier;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        imageLabel = new JLabel(getImage());

        titleLabel = new JLabel("Add Product to " + name_wishlist);
        titleLabel.setFont(new Font("MV Boli", Font.PLAIN, 20));
        titleLabel.setForeground(new Color(255, 183, 76));

        headerPanel.add(imageLabel);
        headerPanel.add(titleLabel);

        backButton.addActionListener(e -> addProductController.changeToWishlistView(this.user, this.exchange));
        buttonsPanel.add(backButton);

        addProductButton.addActionListener(e->{ int selectedIndex = productList.getSelectedIndex();
            if(selectedIndex != -1){
                int result = JOptionPane.showConfirmDialog(frame, "Sure you want to add " + listModel.getElementAt(selectedIndex).toString() + " to your wishlist?",
                        "Swing Tester",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    String name = listModel.getElementAt(selectedIndex).toString();
                    this.addProductController.addProduct(name, id_wishlist);
                }
            }
        });
        buttonsPanel.add(addProductButton);

        iconLabel = new JLabel(getIcon(this.user.getAccountType()));
        positionIcon();
        buttonsPanel.add(iconLabel, BorderLayout.EAST);

        frame.add(headerPanel, BorderLayout.NORTH);
        // Initialize the listModel here
        listModel = new DefaultListModel<>();
        populateList(listModel, id_category, id_supplier);
        searchableListPanel = createSearchableListPanel(listModel);
        frame.add(searchableListPanel, BorderLayout.CENTER);
        frame.add(buttonsPanel, BorderLayout.SOUTH);
    }

    //listModel.getElementAt(selectedIndex);

    public void populateList(DefaultListModel<String> listModel, int id_category, int id_supplier) {
        addProductController.populateList(listModel, id_category, id_supplier);
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

    public void setAddProductController(AddProductController addProductController) {
        this.addProductController = addProductController;
    }

    public void populateCategory(DefaultComboBoxModel<String> categoryModel){
        this.categoryBox.setModel(categoryModel);
    }

    public void populateSupplier(DefaultComboBoxModel<String> supplierModel){
        this.supplierBox.setModel(supplierModel);
    }

    public void categorySetIndex(int index){
        this.categoryBox.setSelectedIndex(index);
    }

    public void supplierSetIndex(int index){
        this.supplierBox.setSelectedIndex(index);
    }
}
