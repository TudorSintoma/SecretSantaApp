package view;

import javax.swing.*;
import java.awt.*;
import model.AccountType;
import controller.UserController;

public class NewAccountView {
    JFrame frame = new JFrame("Create New Account View");

    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    JLabel labelParam1 = new JLabel("Username: ");
    JTextField textFieldParam1 = new JTextField();
    JLabel labelParam2 = new JLabel("Password: ");
    JTextField textFieldParam2 = new JTextField();
    JLabel labelParam3 = new JLabel("Email: ");
    JTextField textFieldParam3 = new JTextField();

    JButton createButton = new JButton("Create");
    JButton backButton = new JButton("Back");

    UserController userController;

    public NewAccountView(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        textFieldParam1.setPreferredSize(new Dimension(100, 20));

        textFieldParam2.setPreferredSize(new Dimension(100, 20));

        textFieldParam3.setPreferredSize(new Dimension(100, 20));

        panel1.add(labelParam1);
        panel1.add(textFieldParam1);
        panel1.add(labelParam2);
        panel1.add(textFieldParam2);
        panel1.add(labelParam3);
        panel1.add(textFieldParam3);

        createButton.addActionListener(e->{ userController.addAccount();
                                            textFieldParam1.setText("");
                                            textFieldParam2.setText("");
                                            textFieldParam3.setText("");});
        backButton.addActionListener(e->userController.changeToLoginView());

        panel2.add(createButton);
        panel2.add(backButton);

        JPanel panel = new JPanel();
        panel.add(panel1);
        panel.add(panel2);

        frame.setContentPane(panel);
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

    public UserController getUserController(){
        return userController;
    }

    public void setUserController(UserController userController){
        this.userController = userController;
    }

}
