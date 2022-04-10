package repositories;

import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.CheckoutRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutRepositoryTest {

    private static final AdminRepository adminRepository = AdminRepository.getRepository();
    private static final CheckoutRepository checkoutRepository = CheckoutRepository.getRepository();


    @BeforeAll
    public static void initDataBase() {
        Repository.setTestMode(true);
        if (!Repository.isTestDatabaseCreated()) {
            adminRepository.createTables();
            adminRepository.loadTestData();
            Repository.setTestDatabaseCreated(true);
        }
    }

    @Test
    public void getShippingCostTest() {
        assertEquals(0, checkoutRepository.getShippingCostById(1, 10_000));
        assertEquals(1600, checkoutRepository.getShippingCostById(2, 100_000));
        assertEquals(890, checkoutRepository.getShippingCostById(2, 150_000));
        assertEquals(0, checkoutRepository.getShippingCostById(2, 200_001));
    }

    @Test
    public void getShippingMethodNameTest() {
        assertEquals("Home delivery", checkoutRepository.getShippingMethodName(1));
        assertEquals("Personal receipt", checkoutRepository.getShippingMethodName(2));
    }

    @Test
    public void getPaymentMethodsTest() {
        Map<Integer, String> paymentMethods = checkoutRepository.getPaymentMethods();
        assertEquals("Cash on delivery", paymentMethods.get(1));
        assertEquals("Credit card", paymentMethods.get(2));
    }

    @Test
    public void getInvalidShippingMethodNameTest() {
        assertNull(checkoutRepository.getShippingMethodName(999));
    }

    @Test
    public void getShippingMethodsTest() {
        assertFalse(checkoutRepository.getShippingMethods(1000).isEmpty());
    }
}
