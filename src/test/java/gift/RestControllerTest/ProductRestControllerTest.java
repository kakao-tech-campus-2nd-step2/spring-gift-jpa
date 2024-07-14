package gift.RestControllerTest;

import gift.Controller.ProductRestController;
import gift.Service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiJ9.M5YfW43tAR9_HEvIj-1Wgvkc9b_Cg23TZgDRNBoPqdU";

    @Test
    @DisplayName("추가 기능 테스트")
    public void postTest() throws Exception{
        //given

        //when

        //then
    }
}
