package model;

import java.sql.Date;

public class Wishlist {

    int ID;
    String name;
    int totalPrice;
    int nrProducts;

    public Wishlist(int ID, String name, int totalPrice, int nrProducts){
        this.ID = ID;
        this.name = name;
        this.totalPrice = totalPrice;
        this.nrProducts = nrProducts;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getNrProducts() {
        return nrProducts;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int incrementNrProducts(){
        this.nrProducts += 1;
        return this.nrProducts;
    }

    public int decrementNrProducts(){
        this.nrProducts -= 1;
        if(this.nrProducts < 0)
            this.nrProducts = 0;
        return this.nrProducts;
    }

    public int increasePrice(int price){
        this.totalPrice += price;
        return this.totalPrice;
    }

    public int decreasePrice(int price){
        this.totalPrice -= price;
        if(this.totalPrice < 0)
            this.totalPrice = 0;
        return this.totalPrice;
    }
}
