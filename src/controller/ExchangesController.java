package controller;

import repository.ExchangesRepository;
import view.ExchangesView;
import javax.swing.table.DefaultTableModel;
import model.AccountType;
import model.User;
import model.Exchange;
import view.MyExchangeView;

public class ExchangesController {

    private ExchangesRepository exchangesRepository;

    private ExchangesView exchangesView;

    public ExchangesController(User user) {
        exchangesRepository = new ExchangesRepository();
        exchangesView = new ExchangesView(user);
        exchangesView.setExchangesController(this);

        // Retrieve data and populate the table
        DefaultTableModel tableModel = exchangesRepository.getExchangesForUser(user);
        exchangesView.populateTable(tableModel);
    }

    public void deleteExchange(int id_exchange, User user){
        this.exchangesRepository.deleteExchange(id_exchange);
        this.exchangesView.setVisibility(false);
        ExchangesController exchangesController = new ExchangesController(user);
    }

    public void changeToNewExchangeView(User user){
        this.exchangesView.setVisibility(false);
        NewExchangeController newExchangeControllerr = new NewExchangeController(user);
    }

    public void changeToMainMenuView(User user) {
        this.exchangesView.setVisibility(false);
        MainMenuController mainMenuController = new MainMenuController(user);
    }

    public void changeToMyExchangeView(User user, Exchange exchange){
        this.exchangesView.setVisibility(false);
        MyExchangeController myExchangeController = new MyExchangeController(user, exchange);
    }

}