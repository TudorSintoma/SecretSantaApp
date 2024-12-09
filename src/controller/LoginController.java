package controller;

import view.LoginView;
import repository.UsersRepository;
import model.AccountType;
import model.User;

public class LoginController{

    private UsersRepository usersRepository;
    private LoginView loginView = new LoginView();

    public LoginController(){
        this.usersRepository =  new UsersRepository();
        this.usersRepository.setLoginController(this);
        this.loginView.setVisibility(true);
        this.loginView.setLoginController(this);
    }

    public void loginError(String message){
        this.loginView.showMessage(message, 0);
        this.loginView.clearFields();
    }

    public void loginSuccess(String message, User user){
        this.loginView.showMessage(message, 1);
        changeToMainMenuView(user);
    }

    public void loginUser() {
        try {
            String username = this.loginView.getParam1();
            String password = this.loginView.getParam2();
            User user = this.usersRepository.getUser(username, password);
        }catch(Exception e){
        }
    }

    public void changeToNewAccountView(AccountType accountType) {
        loginView.setVisibility(false);
        UserController userController = new UserController(accountType);
    }


    public void changeToMainMenuView(User user) {
        loginView.setVisibility(false);
        MainMenuController mainMenuController = new MainMenuController(user);
    }
}
