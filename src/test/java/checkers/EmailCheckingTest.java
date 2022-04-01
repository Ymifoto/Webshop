package checkers;

import hu.progmasters.webshop.checkers.EmailChecking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailCheckingTest {

    public EmailChecking emailChecking = new EmailChecking();


    @Test
    public void validEmailCheck() {
        assertTrue(emailChecking.check("jhon_doe@gmail.conm"));
    }

    @Test
    public void notValidEmailCheckDoubleDot() {
        assertFalse(emailChecking.check("jhon..doe@gmail.a"));
    }

    @Test
    public void notValidEmailCheckNotSign() {
        assertFalse(emailChecking.check("jhon.doegmail.a"));
    }

}



