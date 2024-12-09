package controller;

import repository.UsersRepository;
import view.NewAccountView;
import model.AccountType;
import model.User;

public class UserController {

    private UsersRepository usersRepository;

    private NewAccountView newAccountView;

    private AccountType accountType;

    private LoginController loginController;

    public UserController(AccountType accountType){
        this.newAccountView = new NewAccountView();
        this.usersRepository = new UsersRepository();
        this.newAccountView.setVisibility(true);
        this.newAccountView.setUserController(this);
        this.accountType = accountType;
    }

    public void addAccount(){
        try {
            String username = this.newAccountView.getParam1();
            String password = this.newAccountView.getParam2();
            String email = this.newAccountView.getParam3();
            String message = usersRepository.insertAccount(username, password, email, this.accountType);
            this.newAccountView.showMessage(message,1);
            this.usersRepository.getUsers();
        }
        catch (Exception e){
            this.newAccountView.showMessage("Input error!",0);
        }

    }


    public void changeToLoginView() {
        this.newAccountView.setVisibility(false);
        loginController = new LoginController();
    }
}
