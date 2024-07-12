package gift.controller.api;

import gift.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setup(@Autowired ProductService productService) {
        for (int i = 0; i < 100; i++) {
            productService.addProduct("AmazingCoffee" + i, 59999, "a.jpg.com");
        }
    }

    @Test
    @DisplayName("Pagination 디폴트 동작 확인")
    void getProducts() throws Exception {
        //@PageableDefault 디폴트는 size = 10, Direction.ASC
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.size").value(10),
                        jsonPath("$.sort.sorted").value(true)
                )
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Pagination 파라미터 조작시 동작 확인")
    void getProducts2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                        .param("page", "1")
                        .param("size", "5")
                        .param("sort", "id,desc"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.size").value(5),
                        jsonPath("$.sort.sorted").value(true),
                        jsonPath("number").value(1)
                )
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
