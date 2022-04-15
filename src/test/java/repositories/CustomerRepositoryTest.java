package repositories;

import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.CustomerRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerRepositoryTest {

    private static final AdminRepository adminRepository = new AdminRepository();
    private static final CustomerRepository customerRepository = new CustomerRepository();
    private static final Customer customer = getTestCutomer();

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
    void getCustomerByEmailTest() {
        assertEquals("Jhon Doe",customerRepository.getCustomerByEmail("jhon_doe@gmail.com").orElse(null).getName());
    }

    @Test
    void getCustomerByIdTest_Valid() {
        assertEquals("Jhon Doe",customerRepository.getCustomerById(1).orElse(null).getName());
    }

    @Test
    void getCustomerByIdTest_NotValid() {
        assertNull(customerRepository.getCustomerById(9999).orElse(null));
    }

    @Test
    void addCustomerTest() {
        assertEquals(2,customerRepository.getAllCustomer().size());
        customerRepository.addCustomer(customer);
        assertEquals(3,customerRepository.getAllCustomer().size());
        assertEquals("Jack Test",customerRepository.getCustomerByEmail("jack_test@gmail.com").get().getName());
        assertEquals("Jack Test",customerRepository.getCustomerById(3).get().getName());
    }

    @Test
    void updateCustomerTest() {
        Customer customer = customerRepository.getCustomerById(2).get();
        assertEquals("jane_doe@gmail.com", customer.getEmail());
        Map<String,String> data = new HashMap<>();
        data.put("email","jane_doe.doe@gmail.com");
        customer.updateData(data);
        customerRepository.updateCustomer(customer);
        customer = customerRepository.getCustomerById(2).get();
        assertEquals("jane_doe.doe@gmail.com", customer.getEmail());
    }

    @Test
    void customerSearchTest() {
        assertEquals("Jhon Doe", customerRepository.customerSearch("jhon").stream().findFirst().orElse(null).getName());
    }

    private static Customer getTestCutomer() {
        Address shippingAddress = new Address(0,1,2000,"Szentendre","Gyár utca 2.", false);
        Address billingAddress = new Address(0,1,9999,"Világvége","Gyár utca 2.", true);
        Customer customer = new Customer(1,"Jack Test",shippingAddress,"jack_test@gmail.com","Széllengeti Bt.",true,"222222-2-22", false);
        customer.setBillingAddress(billingAddress);
        return customer;
    }
}
