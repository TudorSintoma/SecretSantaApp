package controller;

import model.User;
import model.Exchange;
import model.Wishlist;
import repository.ProductsRepository;
import view.AddProductView;

import javax.swing.*;
public class AddProductController {

    User user;
    Exchange exchange;
    ProductsRepository productsRepository = new ProductsRepository();

    AddProductView addProductView;

    Wishlist wishlist;

    public AddProductController(User user, Exchange exchange, Wishlist wishlist, int id_category, int id_supplier) {
        this.user = user;
        this.exchange = exchange;
        this.wishlist = wishlist;

        this.addProductView = new AddProductView(user, exchange, this, this.wishlist.getName(), this.wishlist.getID(), id_category, id_supplier);
        this.addProductView.setAddProductController(this);
        this.addProductView.setVisibility(true);


        DefaultListModel<String> listModel;
        if(id_category == 0 && id_supplier == 0) {
            //System.out.println("Case 1");
            listModel = productsRepository.getProductsNames(this.wishlist.getID());
        }else{
            if(id_category == 0){
                //System.out.println("Case 2");
                listModel = productsRepository.getProductsNamesWithSupplier(this.wishlist.getID(), id_supplier);
            }else{
                if(id_supplier == 0){
                    //System.out.println("Case 3");
                    listModel = productsRepository.getProductsNamesWithCategory(this.wishlist.getID(), id_category);
                }else{
                    //System.out.println("Case 4");
                    listModel = productsRepository.getProductsNamesWithCategoryAndSupplier(this.wishlist.getID(), id_category, id_supplier);
                }
            }
        }
        //System.out.println(listModel);
        populateList(listModel, id_category, id_supplier);
        this.addProductView.populateList(listModel, id_category, id_supplier);

        DefaultComboBoxModel<String> categoryModel = getComboBox("category", "name_category");
        DefaultComboBoxModel<String> supplierModel = getComboBox("supplier", "name_supplier");

        this.addProductView.populateCategory(categoryModel);
        this.addProductView.categorySetIndex(id_category);

        this.addProductView.populateSupplier(supplierModel);
        this.addProductView.supplierSetIndex(id_supplier);
    }

    public void search(int id_category, int id_supplier){
        this.addProductView.setVisibility(false);
        AddProductController addProductController = new AddProductController(this.user, this.exchange, this.wishlist, id_category, id_supplier);
    }

    private void checkPrice(int price, int budget){
        if(this.wishlist.getTotalPrice() + price > budget)
            throw new ArithmeticException();
    }

    public void addProduct(String product_name, int id_wishlist){
        try {
            int id_product = this.productsRepository.getProductId(product_name);
            int price = this.productsRepository.getProductPrice(id_product);
            checkPrice(price, this.exchange.getBudget());
            String message = this.productsRepository.createEntry(id_product, id_wishlist);
            int nrProducts = this.wishlist.incrementNrProducts();
            int totalPrice = this.wishlist.increasePrice(price);
            //System.out.println("total price: " + totalPrice + "   nr products: " + nrProducts);
            this.productsRepository.updateWishlist(totalPrice, nrProducts, this.wishlist.getID());
            //this.productsRepository.getWishlist(this.wishlist.getID());
            //System.out.println("wishlist ID: " + this.wishlist.getID());
            this.addProductView.showMessage(message, 1);
            this.addProductView.setVisibility(false);
            AddProductController addProductController = new AddProductController(this.user, this.exchange, this.wishlist, 0,0);
        }catch(ArithmeticException arithmeticException){
           this.addProductView.showMessage("Total price of wishlist products exceeds exchange budget! ", 0);
        }catch(Exception e){
            this.addProductView.showMessage("Error at DB level!", 0);
        }
    }
    public void changeToWishlistView(User user, Exchange exchange){
        this.addProductView.setVisibility(false);
        WishlistController wishlistController = new WishlistController(user, exchange, user);
    }

    public void populateList(DefaultListModel<String> listModel, int id_category, int id_supplier) {

        DefaultListModel<String> productsModel;
        if(id_category == 0 && id_supplier == 0) {
            productsModel = productsRepository.getProductsNames(this.wishlist.getID());
        }else{
            if(id_category == 0){
                productsModel = productsRepository.getProductsNamesWithSupplier(this.wishlist.getID(), id_supplier);
            }else{
                if(id_supplier == 0){
                    productsModel = productsRepository.getProductsNamesWithCategory(this.wishlist.getID(), id_category);
                }else{
                    productsModel = productsRepository.getProductsNamesWithCategoryAndSupplier(this.wishlist.getID(), id_category, id_supplier);
                }
            }
        }

        // Add the participants to the provided list model
        for (int i = 0; i < productsModel.size(); i++) {
            listModel.addElement(productsModel.getElementAt(i));
        }
    }

    public DefaultComboBoxModel<String> getComboBox(String tabelName, String colName) {

        DefaultComboBoxModel<String> productsModel;
        productsModel = productsRepository.populateDropdown(tabelName, colName);

        return productsModel;
    }

}
