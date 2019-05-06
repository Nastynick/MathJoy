package sample;

import java.sql.*;

public class Database {

    private static Connection connection = null;
    private static String url = "jdbc:mysql://den1.mysql2.gear.host:3306/mathjoy";
    private static String user = "mathjoy";
    private static String pass = "Glass1!";

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

    public boolean login (String username, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String usernameDB = null;
        String query = "SELECT username FROM User WHERE username = (?) AND password = (?)";
        PreparedStatement pst = getCurrentConnection().prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            usernameDB = rs.getString("username");
        }


        if (usernameDB == null) {
            return false;
        } else {
            return usernameDB.equals(username);
        }



    }

}
