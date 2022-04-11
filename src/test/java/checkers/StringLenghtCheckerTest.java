package checkers;

import hu.progmasters.webshop.checkers.StringLengthChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringLenghtCheckerTest {

    public StringLengthChecker stringLenghtChecker = new StringLengthChecker();


    @Test
    void validStringTest() {
        assertTrue(stringLenghtChecker.check("joe"));
    }

    @Test
    void notValidStringTest() {
        assertFalse(stringLenghtChecker.check(""));
    }

}
