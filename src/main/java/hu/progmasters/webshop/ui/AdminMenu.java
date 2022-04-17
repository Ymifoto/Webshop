package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.services.AdminService;
import hu.progmasters.webshop.ui.menuoptions.AdminMenuOptions;

public class AdminMenu extends Menu {

    private final AdminService adminService = new AdminService(new AdminRepository());

    public void menuOptions() {
        AdminMenuOptions option;

        do {
            option = (AdminMenuOptions) getMenu(AdminMenuOptions.values());
            switch (option) {
                case ORDERS:
                    new OrderMenu().menuOptions();
                    break;
                case NEW_PRODUCT:
                    adminService.addProduct();
                    break;
                case UPDATE_PRODUCT:
                    adminService.productUpdate();
                    break;
                case ADD_CATEGORY:
                    adminService.addProductToCategory();
                    break;
                case NEW_CATEGORY:
                    adminService.addNewCategory();
                    break;
                case UPDATE_CATEGORY:
                    adminService.updateCategory();
                    break;
                case CUSTOMER:
                    new CustomerMenu().menuOptions();
                    break;
                case LOAD_DATA:
                    adminService.loadData();
                    break;
                case DROP_TABLES:
                    adminService.dropTables();
                    break;
                case CREATE_TABLES:
                    adminService.createTables();
                    break;
                case BACK:
            }
        } while (option != AdminMenuOptions.BACK);
    }
}
