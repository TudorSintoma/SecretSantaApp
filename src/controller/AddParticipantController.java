package controller;

import model.User;
import model.Exchange;
import repository.ExchangesRepository;
import repository.UsersRepository;
import view.AddParticipantView;
import view.ParticipantsView;

import javax.swing.*;

public class AddParticipantController {

    User user;
    Exchange exchange;
    ExchangesRepository exchangesRepository = new ExchangesRepository();

    AddParticipantView addParticipantView;

    public AddParticipantController(User user, Exchange exchange, int order) {
        this.user = user;
        this.exchange = exchange;
        this.addParticipantView = new AddParticipantView(user, exchange, this, order);
        this.addParticipantView.setAddParticipantController(this);
        this.addParticipantView.setVisibility(true);

        // Initialize the list model and populate the list
        if(order == 0) {
            DefaultListModel<String> listModel = this.exchangesRepository.getParticipationUsernames(this.exchange.getID());
            //System.out.println(listModel);
            populateList(listModel, exchange.getID(), order);
            this.addParticipantView.populateList(listModel, order);
        }
        if(order == 1){
            DefaultListModel<String> listModel = this.exchangesRepository.getParticipationUsernamesASC(this.exchange.getID());
            //System.out.println(listModel);
            populateList(listModel, exchange.getID(), order);
            this.addParticipantView.populateList(listModel, order);
        }
        if(order == 2){
            DefaultListModel<String> listModel = this.exchangesRepository.getParticipationUsernamesDSC(this.exchange.getID());
            //System.out.println(listModel);
            populateList(listModel, exchange.getID(), order);
            this.addParticipantView.populateList(listModel, order);
        }
    }

    public void order(int order){
        //System.out.println(order);
        this.addParticipantView.setVisibility(false);
        AddParticipantController addParticipantController = new AddParticipantController(this.user, this.exchange, order);
    }

    public void populateList(DefaultListModel<String> listModel, int exchangeId, int order) {

        DefaultListModel<String> participantsModel;
        if(order == 1) {
            participantsModel = exchangesRepository.getParticipationUsernamesASC(exchangeId);
        }else {
            if(order == 2) {
                participantsModel = exchangesRepository.getParticipationUsernamesDSC(exchangeId);
            }else{
                participantsModel = exchangesRepository.getParticipationUsernames(exchangeId);
            }
        }

        // Add the participants to the provided list model
        for (int i = 0; i < participantsModel.size(); i++) {
            listModel.addElement(participantsModel.getElementAt(i));
        }
    }


    public void addParticipant(String username, int id_exchange){
        int id_user = UsersRepository.getUserId(username);
        int id_participant = UsersRepository.getIDParticipant(id_user);
        try {
            String message = this.exchangesRepository.createParticipation(id_participant, id_exchange, username);
            this.addParticipantView.showMessage(message, 1);
            changeToParticipantsView(this.user, this.exchange);
        }catch(Exception e){
            this.addParticipantView.showMessage("Input error!",0);
        }
    }

    public void changeToParticipantsView(User user, Exchange exchange) {
        this.addParticipantView.setVisibility(false);
        ParticipantsController participantsController = new ParticipantsController(user, exchange);
    }
}
