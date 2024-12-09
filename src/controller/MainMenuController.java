package controller;

import model.User;
import model.AccountType;
import view.MainMenuView;

public class MainMenuController {

    MainMenuView mainMenuView;

    User user;

    public MainMenuController(User user) {
        this.user = user;
        this.mainMenuView = new MainMenuView(user);
        this.mainMenuView.setMainMenuController(this);
        this.mainMenuView.setVisibility(true);
    }

    public void changeToLoginView() {
        this.mainMenuView.setVisibility(false);
        LoginController loginController = new LoginController();
    }

    public void changeToExchangesView(User user) {
        mainMenuView.setVisibility(false);
        //System.out.println(user.getID() + "     " + user.getUsername() + "     " + user.getPassword() + "     "
         //       + user.getEmail() + "     " + user.getAccountType());
        ExchangesController exchangesController = new ExchangesController(user);
    }

    public void changeToMyAccountView(User user){
        mainMenuView.setVisibility(false);
        MyAccountController myAccountController = new MyAccountController(user);
    }
}