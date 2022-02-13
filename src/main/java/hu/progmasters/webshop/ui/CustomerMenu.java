package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.repositories.CustomerRepository;
import hu.progmasters.webshop.ui.menuoptions.CustomersMenuOptions;

import java.util.List;

public class CustomerMenu implements Menu {

    private final CustomerRepository customerRepository = new CustomerRepository();

    public void menuOptions() {
        CustomersMenuOptions option;
        do {
            option = (CustomersMenuOptions) getMenu(CustomersMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add customer");
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
        List<Customer> founded= customerRepository.customerSearch(keyword);
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

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }
}
