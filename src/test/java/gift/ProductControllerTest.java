package gift;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;  //Spring MockMvc 프레임워크를 사용하여 HTTP 요청 및 응답 테스트

    @Autowired
    private ProductRepository productRepository;

    private Product sampleProduct = new Product(null, "아이스 카페 아메리카노 T", 4500L,
            "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");


    @BeforeEach
    void setUp() {
        productRepository.save(sampleProduct);
    }

    @Test
    @DisplayName("상품 조회 테스트")
    void getProductsTest() throws Exception {
        mockMvc.perform(get("/api/products"))  // 'api/products' 경로로 GET 요청
                .andExpect(status().isOk())  // 응답 상태 코드가 '200 OK'인지 검증
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("아이스 카페 아메리카노 T")))));
    }

    @Test
    @DisplayName("상품 추가 테스트")
    public void addProductTest() throws Exception {
        mockMvc.perform(post("/api/products")  // '/api/products' 경로로 POST 요청
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "복숭아 아이스티 T")
                        .param("price", "5900")
                        .param("imageUrl", "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/products"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("복숭아 아이스티 T")))));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void editProductTest() throws Exception {

        Product existingProduct = productRepository.findAll().get(0);

        mockMvc.perform(put("/api/products/" + existingProduct.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "아이스 카페 아메리카노 V")
                        .param("price", "3000")
                        .param("imageUrl", "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/products"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasItem(hasProperty("name", is("아이스 카페 아메리카노 V")))));
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductTest() throws Exception {

        Product existingProduct = productRepository.findAll().get(0);
        String productName = existingProduct.getName();

        mockMvc.perform(delete("/api/products/" + existingProduct.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/products"));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", not(hasItem(hasProperty("name", is(productName))))));

    }

}
