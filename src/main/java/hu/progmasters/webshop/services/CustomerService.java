package hu.progmasters.webshop.services;

import hu.progmasters.webshop.checkers.EmailChecking;
import hu.progmasters.webshop.checkers.StringLengthChecker;
import hu.progmasters.webshop.checkers.ZipCodeChecker;
import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CustomerService extends Services {

    public void customerSearch() {
        System.out.println("Search customers");
        System.out.print("Give a keyword: ");
        String keyword = "%" + InputHandler.getInputString() + "%";
        Set<Customer> founded = customerRepository.customerSearch(keyword);
        System.out.println("Found " + founded.size() + " customer");
        System.out.println();
        OutputHandler.printMapIntegerKey(founded.stream().collect(Collectors.toMap(Customer::getId, Customer::toString)), "ID", "Customer");
    }

    public void customerSearchById() {
        System.out.println("Search customer by ID");
        System.out.print("Give a ID: ");
        int id = InputHandler.getInputNumber();
        Customer customer = customerRepository.getCustomerById(id).orElse(null);
        System.out.println(customer != null ? customer : "Not found!");
    }

    public void updateCustomer(int id) {
        Optional<Customer> customer = getCustomerById(id);
        if (customer.isPresent()) {
            getDataForUpdateCustomer(customer.get());
            customerRepository.updateCustomer(customer.get());
        }
    }

    public void getAllCustomer() {
        customerRepository.getAllCustomer().forEach(System.out::println);
    }

    public int addNewUser() {
        return customerRepository.addCustomer(getDataForNewCustomer());
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    private Customer getDataForNewCustomer() {
        Customer customer;
        Address shippingAddress = new Address();

        String name = checkInputData(new StringLengthChecker(),null,"Give a name: ");

        String email = checkInputData(new EmailChecking(), null, "Give a email address: ");

        shippingAddress.setZip(Integer.parseInt(checkInputData(new ZipCodeChecker(), null, "Give a zip code: ")));

        shippingAddress.setCity(checkInputData(new StringLengthChecker(),null,"Give a city: "));

        shippingAddress.setStreet(checkInputData(new StringLengthChecker(),null,"Give a address: "));

        customer = new Customer(null,name,shippingAddress,email,null,false,null,true);

        if (!yesOrNo("Billing and shipping address are the same? (y/n): ")) {
            Address billingAddress = new Address();
            billingAddress.setZip(Integer.parseInt(checkInputData(new ZipCodeChecker(), null, "Give a zip code: ")));
            System.out.print("Give a city: ");
            billingAddress.setCity(InputHandler.getInputString());
            System.out.print("Give a address: ");
            billingAddress.setStreet(InputHandler.getInputString());
            billingAddress.setBillingAddress(false);
            customer.setBillingAddress(billingAddress);
        } else {
            customer.setBillingAddress(shippingAddress);
        }

        if (yesOrNo("Corporate customer? (y/n): ")) {
            System.out.print("Give a company name: ");
            customer.setCompanyName(InputHandler.getInputString());
            System.out.print("Give a tax number: ");
            customer.setTaxNumber(InputHandler.getInputString());
            customer.setCompany(true);
        }
        return customer;
    }

    private void getDataForUpdateCustomer(Customer customer) {
        Map<String, String> customerData = new TreeMap<>();

        customerData.put("name", checkInputData(new StringLengthChecker(),customer.getName(),"Give a name: "));

        customerData.put("email", checkInputData(new EmailChecking(), customer.getEmail(), "Give a email address: "));

        if (yesOrNo("Update shipping address? (y/n): ")) {
            updateAddress(customer.getShippingAddress());
        }

        if (yesOrNo("Update billing address? (y/n): ")) {
            Address billingAddress;
            if (customer.isSameAddress()) {
                billingAddress = customer.getShippingAddress().copyShippingToBilling();
            } else {
                billingAddress = customer.getBillingAddress();
            }
            updateAddress(billingAddress);
            customer.setBillingAddress(billingAddress);
            customer.setSameAddress(false);
        }

        if (yesOrNo("Corporate customer? (y/n): ")) {
            customerData.put("company", "1");
            System.out.print("Company name: ");
            customerData.put("company_name", InputHandler.getInputString());
            System.out.print("Tax number: ");
            customerData.put("tax_number", InputHandler.getInputString());
        } else {
            customerData.put("company", "0");
            customerData.put("company_name", "");
            customerData.put("tax_number", "");
        }
        customer.updateData(customerData);
    }

    private Address updateAddress(Address address) {
        Map<String, String> customerAddress = new TreeMap<>();
        customerAddress.put("zip", checkInputData(new ZipCodeChecker(), String.valueOf(address.getZip()), "Give a zip code: "));
        System.out.print("(" + address.getCity() + ") Give a city: ");
        customerAddress.put("city", InputHandler.getInputString());
        System.out.print("(" + address.getStreet() + ") Give a address: ");
        customerAddress.put("street", InputHandler.getInputString());
        address.updateData(customerAddress);
        return address;
    }
}
