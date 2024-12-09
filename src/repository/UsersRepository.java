package repository;

import java.io.FileNotFoundException;
import java.sql.*;
import model.AccountType;
import model.User;
import controller.LoginController;

import javax.swing.table.DefaultTableModel;

public class UsersRepository {

    LoginController loginController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void checkPassword(String pass1, String pass2){
        if(!pass1.equals(pass2)){
            throw new IllegalArgumentException();
        }
    }

    public User getUser (String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        User user = null;
        String message;
        try {

            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "select * from account where username = (?)";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            String realPassword = resultSet.getString("password");
            checkPassword(password, realPassword);

            int ID = resultSet.getInt("id_account");
            String email = resultSet.getString("email");
            String accType = resultSet.getString("account_type");

            AccountType accountType;
            if(accType.equals("ORGANIZER"))
                accountType = AccountType.ORGANIZER;
            else
                accountType = AccountType.PARTICIPANT;

            user = new User(ID, username, password, email, accountType);

            message = "Login succesful!";
            this.loginController.loginSuccess(message, user);

        } catch(IllegalArgumentException illegalArgumentException){
            message = "Invalid Password or Username!";
            this.loginController.loginError(message);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            message = "Invalid Password or Username!";
            this.loginController.loginError(message);
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
            return user;
        }
    }

    public static int getUserId(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int idAccount = 0;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT id_account FROM account WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idAccount = resultSet.getInt("id_account");
            } else {
                // Handle the case where the username does not exist
                System.out.println("Username not found: " + username);
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

        return idAccount;
    }

    public int getIDOrganizer(int id_user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        int idOrganizer = 0;
        try {

            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "select id_organizer from organizer where id_account = (?)";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setInt(1, id_user);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            idOrganizer = resultSet.getInt("id_organizer");

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
            return idOrganizer;
        }
    }

    public static int getIDParticipant(int id_user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        int idParticipant = 0;
        try {

            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "select id_participant from participant where id_account = (?)";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setInt(1, id_user);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            idParticipant = resultSet.getInt("id_participant");

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
            return idParticipant;
        }
    }

    public void deleteWishlist(int id_exchange, int id_participant){
        String url = "jdbc:postgresql://localhost:5432/SecretSanta";
        String username = "postgres";
        String password = "Tudor";

        String deleteQuery = "DELETE FROM public.wishlist WHERE id_exchange = ? AND id_participant = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, id_exchange);
            preparedStatement.setInt(2, id_participant);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Wishlist deleted successfully.");
            } else {
                System.out.println("No such wishlist found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteParticipation(int id_exchange, int id_participant) {
        String url = "jdbc:postgresql://localhost:5432/SecretSanta";
        String username = "postgres";
        String password = "Tudor";

        String deleteQuery = "DELETE FROM public.participation WHERE id_exchange = ? AND id_participant = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            deleteWishlist(id_exchange, id_participant);

            preparedStatement.setInt(1, id_exchange);
            preparedStatement.setInt(2, id_participant);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Participation deleted successfully.");
            } else {
                System.out.println("No such participation found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String insertOrganizer(int id_account) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "INSERT INTO organizer (id_account) VALUES (?);";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setInt(1, id_account);

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
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error at DB level";
            }
        }
    }

    public String insertParticipant(int id_account) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;

        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "INSERT INTO participant (id_account) VALUES (?);";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setInt(1, id_account);

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
            } catch (SQLException e) {
                e.printStackTrace();
                return "Error at DB level";
            }
        }
    }

    public String insertAccount(String username, String password, String email, AccountType accountType) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String accType = accountType.toString();
        Integer rowsAffected = 0;
        try {
            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "INSERT INTO account (username, password, email, account_type) VALUES (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(query);


            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, accType);

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
                int ID = getUserId(username);
                //System.out.println(ID);
                if (ID != 0) {
                    if (accountType == AccountType.ORGANIZER) {
                        insertOrganizer(ID);
                    } else {
                        insertParticipant(ID);
                    }
                }
                return rowsAffected + " Rows Affected. Success! Connection Closed!";
            }catch(SQLException sqlException){
                sqlException.printStackTrace();
                return "Error at DB level";
            }
        }
    }

    public void getUsers () {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {

            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

//            String query = "INSERT INTO person values('Bogdan', 'Test')";
            String query = "SELECT * FROM account;";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Display function to show the Resultset
            while (resultSet.next()) {
                int ID = resultSet.getInt("id_account");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String accType = resultSet.getString("account_type");
                System.out.println(ID + "     " + username + "     " + password + "     " + email + "     " + accType);
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

    public DefaultTableModel getUsers (int id_exchange) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        DefaultTableModel tableModel = new DefaultTableModel();
        try {

            String url = "jdbc:postgresql://localhost:5432/SecretSanta";
            String dbUsername = "postgres";
            String dbPassword = "Tudor";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT * FROM account WHERE id_account IN (SELECT id_account FROM participant WHERE id_participant IN (SELECT id_participant FROM participation WHERE id_exchange = (?)));";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_exchange);

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

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

    public User updateUser(User user, String newUsername, String newPassword, String newEmail) {
        // Your database connection details
        String URL = "jdbc:postgresql://localhost:5432/SecretSanta";
        String USERNAME = "postgres";
        String PASSWORD = "Tudor";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // SQL query to update user based on ID
            String query = "UPDATE account SET username = ?, password = ?, email = ? WHERE id_account = ?";
            preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the update statement
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setString(3, newEmail);
            preparedStatement.setInt(4, user.getID());

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                // Update the user object with the new information
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                user.setEmail(newEmail);
                return user;
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

}
