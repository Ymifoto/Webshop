package checkers;

import hu.progmasters.webshop.checkers.ZipCodeChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZipCodeCheckerTest {


    public ZipCodeChecker zipCodeChecker = new ZipCodeChecker();


    @Test
    void validZipTest() {
    assertTrue(zipCodeChecker.check("1154"));
    }

    @Test
    void notValidZipTest() {
        assertFalse(zipCodeChecker.check("joe"));
    }

}
