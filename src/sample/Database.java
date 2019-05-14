package sample;

import java.security.SecureRandom;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database {

    private static Connection connection = null;
    private static String url = "jdbc:mysql://den1.mysql2.gear.host:3306/mathjoy";
    private static String user = "mathjoy";
    private static String pass = "Glass1!";
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Database.username = username;
    }

    // Establish a new connection without needing to create new connections if it does already exist.
    private Connection getCurrentConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                connection = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public boolean login(String username, String password) throws SQLException {
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

        return usernameDB != null && usernameDB.equals(username);


    }

    ArrayList<Task> getTasks(int exerciseID) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String query = "SELECT * FROM tasks WHERE Exercise_idExercise = (?)";
            PreparedStatement pst = getCurrentConnection().prepareStatement(query);
            pst.setInt(1, exerciseID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Task task = new Task(rs.getInt("idTasks"), rs.getString("question"), rs.getString("answer"));
                tasks.add(task);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return tasks;

    }

    ArrayList<Exercise> getExcercises(String username) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String query = "SELECT exercise.*\n" +
                    "FROM exercise\n" +
                    "JOIN user_has_exercise ON user_has_exercise.exercise_idExercise = exercise.idExercise WHERE user_has_exercise.user_username = (?);";
            PreparedStatement pst = getCurrentConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Exercise exercise = new Exercise(rs.getInt("idExercise"), rs.getString("nameExcercise"));
                exercises.add(exercise);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return exercises;

    }

    ArrayList<Result> getMathResults(String username) {
        ArrayList<Result> results = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String query = "        SELECT *\n" +
                    "        FROM results\n" +
                    "        JOIN exercise ON results.exercise_idExercise = exercise.idExercise;";
            PreparedStatement pst = getCurrentConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result(rs.getInt("idResults")
                        , rs.getInt("exercise_idExercise")
                        , username
                        , rs.getString("value")
                        , rs.getString("date")
                        , rs.getString("nameExercise")
                        );
                results.add(result);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return results;

    }

    void insertMathResult(String username, int exerciseID, String value) throws SQLException {
        String query = "insert into results (idResults, date, value, exercise_idExercise, user_username)\n" +
                " values (?, ?, ?, ?, ?);";
        PreparedStatement pst = getCurrentConnection().prepareStatement(query);

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        pst.setInt(1, getRandomNr());
        pst.setString(2, date);
        pst.setString(3, value);
        pst.setInt(4, exerciseID);
        pst.setString(5, username);

        pst.execute();
    }

    private int getRandomNr() { // returns new ID
        SecureRandom random = new SecureRandom();
        return random.nextInt(2147483647);
    }


    ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String query = "SELECT * FROM User";
        PreparedStatement pst = getCurrentConnection().prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("isTeacher"));
            users.add(user);
        }

        return users;

    }

    boolean insertUser(User user) throws SQLException {


        if (checkUsername(user)) {

            String query = "insert into user (username, password, isTeacher)\n" +
                    " values (?, ?, ?);";
            PreparedStatement pst = getCurrentConnection().prepareStatement(query);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, "false");
            pst.execute();
            return true;

        } else {
            return false;
        }
    }

    private boolean checkUsername(User user) throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String usernameDB = null;
        String query = "SELECT username FROM user WHERE username = (?)";
        PreparedStatement pst;
        pst = getCurrentConnection().prepareStatement(query);
        pst.setString(1, user.getUsername());
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            usernameDB = rs.getString("username");
        }


        return usernameDB == null;

    }

    boolean removeUser (User user) {
        try {
            if (!checkUsername(user)) {
                String query = "DELETE from user WHERE username = (?)";
                PreparedStatement pst = getCurrentConnection().prepareStatement(query);
                pst.setString(1, user.getUsername());
                pst.execute();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}





/*
        SELECT *
        FROM tasks
        JOIN exercise ON tasks.Exercise_idExercise = exercise.idExercise WHERE exercise.idExercise = 1;



        SELECT exercise.*
        FROM exercise
        JOIN user_has_exercise ON user_has_exercise.exercise_idExercise = exercise.idExercise WHERE user_has_exercise.user_username = 'Bob';


        */
