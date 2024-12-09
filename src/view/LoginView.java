package view;

import javax.swing.*;
import java.awt.*;
import controller.LoginController;
import model.AccountType;

public class LoginView {

    JFrame frame = new JFrame("Login View");

    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    JLabel labelParam1 = new JLabel("Username: ");
    JTextField textFieldParam1 = new JTextField();
    JLabel labelParam2 = new JLabel("Password: ");
    JTextField textFieldParam2 = new JTextField();

    JButton createNewAccountButton = new JButton("Create New Account");
    JButton loginButton = new JButton("Login");

    LoginController loginController;

    public void chooseAccountType(){
        String responses[] = {"Organizer", "Participant"};
        int option = JOptionPane.showOptionDialog(null, "Choose account type: ", "Create New Account",
                                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, responses, 0);
        if(option == 0){
            loginController.changeToNewAccountView(AccountType.ORGANIZER);
        }else{
            loginController.changeToNewAccountView(AccountType.PARTICIPANT);
        }
    }
    public LoginView(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        textFieldParam1.setPreferredSize(new Dimension(100, 20));

        textFieldParam2.setPreferredSize(new Dimension(100, 20));

        panel1.add(labelParam1);
        panel1.add(textFieldParam1);
        panel1.add(labelParam2);
        panel1.add(textFieldParam2);

        createNewAccountButton.addActionListener(e -> this.chooseAccountType());
        loginButton.addActionListener(e->loginController.loginUser());

        panel2.add(createNewAccountButton);
        panel2.add(loginButton);

        JPanel panel = new JPanel();
        panel.add(panel1);
        panel.add(panel2);

        frame.setContentPane(panel);
    }

    public void setVisibility(boolean isVisible){
        frame.setVisible(isVisible);
    }

    public String getParam1() {
        return textFieldParam1.getText();
    }

    public String getParam2() {
        return textFieldParam2.getText();
    }

    public void showMessage(String message, int option){
        if( option == 0) {
            JOptionPane.showMessageDialog(frame, message, "Swing Tester", JOptionPane.ERROR_MESSAGE);
        }
        if(option ==1){
            JOptionPane.showMessageDialog(frame, message, "Swing Tester", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void clearFields(){
        textFieldParam1.setText("");
        textFieldParam2.setText("");
    }

    public LoginController getLoginController() {

        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

}
