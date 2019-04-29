package sample;

import java.sql.*;

public class Database {

    private static Connection connection = null;
    private static String url = "";
    private static String user = "";
    private static String pass = "";

    // Establish a new connection without needing to create new connections if it does already exist.
    private Connection getCurrentConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                connection = DriverManager.getConnection(url,user,pass);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    boolean login (String username, String password) throws SQLException {
        String usernameDB = null;
        String query = "SELECT username FROM User WHERE username = (?) AND password = (?)";
        PreparedStatement pst = getCurrentConnection().prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            usernameDB = rs.getString(username);
        }

        assert usernameDB != null;
        return usernameDB.equals(username);


    }

}
