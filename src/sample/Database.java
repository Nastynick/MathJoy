package sample;

import java.sql.*;
import java.util.ArrayList;

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

    boolean login (String username, String password) throws SQLException {
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

            String query = "SELECT exercise.* JOIN user_has_exercise ON user_has_exercise.exercise_idExercise = exercise.idExercise WHERE user_has_exercise.user_username = (?);";
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

}





/*
        SELECT *
        FROM tasks
        JOIN exercise ON tasks.Exercise_idExercise = exercise.idExercise WHERE exercise.idExercise = 1;



        SELECT exercise.*
        FROM exercise
        JOIN user_has_exercise ON user_has_exercise.exercise_idExercise = exercise.idExercise WHERE user_has_exercise.user_username = 'Bob';


        */
