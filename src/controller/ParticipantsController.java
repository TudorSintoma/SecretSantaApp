package controller;

import model.User;
import model.Exchange;
import model.AccountType;
import repository.UsersRepository;
import view.MyExchangeView;
import view.ParticipantsView;

import javax.swing.table.DefaultTableModel;

public class ParticipantsController {

    User user;

    Exchange exchange;

    UsersRepository usersRepository;

    ParticipantsView participantsView;

    public ParticipantsController(User user, Exchange exchange){
        this.user = user;
        this.exchange = exchange;
        this.participantsView = new ParticipantsView(user, exchange);
        this.usersRepository = new UsersRepository();
        this.participantsView.setParticipantsController(this);
        this.participantsView.setVisibility(true);

        DefaultTableModel tableModel = usersRepository.getUsers(this.exchange.getID());
        participantsView.populateTable(tableModel);
    }

    public void removeParticipant(int id_user){
        int id_participant = this.usersRepository.getIDParticipant(id_user);
        this.usersRepository.deleteParticipation(this.exchange.getID(), id_participant);
        this.participantsView.setVisibility(false);
        ParticipantsController participantsController = new ParticipantsController(this.user, this.exchange);
    }

    public void changeToMyExchangeView(User user, Exchange exchange){
        this.participantsView.setVisibility(false);
        MyExchangeController myExchangeController = new MyExchangeController(user, exchange);
    }

    public void changeToAddParticipantView(User user, Exchange exchange){
        this.participantsView.setVisibility(false);
        AddParticipantController addParticipantController = new AddParticipantController(user, exchange, 0);
    }

    public void changeToWishlistView(User user, Exchange exchange, User owner){
        this.participantsView.setVisibility(false);
        WishlistController wishlistController = new WishlistController(user, exchange, owner);
    }

}
