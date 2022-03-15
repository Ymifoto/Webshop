package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.CustomerRepository;
import hu.progmasters.webshop.ui.menuoptions.CustomersMenuOptions;

import java.util.*;

public class CustomerMenu extends Menu {

    private final CustomerRepository customerRepository = new CustomerRepository();

    public void menuOptions() {
        int id;

        CustomersMenuOptions option;
        do {
            option = (CustomersMenuOptions) getMenu(CustomersMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add customer");
                    id = addNewUser();
                    System.out.println(id > 0 ? "Customer added, ID: " + id : "");
                    break;
                case UPDATE:
                    customerSearch();
                    if (yesOrNo("Update customer (y/n): ")) {
                        System.out.print("Customer ID: ");
                        id = inputHandler.getInputNumber();
                        updateCustomer(id);
                    }
                    break;
                case SEARCH:
                    customerSearch();
                    break;
                case SEARCH_BYID:
                    System.out.println("Search customer by ID");
                    customerSearchById();
                    break;
                case ALL_CUSTOMER:
                    customerRepository.getAllCustomer().forEach(System.out::println);
                    break;
                case BACK:
                    break;
            }
        } while (option != CustomersMenuOptions.BACK);
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

    private void customerSearch() {
        System.out.println("Search customers");
        System.out.print("Give a keyword: ");
        String keyword = "%" + inputHandler.getInputString() + "%";
        Set<Customer> founded = customerRepository.customerSearch(keyword);
        System.out.println("Found " + founded.size() + " customer");
        System.out.println();
        founded.forEach(c -> OutputHandler.outputCyan(c.toString()));
    }

    private void customerSearchById() {
        System.out.print("Give a ID: ");
        int id = inputHandler.getInputNumber();
        Customer customer = customerRepository.getCustomerById(id).orElse(null);
        System.out.println(customer != null ? customer : "Not found!");
    }

    private void updateCustomer(int id) {
        Optional<Customer> customer = getCustomerById(id);
        if (customer.isPresent()) {
            getDataForUpdateCustomer(customer.get());
            customerRepository.updateCustomer(customer.get());
        }
    }

    private Customer getDataForNewCustomer() {
        Address shippingAddress = new Address();
        Address billingAddress = new Address();

        System.out.print("Give a name: ");
        String name = inputHandler.getInputString();

        System.out.print("Give a email address: ");
        String email = inputHandler.getInputString();

        System.out.print("Give a zip code: ");
        shippingAddress.setZip(inputHandler.getInputNumber());

        System.out.print("Give a city: ");
        shippingAddress.setCity(inputHandler.getInputString());

        System.out.print("Give a address: ");
        shippingAddress.setStreet(inputHandler.getInputString());

        if (!yesOrNo("Billing and shipping address are the same? (y/n): ")) {
            System.out.print("Give a zip code: ");
            billingAddress.setZip(inputHandler.getInputNumber());
            System.out.print("Give a city: ");
            billingAddress.setCity(inputHandler.getInputString());
            System.out.print("Give a address: ");
            billingAddress.setStreet(inputHandler.getInputString());
            billingAddress.setBillingAddress(true);
        } else {
                billingAddress = shippingAddress.copy();
                billingAddress.setBillingAddress(true);
        }

        if (yesOrNo("Corporate customer? (y/n): ")) {
            System.out.print("Give a company name: ");
            String companyName = inputHandler.getInputString();
            System.out.print("Give a tax number: ");
            String taxNumber = inputHandler.getInputString();
            return new Customer(name, shippingAddress, billingAddress, email, companyName, true, taxNumber);
        }
        return new Customer(name, shippingAddress, billingAddress, email, "", false, "");
    }

    private Map<String, String> getDataForUpdateCustomer(Customer customer) {
        Map<String, String> customerData = new TreeMap<>();
        Map<String, String> customerAddress = new TreeMap<>();

        System.out.print("(" + customer.getName() + ") Name: ");
        customerData.put("name", inputHandler.getInputString());

        System.out.print("(" + customer.getEmail() + ") Email address: ");
        customerData.put("email", inputHandler.getInputString());

        if (!yesOrNo("Update address? (y/n): ")) {
            System.out.print("Give a zip code: ");
            customerAddress.put("zip", String.valueOf(inputHandler.getInputNumber()));
            System.out.print("Give a city: ");
            customerAddress.put("city", inputHandler.getInputString());
            System.out.print("Give a address: ");
            customerAddress.put("street", inputHandler.getInputString());
            customer.getShippingAddress().updateData(customerAddress);
            if (!yesOrNo("Billing and shipping address are the same? (y/n): ")) {
                System.out.print("Give a zip code: ");
                customerAddress.put("zip", inputHandler.getInputString());
                System.out.print("Give a city: ");
                customerAddress.put("city", inputHandler.getInputString());
                System.out.print("Give a address: ");
                customerAddress.put("street", inputHandler.getInputString());
                customer.getBillingAddress().updateData(customerAddress);
            }
            customer.getBillingAddress().updateData(customerAddress);
        }

        if (yesOrNo("Corporate customer? (y/n): ")) {
            customerData.put("company", "1");
            System.out.print("Company name: ");
            customerData.put("company_name", inputHandler.getInputString());
            System.out.print("Tax number: ");
            customerData.put("tax_number", inputHandler.getInputString());
        } else {
            customerData.put("company", "0");
            customerData.put("company_name", "");
            customerData.put("tax_number", "");
        }
        return customerData;
    }
}
