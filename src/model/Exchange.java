package model;

import java.sql.Date;

public class Exchange {

    int ID;
    String name;
    int budget;
    Date date;

    public Exchange(int ID, String name, int budget, Date date){
        this.ID = ID;
        this.name = name;
        this.budget = budget;
        this.date = date;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
