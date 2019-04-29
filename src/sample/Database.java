package sample;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static Connection connection = null;
    private static String url = "den1.mysql2.gear.host";
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
}
