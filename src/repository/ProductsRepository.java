package repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ProductsRepository {


    public int getWishlistId(int id_participant, int id_exchange) {
        int wishlistId = -1;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT id_wishlist FROM wishlist WHERE id_participant = ? AND id_exchange = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_participant);
            preparedStatement.setInt(2, id_exchange);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                wishlistId = resultSet.getInt("id_wishlist");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your needs
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return wishlistId;
    }
    public String getWishlistName(int id_wishlist) {
        String wishlistName = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT wishlist_name FROM wishlist WHERE id_wishlist = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                wishlistName = resultSet.getString("wishlist_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your needs
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return wishlistName;
    }


    public int getNrProducts(int id_wishlist) {
        int nrProducts = -1;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT nr_products FROM wishlist WHERE id_wishlist = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nrProducts = resultSet.getInt("nr_products");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your needs
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nrProducts;
    }

    public int getTotalPrice(int id_wishlist) {
        int totalPrice = -1;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT total_price FROM wishlist WHERE id_wishlist = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalPrice = resultSet.getInt("total_price");
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your needs
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalPrice;
    }

    public int getProductId(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int idProduct = 0;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT id_product FROM product WHERE name_product = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idProduct = resultSet.getInt("id_product");
            } else {
                System.out.println("Name not found: " + name);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idProduct;
    }

    public int getProductPrice(int id_product) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int priceProduct = 0;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT price_unit FROM product WHERE id_product = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id_product);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                priceProduct = resultSet.getInt("price_unit");
            } else {
                System.out.println("Product not found");
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return priceProduct;
    }

    public String createEntry(int id_product, int id_wishlist) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Insert a new participation
            String query = "INSERT INTO entry (id_product, id_wishlist) VALUES (?, ?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_product);
            preparedStatement.setInt(2, id_wishlist);

            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Error at DB level!";
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                return rowsAffected + " Rows Affected. Success! Connection Closed!";
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error at DB level!";
            }
        }
    }

    public void getWishlist (int id_wishlist) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {

            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

//            String query = "INSERT INTO person values('Bogdan', 'Test')";
            String query = "SELECT * FROM wishlist where id_wishlist = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Display function to show the Resultset
            while (resultSet.next()) {
                int ID = resultSet.getInt("id_wishlist");
                String name = resultSet.getString("wishlist_name");
                int price = resultSet.getInt("total_price");
                int nrProducts = resultSet.getInt("nr_products");
                System.out.println(ID + "     " + name + "     " + price + "     " + nrProducts);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateWishlist(int price, int nrProducts, int id_wishlist){
        String URL = "jdbc:postgresql://localhost:5432/SecretSanta";
        String USERNAME = "postgres";
        String PASSWORD = "Tudor";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // SQL query to update user based on ID
            String query = "UPDATE wishlist SET total_price = ?, nr_products = ? WHERE id_wishlist = ?";
            preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the update statement
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, nrProducts);
            preparedStatement.setInt(3, id_wishlist);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            System.out.println("Error1");
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception according to your needs
                System.out.println("Error2");
            }
        }
    }

    public void deleteEntry(int id_entry) {
        String url = "jdbc:postgresql://localhost:5432/SecretSanta";
        String username = "postgres";
        String password = "Tudor";

        String deleteQuery = "DELETE FROM public.entry WHERE id_entry = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, id_entry);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Entry deleted successfully.");
            } else {
                System.out.println("No such entry found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DefaultTableModel getProducts(int id_participant, int id_exchange) {
        int wishlistId = getWishlistId(id_participant, id_exchange);
        DefaultTableModel tableModel = new DefaultTableModel();

        if (wishlistId != -1) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                String url = "jdbc:postgresql://localhost:5432/SecretSanta";
                String dbUsername = "postgres";
                String dbPassword = "Tudor";

                connection = DriverManager.getConnection(url, dbUsername, dbPassword);

                // Select all columns from the product table for the given wishlist
                String query = "SELECT * " +
                        "FROM product p " +
                        "JOIN entry e ON p.id_product = e.id_product " +
                        "WHERE e.id_wishlist = ?";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, wishlistId);

                ResultSet resultSet = preparedStatement.executeQuery();

                // Add columns to the table model
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    tableModel.addColumn(resultSet.getMetaData().getColumnName(i));
                }

                // Add rows to the table model
                //System.out.println("Products in wishlist:");
                while (resultSet.next()) {
                    //System.out.println(resultSet.getString("name_product"));
                    Object[] row = new Object[resultSet.getMetaData().getColumnCount()];
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row[i - 1] = resultSet.getObject(i);
                    }
                    tableModel.addRow(row);
                }

            } catch (SQLException e) {
                e.printStackTrace();  // Handle the exception according to your needs
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return tableModel;
    }

    public DefaultListModel<String> getProductsNames(int id_wishlist) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Select usernames of participants who don't have a participation in the given exchange
            String query = "select prod.name_product from product prod where prod.id_product not in " +
                    " (select e.id_product from entry e where e.id_wishlist = ?) order by prod.name_product ASC";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name_product");
                listModel.addElement(name);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listModel;
        }
    }

    public DefaultListModel<String> getProductsNamesWithCategory(int id_wishlist, int id_category) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Select usernames of participants who don't have a participation in the given exchange
            String query = "select prod.name_product from product prod where prod.id_category = ? AND prod.id_product not in " +
                    " (select e.id_product from entry e where e.id_wishlist = ?) order by prod.name_product ASC";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_category);
            preparedStatement.setInt(2, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name_product");
                listModel.addElement(name);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listModel;
        }
    }

    public DefaultListModel<String> getProductsNamesWithSupplier(int id_wishlist, int id_supplier) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Select usernames of participants who don't have a participation in the given exchange
            String query = "select prod.name_product from product prod where prod.id_category = ? AND prod.id_product not in " +
                    " (select e.id_product from entry e where e.id_wishlist = ?) order by prod.name_product ASC";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_supplier);
            preparedStatement.setInt(2, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name_product");
                listModel.addElement(name);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listModel;
        }
    }

    public DefaultListModel<String> getProductsNamesWithCategoryAndSupplier(int id_wishlist, int id_category, int id_supplier) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Select usernames of participants who don't have a participation in the given exchange
            String query = "select prod.name_product from product prod where prod.id_category = ? AND id_supplier = ? AND prod.id_product not in " +
                    " (select e.id_product from entry e where e.id_wishlist = ?) order by prod.name_product ASC";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_category);
            preparedStatement.setInt(2, id_supplier);
            preparedStatement.setInt(3, id_wishlist);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name_product");
                listModel.addElement(name);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listModel;
        }
    }

    public static DefaultComboBoxModel<String> populateDropdown(String tableName, String columnName) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("All");

        // Database connection details
        String url = "jdbc:postgresql://localhost:5432/SecretSanta";
        String username = "postgres";
        String password = "Tudor";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT " + columnName + " FROM " + tableName;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                // Populate the model with data from the database
                while (resultSet.next()) {
                    String value = resultSet.getString(columnName);
                    model.addElement(value);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}
