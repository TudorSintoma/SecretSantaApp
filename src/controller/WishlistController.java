package controller;

import model.User;
import model.Exchange;
import model.AccountType;
import model.Wishlist;
import view.WishlistView;
import repository.ProductsRepository;
import repository.UsersRepository;

import javax.swing.table.DefaultTableModel;

public class WishlistController {

    User user;

    User owner;

    Exchange exchange;

    WishlistView wishlistView;

    Wishlist wishlist;

    ProductsRepository productsRepository = new ProductsRepository();

    public WishlistController(User user, Exchange exchange, User owner){
        this.user = user;
        this.owner = owner;
        this.exchange = exchange;
        int id_participant = UsersRepository.getIDParticipant(owner.getID());

        int id = this.productsRepository.getWishlistId(id_participant, exchange.getID());
        String name = this.productsRepository.getWishlistName(id);
        int nrProducts = this.productsRepository.getNrProducts(id);
        int totalPrice = this.productsRepository.getTotalPrice(id);

        //System.out.println(id + "  " + name + "  " + totalPrice + "  " + nrProducts);

        this.wishlist = new Wishlist(id, name, totalPrice, nrProducts);

        this.wishlistView= new WishlistView(user, exchange, owner, name, totalPrice);
        this.wishlistView.setWishlistController(this);
        this.wishlistView.setVisibility(true);

        DefaultTableModel tableModel = productsRepository.getProducts(id_participant, exchange.getID());
        wishlistView.populateTable(tableModel);
    }

    public void removeProduct(int id_entry, int id_product){
        try {
            this.productsRepository.deleteEntry(id_entry);
            int price = this.productsRepository.getProductPrice(id_product);
            int nrProducts = this.wishlist.decrementNrProducts();
            int totalPrice = this.wishlist.decreasePrice(price);
            //System.out.println("total price: " + totalPrice + "   nr products: " + nrProducts);
            this.productsRepository.updateWishlist(totalPrice, nrProducts, this.wishlist.getID());
            //this.productsRepository.getWishlist(this.wishlist.getID());
            this.wishlistView.setVisibility(false);
            WishlistController wishlistController = new WishlistController(this.user, this.exchange, this.owner);
        }catch(Exception e){

        }
    }

    public void changeView(User user, Exchange exchange, User owner){
        if(user.getUsername().equals(owner.getUsername())){
            changeToMyExchangeView(user, exchange);
        }else{
            changeToParticipantsView(user, exchange);
        }
    }

    public void changeToMyExchangeView(User user, Exchange exchange){
        this.wishlistView.setVisibility(false);
        MyExchangeController myExchangeController = new MyExchangeController(user, exchange);
    }

    public void changeToParticipantsView(User user, Exchange exchange){
        this.wishlistView.setVisibility(false);
        ParticipantsController participantsController = new ParticipantsController(user, exchange);
    }

    public void changeToAddProductView(User user, Exchange exchange){
        this.wishlistView.setVisibility(false);
        AddProductController addProductController= new AddProductController(user, exchange, this.wishlist, 0, 0);
    }

}
