package gift.controller.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.common.annotation.LoginMember;
import gift.controller.dto.response.WishResponse;
import gift.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WishesRestControllerTest {

    @InjectMocks
    private WishesRestController wishesRestController;

    @Mock
    private WishService wishService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() throws Exception {
        HandlerMethodArgumentResolver loginMemberResolver = mock(HandlerMethodArgumentResolver.class);
        when(loginMemberResolver.supportsParameter(any()))
                .thenAnswer(invocation -> {
                    MethodParameter parameter = invocation.getArgument(0);
                    return parameter.hasParameterAnnotation(LoginMember.class);
                });
        when(loginMemberResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(wishesRestController)
                .setCustomArgumentResolvers(loginMemberResolver)
                .build();
    }



    @DisplayName("Wish 목록 조회[성공]")
    @Test
    void getWishSuccess() throws Exception {
        // given
        int dataCount = 5;
        doReturn(wishList(dataCount)).when(wishService)
                .findAllByMemberId(1L);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/wishes")
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        WishResponse[] responses = objectMapper.readValue(jsonResponse, WishResponse[].class);

        assertThat(responses).hasSize(dataCount);
        for (int i = 1; i < dataCount; i++) {
            assertThat(responses[i - 1].id()).isEqualTo((i));
            assertThat(responses[i - 1].productCount()).isEqualTo((i + 1));
            assertThat(responses[i - 1].productId()).isEqualTo((i));
            assertThat(responses[i - 1].productName()).isEqualTo(("testProduct"));
            assertThat(responses[i - 1].productPrice()).isEqualTo((i * 1000));
            assertThat(responses[i - 1].productImageUrl()).isEqualTo(("URL"));
        }
    }



    private List<WishResponse> wishList(int count) {
        List<WishResponse> wishList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            wishList.add(new WishResponse((long) i, i + 1, (long)i, "testProduct",
                    i * 1000, "URL", null, null));
        }
        return wishList;
    }

}