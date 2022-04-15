package checkers;

import hu.progmasters.webshop.checkers.EmailChecking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailCheckingTest {

    public EmailChecking emailChecking = new EmailChecking();


    @Test
    void validEmailCheck() {
        assertTrue(emailChecking.check("jhon_doe@gmail.conm"));
    }

    @Test
    void notValidEmailCheckDoubleDot() {
        assertFalse(emailChecking.check("jhon..doe@gmail.a"));
    }

    @Test
    void notValidEmailCheckNotSign() {
        assertFalse(emailChecking.check("jhon.doegmail.a"));
    }

}



