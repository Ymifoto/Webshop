package checkers;

import hu.progmasters.webshop.checkers.ZipCodeChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipCodeCheckerTest {


    public ZipCodeChecker zipCodeChecker = new ZipCodeChecker();


    @Test
    public void validZipTest() {
    assertTrue(zipCodeChecker.check("1154"));
    }

    @Test
    public void notValidZipTest() {
        assertFalse(zipCodeChecker.check("joe"));
    }

}
