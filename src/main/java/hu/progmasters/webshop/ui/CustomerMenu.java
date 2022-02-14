package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.repositories.CustomerRepository;
import hu.progmasters.webshop.ui.menuoptions.CustomersMenuOptions;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CustomerMenu extends Menu {

    private final CustomerRepository customerRepository = new CustomerRepository();

    public void menuOptions() {
        CustomersMenuOptions option;
        do {
            option = (CustomersMenuOptions) getMenu(CustomersMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add customer");
                    int id = addNewUser();
                    System.out.println(id > 0 ? "Customer added, ID: " + id : "");
                    break;
                case UPDATE:
                    System.out.println("Update customer");
                    break;
                case SEARCH:
                    System.out.println("Search customer");
                    customerSearch();
                    break;
                case SEARCH_BYID:
                    System.out.println("Search customer by ID");
                    customerSearchById();
                    break;
                case BACK:
                    break;
            }
        } while (option != CustomersMenuOptions.BACK);
    }

    private void customerSearch() {
        System.out.println("Search customers");
        System.out.print("Give a keyword: ");
        String keyword = "%" + inputHandler.getInputString() + "%";
        List<Customer> founded = customerRepository.customerSearch(keyword);
        founded.forEach(System.out::println);
        System.out.println("Found " + founded.size() + " customer");
    }

    private void customerSearchById() {
        System.out.println("Search customer by ID");
        System.out.print("Give a ID: ");
        int id = inputHandler.getInputNumber();
        Customer customer = customerRepository.getCustomerById(id);
        System.out.println(customer != null ? customer : "Not found!");
    }

    public Map<String, String> getCustomerData() {
        Map<String, String> customerData = new TreeMap<>();
        System.out.print("Give a name: ");
        customerData.put("name", inputHandler.getInputString());

        System.out.print("Give a email address: ");
        customerData.put("email", inputHandler.getInputString());

        System.out.print("Give a shipping address: ");
        customerData.put("shipping_address", inputHandler.getInputString());

        if (!yesOrNo("Billing and shipping address are the same? (yes or no): ")) {
            System.out.print("Give a billing address: ");
            customerData.put("billing_address", inputHandler.getInputString());
        }

        if (yesOrNo("Corporate customer? (yes or no): ")) {
            customerData.put("company", "1");
            System.out.print("Give a company name: ");
            customerData.put("company_name", inputHandler.getInputString());
            System.out.print("Give a tax number: ");
            customerData.put("tax_number", inputHandler.getInputString());
        }
        return customerData;
    }

    public int addNewUser() {
        return customerRepository.addCustomer(getCustomerData());
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }
}
