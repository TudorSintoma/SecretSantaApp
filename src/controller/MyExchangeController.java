package controller;

import model.Exchange;
import view.MyAccountView;
import view.MyExchangeView;
import model.User;
import model.AccountType;
import repository.ExchangesRepository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyExchangeController {

    User user;

    Exchange exchange;

    MyExchangeView myExchangeView;

    ExchangesRepository exchangesRepository = new ExchangesRepository();

    public MyExchangeController(User user, Exchange exchange){
        this.user = user;
        this.exchange = exchange;
        this.myExchangeView = new MyExchangeView(user, exchange);
        this.myExchangeView.setMyExchangeController(this);
        this.myExchangeView.setVisibility(true);
    }

    private void checkEmptyField(String name, String date, String budget){

        if(name.equals("") || date.equals("") || budget.equals("")){
            throw new NullPointerException();
        }
    }


    public void updateExchangeInfo(Exchange exchange){
        try{
            String name = this.myExchangeView.getName();
            String dateString = this.myExchangeView.getDate();
            String budgetString = this.myExchangeView.getBudget();

            checkEmptyField(name, dateString, budgetString);

            // Define the SQL date format
            String sqlDateFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(sqlDateFormat);
            sdf.setLenient(false); // Strict parsing
            sdf.parse(dateString);

            Date date = Date.valueOf(dateString);
            int budget = Integer.parseInt(budgetString);
            if(budget < 20 || budget > 300)
                throw new NumberFormatException();

            this.exchange = this.exchangesRepository.updateExchange(exchange, name, date, budget);
            String message = "Update Successful!";
            this.myExchangeView.showMessage(message, 1);
            changeToExchangesView(this.user);
        } catch(NullPointerException nullPointerException){

            String message = "Empty field(s)!";
            this.myExchangeView.showMessage(message, 0);

        } catch (ParseException parseException){

            String message = "Invalid date! (must be of type yyyy-MM-dd)";
            this.myExchangeView.showMessage(message, 0);

        } catch(NumberFormatException numberFormatException){

            String message = "Invalid budget! (must be between 20 and 300)";
            this.myExchangeView.showMessage(message, 0);

        } catch(Exception e){

        }
    }

    public void changeToExchangesView(User user) {
        myExchangeView.setVisibility(false);
        ExchangesController exchangesController = new ExchangesController(user);
    }

    public void changeToParticipantsView(User user, Exchange exchange){
        myExchangeView.setVisibility(false);
        ParticipantsController participantsController = new ParticipantsController(user, exchange);
    }

    public void changeToWishlistView(User user, Exchange exchange, User owner){
        myExchangeView.setVisibility(false);
        WishlistController wishlistController = new WishlistController(user, exchange, owner);
    }
}
