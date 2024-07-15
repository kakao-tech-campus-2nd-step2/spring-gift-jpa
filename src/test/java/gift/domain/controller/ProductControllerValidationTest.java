package gift.domain.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.dto.request.ProductRequest;
import gift.domain.repository.ProductRepository;
import gift.domain.service.ProductService;
import gift.utilForTest.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//TODO: Mock Service, Repository가 주입될 수 있도록 하기
@Import(TestUtil.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerValidationTest {

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(ProductController.class)
            .build();
    }

    @Test
    public void nameLengthViolationAtProductAddTest() throws Exception {
        ProductRequest request = new ProductRequest("", 5_000, "image.jpg");

        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(stringify(request))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                .value("상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다."));

    }

    public String stringify(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }
}
