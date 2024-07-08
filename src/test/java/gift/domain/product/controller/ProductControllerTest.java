package gift.domain.product.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품 생성 성공")
    void create_success() throws Exception {
        getPostPerform("탕종 블루베리 베이글", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrlPattern("/products/{spring:[0-9]+}"))
        .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("상품 생성 시 이름이 NULL인 경우")
    void create_fail_null_error() throws Exception {
        getPostPerform(null, "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "상품 이름은 필수 입력 필드이며 공백으로만 구성될 수 없습니다."));
    }

    @Test
    @DisplayName("상품 생성 시 가격이 범위를 초과한 경우")
    void create_fail_price_range_error() throws Exception {
        getPostPerform("탕종 블루베리 베이글", "-123", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("priceError"))
        .andExpect(flash().attribute("priceError", "상품 가격은 1 이상 " + Integer.MAX_VALUE + " 이하여야 합니다."));
    }

    @Test
    @DisplayName("상품 생성 시 가격이 int형으로 변환 불가능한 경우")
    void create_fail_price_type_error() throws Exception {
        getPostPerform("탕종 블루베리 베이글", "삼천오백원", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("priceError"))
        .andExpect(flash().attribute("priceError", "잘못된 형식입니다. 상품 가격을 숫자로 입력해주세요."));
    }

    @Test
    @DisplayName("상품 생성 시 형식에 맞지 않는 URL이 입력된 경우")
    void create_fail_URL_format_error() throws Exception {
        getPostPerform("탕종 블루베리 베이글", "3500", "D:")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("imageUrlError"))
        .andExpect(flash().attribute("imageUrlError", "잘못된 URL 형식입니다."));
    }

    @Test
    @DisplayName("상품 생성 시 이름이 15자를 초과한 경우")
    void create_fail_name_size_error() throws Exception {
        getPostPerform("탕종 블루베리 베이글이삼사오육", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "상품 이름은 15자를 초과할 수 없습니다."));
    }

    @Test
    @DisplayName("상품 생성 시 이름에 불가능한 특수문자")
    void create_fail_name_special_charset_error() throws Exception {
        getPostPerform("탕종 블루베리 베이글#", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "(,),[,],+,-,&,/,_ 외의 특수 문자는 사용이 불가능합니다."));
    }

    @Test
    @DisplayName("상품 생성 시 이름에 \"카카오\"가 포함")
    void create_fail_name_kakao_error() throws Exception {
        getPostPerform("카카오빵", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/new"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "\"카카오\"가 포함된 문구는 담당 MD와 협의 후 사용 가능합니다."));
    }

    private ResultActions getPostPerform(String name, String price, String imageUrl) throws Exception {
        return mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Referer", "/products/new")
            .param("name", name)
            .param("price", price)
            .param("imageUrl", imageUrl));
    }

    @Test
    @DisplayName("상품 수정 성공")
    void update_success() throws Exception {
        getPutPerform("탕종 블루베리 베이글", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/1"))
        .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("상품 수정 시 이름이 NULL인 경우")
    void update_fail_null_error() throws Exception {
        getPutPerform(null, "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "상품 이름은 필수 입력 필드이며 공백으로만 구성될 수 없습니다."));
    }

    @Test
    @DisplayName("상품 수정 시 가격이 범위를 초과한 경우")
    void update_fail_price_range_error() throws Exception {
        getPutPerform("탕종 블루베리 베이글", "-123", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("priceError"))
        .andExpect(flash().attribute("priceError", "상품 가격은 1 이상 " + Integer.MAX_VALUE + " 이하여야 합니다."));
    }

    @Test
    @DisplayName("상품 수정 시 가격이 int형으로 변환 불가능한 경우")
    void update_fail_price_type_error() throws Exception {
        getPutPerform("탕종 블루베리 베이글", "1010121000010000", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("priceError"))
        .andExpect(flash().attribute("priceError", "잘못된 형식입니다. 상품 가격을 숫자로 입력해주세요."));
    }

    @Test
    @DisplayName("상품 수정 시 형식에 맞지 않는 URL이 입력된 경우")
    void update_fail_URL_format_error() throws Exception {
        getPutPerform("탕종 블루베리 베이글", "3500", "D:")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("imageUrlError"))
        .andExpect(flash().attribute("imageUrlError", "잘못된 URL 형식입니다."));
    }

    @Test
    @DisplayName("상품 수정 시 이름이 15자를 초과한 경우")
    void update_fail_name_size_error() throws Exception {
        getPutPerform("탕종 블루베리 베이글이삼사오육", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "상품 이름은 15자를 초과할 수 없습니다."));
    }

    @Test
    @DisplayName("상품 수정 시 이름에 불가능한 특수문자")
    void update_fail_name_special_charset_error() throws Exception {
        getPutPerform("탕종 블루베리 베이글#", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "(,),[,],+,-,&,/,_ 외의 특수 문자는 사용이 불가능합니다."));
    }

    @Test
    @DisplayName("상품 수정 시 이름에 \"카카오\"가 포함")
    void update_fail_name_kakao_error() throws Exception {
        getPutPerform("카카오빵", "3500", "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg")
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/products/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attributeExists("nameError"))
        .andExpect(flash().attribute("nameError", "\"카카오\"가 포함된 문구는 담당 MD와 협의 후 사용 가능합니다."));
    }

    private ResultActions getPutPerform(String name, String price, String imageUrl) throws Exception {
        return mockMvc.perform(put("/products/1")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Referer", "/products/edit/1")
            .param("name", name)
            .param("price", price)
            .param("imageUrl", imageUrl));
    }
}