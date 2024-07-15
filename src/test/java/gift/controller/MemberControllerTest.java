package gift.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("/sql/truncateIdentity.sql")
class MemberControllerTest {

    private @Autowired MockMvc mockMvc;

    void registerMember(String member) throws Exception {
        mockMvc.perform(post("/api/members/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(member));
    }

    void addProduct(String product) throws Exception {
        mockMvc.perform(post("/api/products/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(product));
    }

    String loginAndGetToken(String member) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/members/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(member)).andReturn();
        return mvcResult.getResponse().getHeader("token");
    }

    @Test
    @DisplayName("회원가입 테스트")
    void registerMember() throws Exception {
        String member = """
            {"email": "sgoh", "password": "sgohpass"}
            """;
        mockMvc.perform(post("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(member))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        String member = """
            {"email": "sgoh", "password": "sgohpass"}
            """;
        registerMember(member);

        mockMvc.perform(post("/api/members/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(member))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("위시 리스트 목록 테스트")
    void wishlist() throws Exception {
        String member = """
            {"email": "sgoh", "password": "sgohpass"}
            """;
        String product = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;
        String product2 = """
            {"name": "커피2", "price": 5500,"imageUrl": "https://..."}
            """;
        registerMember(member);
        addProduct(product);
        addProduct(product2);
        String token = loginAndGetToken(member);

        mockMvc.perform(post("/api/members/wishlist/1")
            .header("Authorization", "Bearer " + token));
        mockMvc.perform(post("/api/members/wishlist/2")
            .header("Authorization", "Bearer " + token));

        mockMvc.perform(get("/api/members/wishlist")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("위시 리스트 추가 테스트")
    void addWishlist() throws Exception {
        String member = """
            {"email": "sgoh", "password": "sgohpass"}
            """;
        String product = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;
        registerMember(member);
        addProduct(product);
        String token = loginAndGetToken(member);

        mockMvc.perform(post("/api/members/wishlist/1")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("위시 리스트 삭제 테스트")
    void deleteWishlist() throws Exception {
        String member = """
            {"email": "sgoh", "password": "sgohpass"}
            """;
        String product = """
            {"name": "커피", "price": 5500,"imageUrl": "https://..."}
            """;
        registerMember(member);
        addProduct(product);
        String token = loginAndGetToken(member);

        mockMvc.perform(post("/api/members/wishlist/1")
            .header("Authorization", "Bearer " + token));
        mockMvc.perform(delete("/api/members/wishlist/1")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }
}