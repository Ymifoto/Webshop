package services;

import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;


    @Test
    void getOnSaleProductsTest() {
        when(productRepository.getStockOrDiscountProducts(any())).thenReturn(List.of());
        assertThat(productService.getOnSaleProducts()).isEmpty();
    }
}
