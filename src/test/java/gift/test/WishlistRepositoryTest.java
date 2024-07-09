package gift.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import gift.model.Product;
import gift.model.SiteUser;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.security.test.context.support.WithMockUser;


@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Order(1)
    @DisplayName("위시리스트에 상품 3개 담기")
    @WithMockUser(username = "testuser")
    void testAddToWishlist() throws Exception {
        // Add first product
        mockMvc.perform(post("/web/wishlist/add")
                .param("productId", "1")
                .param("quantity", "2"))
            .andExpect(status().isOk());

        // Add second product
        mockMvc.perform(post("/web/wishlist/add")
                .param("productId", "2")
                .param("quantity", "3"))
            .andExpect(status().isOk());

        // Add third product
        mockMvc.perform(post("/web/wishlist/add")
                .param("productId", "3")
                .param("quantity", "1"))
            .andExpect(status().isOk());

        // Verify items added to wishlist
        List<Wishlist> wishlist = wishlistRepository.findByUsername("testuser");
        assertThat(wishlist).hasSize(3);
    }



}
