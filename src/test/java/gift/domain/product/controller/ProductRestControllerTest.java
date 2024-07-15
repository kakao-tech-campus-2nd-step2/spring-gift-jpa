package gift.domain.product.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.product.dao.ProductJpaRepository;
import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import gift.domain.product.service.ProductService;
import gift.exception.InvalidProductInfoException;
import java.util.List;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private static final String DEFAULT_URL = "/api/products";
    private static final String PATH_VAR_URL = "/api/products/{productId}";


    @Test
    @DisplayName("상품 생성에 성공하는 경우")
    void create_success() throws Exception {
        // given
        ProductDto productDto = new ProductDto(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        String jsonContent = objectMapper.writeValueAsString(productDto);

        Product product = productDto.toProduct();
        product.setId(1L);

        given(productService.create(any(ProductDto.class))).willReturn(product);
        String expectedResult = objectMapper.writeValueAsString(product);

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().json(expectedResult))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 생성에 실패하는 경우 - 상품 이름이 NULL인 경우")
    void create_fail_null_name_error() throws Exception {
        // given
        ProductDto productDto = new ProductDto(null, null, 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        String jsonContent = objectMapper.writeValueAsString(productDto);

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name", Is.is("상품 이름은 필수 입력 필드이며 공백으로만 구성될 수 없습니다.")));
    }

    @Test
    @DisplayName("상품 생성에 실패하는 경우 - 가격이 int형으로 변환 불가능한 경우")
    void create_fail_price_type_error() throws Exception {
        // given
        String jsonContent = "{ \"name\": \"탕종 블루베리 베이글\", \"price\": \"삼천오백원\", \"imageUrl\": \"https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg\" }";

        // when & then
        mockMvc.perform(post(DEFAULT_URL)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("잘못된 형식입니다. 상품 가격을 숫자로 입력해주세요."));
    }

    @Test
    @DisplayName("상품 전체를 조회하는 경우")
    void readAll_success() throws Exception {
        // given
        List<Product> productList = List.of(
            new Product(1L, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg"),
            new Product(2L, "아이스 카페 아메리카노 T", 4500, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg")
        );
        Page<Product> expectedPage = new PageImpl<>(productList, PageRequest.of(0, 5), productList.size());

        given(productService.readAll(any(Pageable.class))).willReturn(expectedPage);
        String expectedResult = objectMapper.writeValueAsString(expectedPage);

        // when & then
        mockMvc.perform(get(DEFAULT_URL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResult))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 ID로 조회 성공하는 경우")
    void readById_success() throws Exception {
        // given
        Product product = new Product(1L, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        given(productService.readById(anyLong())).willReturn(product);
        String expectedResult = objectMapper.writeValueAsString(product);

        // when & then
        mockMvc.perform(get(PATH_VAR_URL, 1L))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResult))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 ID로 조회 실패하는 경우 - 존재하지 않는 ID")
    void readById_fail_id_error() throws Exception {
        // given
        given(productService.readById(anyLong()))
            .willThrow(new InvalidProductInfoException("error.invalid.product.id"));

        // when & then
        mockMvc.perform(get(PATH_VAR_URL, 1L))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("잘못된 상품 정보입니다."))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 수정에 성공하는 경우")
    void update_success() throws Exception {
        // given
        ProductDto productDto = new ProductDto(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        String jsonContent = objectMapper.writeValueAsString(productDto);

        Product product = productDto.toProduct();
        product.setId(1L);

        given(productService.update(anyLong(), any(ProductDto.class))).willReturn(product);
        String expectedResult = objectMapper.writeValueAsString(product);

        // when & then
        mockMvc.perform(put(PATH_VAR_URL, 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResult));
    }

    @Test
    @DisplayName("상품 수정에 실패하는 경우 - 존재하지 않는 ID")
    void update_fail_id_error() throws Exception {
        // given
        ProductDto productDto = new ProductDto(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        String jsonContent = objectMapper.writeValueAsString(productDto);

        given(productService.update(anyLong(), any(ProductDto.class)))
            .willThrow(new InvalidProductInfoException("error.invalid.product.id"));

        // when & then
        mockMvc.perform(put(PATH_VAR_URL, 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("잘못된 상품 정보입니다."))
            .andDo(print());
    }

    @Test
    @DisplayName("상품 수정에 실패하는 경우 - 이름에 \"카카오\"가 포함")
    void update_fail_kakao_name_error() throws Exception {
        // given
        ProductDto productDto = new ProductDto(null, "카카오빵", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        String jsonContent = objectMapper.writeValueAsString(productDto);

        // when & then
        mockMvc.perform(put(PATH_VAR_URL, 1L)
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name", Is.is("\"카카오\"가 포함된 문구는 담당 MD와 협의 후 사용 가능합니다.")));
    }

    @Test
    @DisplayName("상품 삭제에 성공하는 경우")
    void delete_success() throws Exception {
        // given
        doNothing().when(productService).delete(anyLong());

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_VAR_URL, 1L))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    @DisplayName("상품 삭제에 실패하는 경우 - 존재하지 않는 ID")
    void delete_fail_id_error() throws Exception {
        // given
        willThrow(new InvalidProductInfoException("error.invalid.product.id"))
            .given(productService).delete(anyLong());

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_VAR_URL, 1L))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("잘못된 상품 정보입니다."))
            .andDo(print());
    }
}