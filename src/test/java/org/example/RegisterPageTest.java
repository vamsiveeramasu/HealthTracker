package org.example;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterPageTest {

    private RegisterPage registerPage;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @Before
    public void setUp() {
        registerPage = new RegisterPage();

    }

    @Test
    public void testIsValidEmail_ValidEmail() {
        assertTrue(registerPage.isValidEmail("test@example.com"));
    }

    @Test
    public void testIsValidEmail_InvalidEmail() {
        assertFalse(registerPage.isValidEmail("test@.com"));
        assertFalse(registerPage.isValidEmail("test@example"));
        assertFalse(registerPage.isValidEmail("test@com"));
        assertFalse(registerPage.isValidEmail("test@.example.com"));
    }
}

