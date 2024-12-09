package controller;

import view.MyAccountView;
import repository.UsersRepository;
import model.User;
import model.AccountType;

import java.sql.SQLException;

public class MyAccountController {

    MyAccountView myAccountView;

    UsersRepository usersRepository;

    User user;

    public MyAccountController(User user) {
        this.user = user;
        this.myAccountView = new MyAccountView(user);
        this.myAccountView.setMyAccountController(this);
        this.myAccountView.setVisibility(true);
        this.usersRepository = new UsersRepository();

        this.myAccountView.setUsername(user.getUsername());
        this.myAccountView.setPassword(user.getPassword());
        this.myAccountView.setEmail(user.getEmail());
    }

    public void changeToMainMenuView(User user) {
        myAccountView.setVisibility(false);
        MainMenuController mainMenuController = new MainMenuController(user);
    }

    private AccountType checkAccountType(String accountType){
        if(accountType.equals("ORGANIZER")){
            return AccountType.ORGANIZER;
        }
        if(accountType.equals("PARTICIPANT")){
            return AccountType.PARTICIPANT;
        }
        throw new IllegalStateException();
    }

    private void checkEmptyField(String username, String password, String email){

        if(username.equals("") || password.equals("") || email.equals("")){
            throw new NullPointerException();
        }
    }

    private void checkPasswordLength(String password){
        if(password.length() < 8){
            throw new ArithmeticException();
        }
    }

    public void updateUserInfo(User user) {
        try {

            String username = this.myAccountView.getUsername();
            String password = this.myAccountView.getPassword();
            String email = this.myAccountView.getEmail();

            checkEmptyField(username, password, email);
            checkPasswordLength(password);

            this.user = this.usersRepository.updateUser(user, username, password, email);
            String message = "Update Successful!";
            this.myAccountView.showMessage(message, 1);
            changeToMainMenuView(this.user);

        }catch(NullPointerException nullPointerException){

            String message = "Empty field(s)!";
            this.myAccountView.showMessage(message, 0);

        }catch(ArithmeticException arithmeticException){

            String message = "Password must have at least 8 characters!";
            this.myAccountView.showMessage(message, 0);

        }catch(Exception e){

        }
    }
}
