package domain;

import hu.progmasters.webshop.domain.Address;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    private final Address shippingAddress = new Address(0, 1, 2000, "Szentendre", "Gyár utca 2.", false);


    @Test
    void copyShippingToBillingTest() {
        Address newAddress = shippingAddress.copyShippingToBilling();
        assertThat(newAddress)
                .isNotNull()
                .matches(a -> a.getCity().equals(shippingAddress.getCity()))
                .matches(a -> a.getZip() == shippingAddress.getZip())
                .matches(a -> a.getStreet().equals(shippingAddress.getStreet()));
    }

    @Test
    void getDataTest_ShippingAddress() {
        Map<String, String> data = shippingAddress.getData();
        assertThat(data)
                .matches(e -> e.get("billing_address").equals("0"))
                .matches(e -> e.get("street").equals("Gyár utca 2."))
                .matches(e -> e.get("city").equals("Szentendre"));
    }

    @Test
    void updateDataTest() {
        Map<String, String> data = new HashMap<>();
        data.put("zip","9999");
        shippingAddress.updateData(data);
        assertEquals(9999,shippingAddress.getZip());

    }
}
