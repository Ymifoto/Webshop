package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.repositories.CustomerRepository;
import hu.progmasters.webshop.services.CustomerService;
import hu.progmasters.webshop.ui.menuoptions.CustomersMenuOptions;

import java.util.Optional;

public class CustomerMenu extends Menu {

    private final CustomerService customerService = new CustomerService(new CustomerRepository());

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
                    customerService.customerSearch();
                    if (yesOrNo("Update customer (y/n): ")) {
                        System.out.print("Customer ID: ");
                        id = InputHandler.getInputNumber();
                        customerService.updateCustomer(id);
                    }
                    break;
                case SEARCH:
                    customerService.customerSearch();
                    break;
                case SEARCH_BYID:
                    customerService.customerSearchById();
                    break;
                case ALL_CUSTOMER:
                    customerService.getAllCustomer();
                    break;
                case BACK:
                    break;
            }
        } while (option != CustomersMenuOptions.BACK);
    }

    public int addNewUser() {
        return customerService.addNewUser();
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerService.getCustomerByEmail(email);
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerService.getCustomerById(id);
    }
}
