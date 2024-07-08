package gift.controller.wishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.WishListAddRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WishListControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getWishList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/wishlist")
                        .header("Authorization","Bearer 1234"))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @ParameterizedTest
    @ValueSource(ints = {1,3,0})
    @DisplayName("위시리스트 update 테스트")
    void updateWishlist(int amount) throws Exception{
        WishListAddRequest request = new WishListAddRequest(4L, amount);
        String json = objectMapper.writeValueAsString(request);
        MvcResult mvcResult = mockMvc.perform(put("/api/wishlist")//테스트용 데이터 사용함. 이 토큰은 유저 1L 가리킴
                        .header("Authorization", "Bearer 1234")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
