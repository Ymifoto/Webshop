package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.ui.menuoptions.AdminMenuOptions;

public class AdminMenu extends Menu {

    private final AdminRepository adminRepository = new AdminRepository();

    public void menuOptions() {
        AdminMenuOptions option;

        do {
            option = (AdminMenuOptions) getMenu(AdminMenuOptions.values());
            switch (option) {
                case LOAD_DATA:
                    adminRepository.loadData();
                    break;
                case DELETE_DATA:
                    adminRepository.deleteData();
                    break;
                case CREATE_TABLES:
                    adminRepository.createTables();
                    break;
                case BACK:
            }
        } while (option != AdminMenuOptions.BACK);
    }
}
