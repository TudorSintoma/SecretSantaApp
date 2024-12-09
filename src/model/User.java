package model;

public class User {

    int ID;
    String username;
    String password;
    String email;
    AccountType accountType;

    public User(int ID, String username, String password, String email, AccountType accountType){
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
