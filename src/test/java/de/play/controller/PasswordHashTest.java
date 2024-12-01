package de.play.controller;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordHashTest {

    @Test
    public void testPasswordHash() {
        try {
            PasswordHash.passwortSalt = "test".getBytes(Charset.defaultCharset());
            String exptected = "4096:74657374:c0b98ac464080e9c6810f3cf89376fb8748a1e1578352a7d086bf4e16d02beb4d97dfa905d2f982f9e80bb5f192438223dc2c15f85ef95a09562a6cbdc8e7a6b";
            String dummyHashed = PasswordHash.createHashedPassword("dummyy");
            Assertions.assertEquals(exptected, dummyHashed);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordHashTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(PasswordHashTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
