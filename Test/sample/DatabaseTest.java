package sample;

import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseTest {
    private Database database = new Database();
    @Test
    public void loginSuccess() throws Exception {
        boolean expected = true;

        boolean actual = database.login("Bob", "Bob1");

        assertEquals(actual,expected);
    }

    @Test
    public void loginFail() throws Exception {
        boolean expected = false;

        boolean actual = database.login("bob", "Bob1");

        assertEquals(actual,expected);
    }

    @Test
    public void getTasks() throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks = database.getTasks(1);
        Task task = tasks.get(0);
        assertNotNull(task);
    }

    @Test
    public void getTasksNegative() throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks = database.getTasks(5000);      //test for trying task that don't exist
        boolean actual = false;

        if (tasks.size() < 1) {
            actual = true;
        }
        assertTrue(actual);
    }

    @Test
    public void getAllUsers() throws SQLException {
        ArrayList<User> users = database.getAllUsers();
        boolean actual = true;
        if (users.size() <1 ) {
            actual = false;
        }
        assertTrue(actual);
    }


}