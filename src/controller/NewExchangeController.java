package controller;

import model.Exchange;
import model.User;
import model.AccountType;
import repository.ExchangesRepository;
import repository.UsersRepository;
import view.NewExchangeView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewExchangeController {

    User user;

    UsersRepository usersRepository = new UsersRepository();

    ExchangesRepository exchangesRepository = new ExchangesRepository();

    NewExchangeView newExchangeView;
    public NewExchangeController(User user){
        this.user = user;
        this.newExchangeView = new NewExchangeView(this.user);
        this.newExchangeView.setNewExchangeController(this);
        this.newExchangeView.setVisibility(true);
    }

    private void checkEmptyField(String name, String date, String budget){

        if(name.equals("") || date.equals("") || budget.equals("")){
            throw new NullPointerException();
        }
    }
    public void createExchange(){
        try{
            String name = this.newExchangeView.getParam1();
            String dateString = this.newExchangeView.getParam2();
            String budgetString = this.newExchangeView.getParam3();

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

            String message = this.exchangesRepository.insertExchange(getIDOrganizer(), name, date, budget);
            this.newExchangeView.showMessage(message, 1);
            changeToExchangesView(this.user);
        } catch(NullPointerException nullPointerException){

            String message = "Empty field(s)!";
            this.newExchangeView.showMessage(message, 0);

        } catch (ParseException parseException){

            String message = "Invalid date! (must be of type yyyy-MM-dd)";
            this.newExchangeView.showMessage(message, 0);

        } catch(NumberFormatException numberFormatException){

            String message = "Invalid budget! (must be between 20 and 300)";
            this.newExchangeView.showMessage(message, 0);

        } catch(Exception e){
            this.newExchangeView.showMessage("Input error!",0);
        }
    }

    private int getIDOrganizer(){
        return this.usersRepository.getIDOrganizer(this.user.getID());
    }

    public void changeToExchangesView(User user) {
        newExchangeView.setVisibility(false);
        ExchangesController exchangesController = new ExchangesController(user);
    }
}
