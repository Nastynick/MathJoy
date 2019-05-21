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

        //Attempts login for the program, returns true if success, false if failure.
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

        //Returns an Arraylist of tasks given by ExerciseID
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
        //Gets a list of exercises by ID
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

        //returns a list of results for the user
        ArrayList<Result> results = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String query = "        SELECT *\n" +
                    "        FROM results\n" +
                    "        JOIN exercise ON results.exercise_idExercise = exercise.idExercise WHERE user_username = (?);";
            PreparedStatement pst = getCurrentConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Result result = new Result(rs.getInt("idResults")
                        , rs.getInt("exercise_idExercise")
                        , username
                        , rs.getString("value")
                        , rs.getString("date")
                        , rs.getString("nameExcercise")
                        );
                results.add(result);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return results;

    }

    void insertMathResult(String username, int exerciseID, String value) throws SQLException {

        //Inserts a new result for the user
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

        //returns all users in the list
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
            //inserts user in database, returns true if success.

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

        //returns true if the username does not exist.

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

        //removes user from database, returns true on success
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

    boolean editUser (User user) {

        //edits user, returns true on success
        try {
            if (!checkUsername(user)) {
                String query = "UPDATE user SET username = ?, password = ? WHERE username = ?;";
                PreparedStatement pst = getCurrentConnection().prepareStatement(query);
                pst.setString(1, user.getUsername());
                pst.setString(2, user.getPassword());
                pst.setString(3,user.getUsername());
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

    ArrayList<Exercise> getAllExcercises() {

        // returns all exercises
        ArrayList<Exercise> exercises = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String query = "SELECT * from exercise";
            PreparedStatement pst = getCurrentConnection().prepareStatement(query);
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

    boolean addUserToExercise (User user, int exerciseId) throws SQLException {

        //adds a user to an exercise, returns true if success
        if (!checkUsername(user)) {
            return false;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String usernameDB = null;
        String query = "SELECT user_username FROM user_has_exercise WHERE exercise_idExercise = (?)";
        PreparedStatement pst;
        pst = getCurrentConnection().prepareStatement(query);
        pst.setInt(1, exerciseId);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            usernameDB = rs.getString("user_username");
        }

        if (usernameDB == null || !usernameDB.equals(user.getUsername()) ) {
            return false;
        } else {
            query = "INSERT into user_has_exercise VALUES (?, ?);";
            pst = getCurrentConnection().prepareStatement(query);
            pst.setString(1,user.getUsername());
            pst.setInt(2,exerciseId);
            pst.execute();
            return true;
        }



    }

    boolean addExercise (Exercise exercise) throws SQLException {
        //inserts exercise in database, returns true if success.

        //returns true if the username does not exist.

        boolean exist = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String taskName = null;
        String query = "SELECT nameExcercise FROM exercise WHERE idExercise = (?)";
        PreparedStatement pst;
        pst = getCurrentConnection().prepareStatement(query);
        pst.setInt(1, exercise.getID());
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            taskName = rs.getString("nameExcercise");
        }

        if (taskName != null) {
            exist = true;
        }


        if (!exist) {

            query = "insert into exercise (idExercise, nameExcercise)\n" +
                    " values (?, ?);";
            pst = getCurrentConnection().prepareStatement(query);
            pst.setInt(1, getRandomNr());
            pst.setString(2, exercise.getName());
            pst.execute();
            return true;

        } else {
            return false;
        }

    }

    boolean insertTask (Task task, int exerciseID) throws SQLException {
        //inserts exercise in database, returns true if success.

        //returns true if the username does not exist.

        boolean exist = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String taskName = null;
        String query = "SELECT question FROM tasks WHERE idTasks = (?)";
        PreparedStatement pst;
        pst = getCurrentConnection().prepareStatement(query);
        pst.setInt(1, task.getQuestionID());
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            taskName = rs.getString("question");
        }

        if (taskName != null) {
            exist = true;
        }


        if (!exist) {

            query = "insert into tasks values (?, ?, ?, ?);";
            pst = getCurrentConnection().prepareStatement(query);
            pst.setInt(1, getRandomNr());
            pst.setString(2, task.getQuestionText());
            pst.setString(3, task.getAnswer());
            pst.setInt(4,exerciseID);
            pst.execute();
            return true;

        } else {
            return false;
        }
    }

    boolean deleteExercise (Exercise exercise) throws SQLException {

        boolean exist = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String taskName = null;
        String query = "SELECT nameExcercise FROM exercise WHERE idExercise = (?)";
        PreparedStatement pst;
        pst = getCurrentConnection().prepareStatement(query);
        pst.setInt(1, exercise.getID());
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            taskName = rs.getString("nameExcercise");
        }

        if (taskName != null) {
            exist = true;
        }


        //removes user from database, returns true on success
        try {
            if (exist) {
                query = "DELETE from exercise WHERE idExercise = (?)";
                pst = getCurrentConnection().prepareStatement(query);
                pst.setInt(1, exercise.getID());
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

    boolean deleteTask (Task task) throws SQLException {

        boolean exist = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String taskName = null;
        String query = "SELECT question FROM tasks WHERE idTasks = (?)";
        PreparedStatement pst;
        pst = getCurrentConnection().prepareStatement(query);
        pst.setInt(1, task.getQuestionID());
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            taskName = rs.getString("question");
        }

        if (taskName != null) {
            exist = true;
        }


        //removes user from database, returns true on success
        try {
            if (exist) {
                query = "DELETE from tasks WHERE idTasks = (?)";
                pst = getCurrentConnection().prepareStatement(query);
                pst.setInt(1, task.getQuestionID());
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

    boolean editTask (Task task) throws SQLException {

        boolean exist = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String taskName = null;
        String query = "SELECT question FROM tasks WHERE idTasks = (?)";
        PreparedStatement pst;
        pst = getCurrentConnection().prepareStatement(query);
        pst.setInt(1, task.getQuestionID());
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            taskName = rs.getString("question");
        }

        if (taskName != null) {
            exist = true;
        }


        try {
            if (exist) {
                query = "UPDATE tasks SET question = ?, answer = ? WHERE idTasks = ?;";
                pst = getCurrentConnection().prepareStatement(query);
                pst.setString(1, task.getQuestionText());
                pst.setString(2, task.getAnswer());
                pst.setInt(3,task.getQuestionID());
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
