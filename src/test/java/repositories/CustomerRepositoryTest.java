package repositories;

import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.CustomerRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerRepositoryTest {

    private static final AdminRepository adminRepository = AdminRepository.getRepository();
    private static final CustomerRepository customerRepository = CustomerRepository.getRepository();
    private static final Customer customer = getTestCutomer();

    @BeforeAll
    public static void initDataBase() {
        Repository.setTestMode(true);
        adminRepository.createTables();
        adminRepository.deleteData();
        adminRepository.loadTestData();
    }

    @Test
    public void getCustomerByEmailTest() {
        assertEquals("Jhon Doe",customerRepository.getCustomerByEmail("jhon_doe@gmail.com").get().getName());
    }

    @Test
    public void getCustomerByIdTest() {
        assertEquals("Jhon Doe",customerRepository.getCustomerById(1).get().getName());
    }

    @Test
    public void addCustomerTest() {
        assertEquals(2,customerRepository.getAllCustomer().size());
        customerRepository.addCustomer(customer);
        assertEquals(3,customerRepository.getAllCustomer().size());
        assertEquals("Jack Test",customerRepository.getCustomerByEmail("jack_test@gmail.com").get().getName());
        assertEquals("Jack Test",customerRepository.getCustomerById(3).get().getName());
    }

    @Test
    public void customerSearchTest() {
        assertEquals("Jhon Doe", customerRepository.customerSearch("jhon").stream().findFirst().get().getName());
    }

    private static Customer getTestCutomer() {
        Address shippingAddress = new Address(0,1,2000,"Szentendre","Gyár utca 2.", false);
        Address billingAddress = new Address(0,1,9999,"Világvége","Gyár utca 2.", true);
        Customer customer = new Customer(1,"Jack Test",shippingAddress,"jack_test@gmail.com","Széllengeti Bt.",true,"222222-2-22", false);
        customer.setBillingAddress(billingAddress);
        return customer;
    }
}
