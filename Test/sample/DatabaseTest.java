package sample;

import org.junit.Test;

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

}