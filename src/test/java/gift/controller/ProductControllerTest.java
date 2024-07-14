package gift.controller;

import gift.controller.product.ProductController;
import gift.model.product.Product;
import gift.repository.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetProducts() throws Exception {
        // given: 테스트에 사용될 mock 데이터를 설정합니다.
        var products = List.of(
            Product.create(1L, "Product1", 100, "http://image1.com"),
            Product.create(2L, "Product2", 200, "http://image2.com")
        );
        // when: productDao.findAll() 메서드가 호출될 때 products를 반환하도록 설정합니다.
        when(productRepository.findAll()).thenReturn(products);

        // then: GET /products 요청을 보내고 반환된 데이터를 검증합니다.
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("Product1"))
            .andExpect(jsonPath("$[0].price").value(100))
            .andExpect(jsonPath("$[0].imageUrl").value("http://image1.com"))
            .andExpect(jsonPath("$[1].name").value("Product2"))
            .andExpect(jsonPath("$[1].price").value(200))
            .andExpect(jsonPath("$[1].imageUrl").value("http://image2.com"));

        // productDao.findAll() 메서드가 한 번 호출되었는지 검증합니다.
        verify(productRepository, times(1)).findAll();
    }


    @Test
    void testGetProduct() throws Exception {
        var product = Product.create(1L, "Product1", 100, "http://image1.com");
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Product1"))
            .andExpect(jsonPath("$.price").value(100))
            .andExpect(jsonPath("$.imageUrl").value("http://image1.com"));

        verify(productRepository, times(1)).findById(anyLong());
    }

}
