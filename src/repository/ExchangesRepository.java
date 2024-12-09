package repository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

import model.Exchange;
import model.User;
import model.AccountType;

public class ExchangesRepository {

    public DefaultTableModel getExchanges() {
        DefaultTableModel tableModel = new DefaultTableModel();
        try {
            // Initialize your database connection
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SecretSanta", "postgres", "Tudor");

            String query = "SELECT * FROM exchange";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // Retrieve data
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableModel;
    }

    public DefaultTableModel getExchangesForUser(User user) {
        DefaultTableModel tableModel = new DefaultTableModel();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query;

            if (user.getAccountType() == AccountType.ORGANIZER) {
                // For organizers, retrieve exchanges organized by them
                query = "SELECT * FROM exchange WHERE id_organizer = (SELECT id_organizer FROM organizer WHERE id_account = ?)";
            } else {
                // For participants, retrieve exchanges they are participating in
                query = "SELECT e.* FROM exchange e " +
                        "JOIN participation p ON e.id_exchange = p.id_exchange " +
                        "WHERE p.id_participant = (SELECT id_participant FROM participant WHERE id_account = ?)";
            }

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getID()); // Assuming getId() returns the user's ID

            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // Retrieve data
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(rowData);
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

        return tableModel;
    }

    public Exchange updateExchange(Exchange exchange, String newName, Date newDate, int newBudget){
        // Your database connection details
        String URL = "jdbc:postgresql://localhost:5432/SecretSanta";
        String USERNAME = "postgres";
        String PASSWORD = "Tudor";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // SQL query to update user based on ID
            String query = "UPDATE exchange SET name_exchange = ?, date_exchange = ?, budget_exchange = ? WHERE id_exchange = ?";
            preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the update statement
            preparedStatement.setString(1, newName);
            preparedStatement.setObject(2, newDate);
            preparedStatement.setInt(3, newBudget);
            preparedStatement.setInt(4, exchange.getID());

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                // Update the user object with the new information
                exchange.setName(newName);
                exchange.setDate(newDate);
                exchange.setBudget(newBudget);
                return exchange;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
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
            }
        }
        return null; // Return null if the update fails
    }

    public void deleteExchange(int id_exchange) {
        String url = "jdbc:postgresql://localhost:5432/SecretSanta";
        String username = "postgres";
        String password = "Tudor";

        String deleteQuery = "DELETE FROM public.exchange WHERE id_exchange = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, id_exchange);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Exchange deleted successfully.");
            } else {
                System.out.println("No exchange found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String insertExchange(int id_organizer, String name, Date date, int budget) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "INSERT INTO exchange (id_organizer, name_exchange, date_exchange, budget_exchange) VALUES (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setInt(1, id_organizer);
            preparedStatement.setString(2, name);
            preparedStatement.setObject(3, date);
            preparedStatement.setInt(4, budget);

            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Error at DB level";
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                return rowsAffected + " Rows Affected. Success! Connection Closed!";
            }catch(SQLException sqlException){
                sqlException.printStackTrace();
                return "Error at DB level";
            }
        }
    }

    public DefaultListModel<String> getParticipationUsernames(int id_exchange) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Select usernames of participants who don't have a participation in the given exchange
            String query = "select acc.username from account acc where acc.account_type = 'PARTICIPANT' AND acc.id_account not in " +
                    " (select a.id_account from account a join participant p on " +
                    " (a.id_account = p.id_account) join participation pa on(p.id_participant = pa.id_participant) " +
                    " where pa.id_exchange = ?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_exchange);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                listModel.addElement(username);
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

    public DefaultListModel<String> getParticipationUsernamesASC(int id_exchange) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Select usernames of participants who don't have a participation in the given exchange
            String query = "select acc.username from account acc where acc.account_type = 'PARTICIPANT' AND acc.id_account not in " +
                    " (select a.id_account from account a join participant p on " +
                    " (a.id_account = p.id_account) join participation pa on(p.id_participant = pa.id_participant) " +
                    " where pa.id_exchange = ?) ORDER BY LOWER(acc.username) ASC";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_exchange);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                //System.out.println(username);
                listModel.addElement(username);
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

    public DefaultListModel<String> getParticipationUsernamesDSC(int id_exchange) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Select usernames of participants who don't have a participation in the given exchange
            String query = "select acc.username from account acc where acc.account_type = 'PARTICIPANT' AND acc.id_account not in " +
                    " (select a.id_account from account a join participant p on " +
                    " (a.id_account = p.id_account) join participation pa on(p.id_participant = pa.id_participant) " +
                    " where pa.id_exchange = ?) ORDER BY LOWER(acc.username) DESC";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_exchange);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                //System.out.println(username);
                listModel.addElement(username);
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

    public void createWishlist(String username, int id_participant, int id_exchange){

        String wishlistName = username + "'s Wishlist";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "INSERT INTO wishlist (wishlist_name, nr_products, total_price, id_participant, id_exchange) VALUES (?, 0, 0, ?, ?);";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1, wishlistName);
            preparedStatement.setInt(2, id_participant);
            preparedStatement.setInt(3, id_exchange);

            rowsAffected = preparedStatement.executeUpdate();

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
            }catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }

    public String createParticipation(int id_participant, int id_exchange, String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {
            createWishlist(username, id_participant, id_exchange);
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Insert a new participation
            String query = "INSERT INTO participation (id_participant, id_exchange) VALUES (?, ?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_participant);
            preparedStatement.setInt(2, id_exchange);

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

}