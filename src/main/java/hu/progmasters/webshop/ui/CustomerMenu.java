package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.checkers.EmailChecking;
import hu.progmasters.webshop.checkers.StringLengthChecker;
import hu.progmasters.webshop.checkers.ZipCodeChecker;
import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.CustomerRepository;
import hu.progmasters.webshop.ui.menuoptions.CustomersMenuOptions;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CustomerMenu extends Menu {

    private final CustomerRepository customerRepository = CustomerRepository.getRepository();

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
        OutputHandler.printMapIntegerKey(founded.stream().collect(Collectors.toMap(Customer::getId, Customer::toString)), "ID", "Customer");
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
        Customer customer;
        Address shippingAddress = new Address();

        String name = checkInputData(new StringLengthChecker(),null,"Give a name: ");

        String email = checkInputData(new EmailChecking(), null, "Give a email address: ");

        shippingAddress.setZip(Integer.parseInt(checkInputData(new ZipCodeChecker(), null, "Give a zip code: ")));

        System.out.print("Give a city: ");
        shippingAddress.setCity(inputHandler.getInputString());

        System.out.print("Give a address: ");
        shippingAddress.setStreet(inputHandler.getInputString());

        customer = new Customer(null,name,shippingAddress,email,null,false,null,true);

        if (!yesOrNo("Billing and shipping address are the same? (y/n): ")) {
            Address billingAddress = new Address();
            billingAddress.setZip(Integer.parseInt(checkInputData(new ZipCodeChecker(), null, "Give a zip code: ")));
            System.out.print("Give a city: ");
            billingAddress.setCity(inputHandler.getInputString());
            System.out.print("Give a address: ");
            billingAddress.setStreet(inputHandler.getInputString());
            billingAddress.setBillingAddress(false);
            customer.setBillingAddress(billingAddress);
        } else {
            customer.setBillingAddress(shippingAddress);
        }

        if (yesOrNo("Corporate customer? (y/n): ")) {
            System.out.print("Give a company name: ");
            customer.setCompanyName(inputHandler.getInputString());
            System.out.print("Give a tax number: ");
            customer.setTaxNumber(inputHandler.getInputString());
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
            customerData.put("company_name", inputHandler.getInputString());
            System.out.print("Tax number: ");
            customerData.put("tax_number", inputHandler.getInputString());
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
        customerAddress.put("city", inputHandler.getInputString());
        System.out.print("(" + address.getStreet() + ") Give a address: ");
        customerAddress.put("street", inputHandler.getInputString());
        address.updateData(customerAddress);
        return address;
    }
}
