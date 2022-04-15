package repositories;

import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.CheckoutRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SoftAssertionsExtension.class)
class CheckoutRepositoryTest {

    private static final AdminRepository adminRepository = new AdminRepository();
    private static final CheckoutRepository checkoutRepository = new CheckoutRepository();


    @BeforeAll
    static void initDataBase() {
        Repository.setTestMode(true);
        if (!Repository.isTestDatabaseCreated()) {
            adminRepository.createTables();
            adminRepository.loadTestData();
            Repository.setTestDatabaseCreated(true);
        }
    }

    @Test
    void getShippingCostTest() {
        assertEquals(0, checkoutRepository.getShippingCostById(1, 10_000));
        assertEquals(1600, checkoutRepository.getShippingCostById(2, 100_000));
        assertEquals(890, checkoutRepository.getShippingCostById(2, 150_000));
        assertEquals(0, checkoutRepository.getShippingCostById(2, 200_001));
    }

    @Test
    void getShippingMethodNameTest_Valid() {
        assertEquals("Home delivery", checkoutRepository.getShippingMethodName(1));
        assertEquals("Personal receipt", checkoutRepository.getShippingMethodName(2));
    }

    @Test
    void getShippingMethodNameTest_NotValid() {
        assertNull(checkoutRepository.getShippingMethodName(999));
    }

    @Test
    void getPaymentMethodsTest() {
        Map<Integer, String> paymentMethods = checkoutRepository.getPaymentMethods();
        assertEquals("Cash on delivery", paymentMethods.get(1));
        assertEquals("Credit card", paymentMethods.get(2));
    }

    @Test
    void getShippingMethodsTest() {
        assertFalse(checkoutRepository.getShippingMethods(1000).isEmpty());
    }
}
