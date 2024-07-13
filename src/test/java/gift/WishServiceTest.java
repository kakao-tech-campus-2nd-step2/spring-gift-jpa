package gift;

import gift.dto.WishResponse;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.repository.WishRepository;
import gift.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WishServiceTest {

    @Autowired
    private WishService wishService;

    @MockBean
    private WishRepository wishRepository;

    private Member member;
    private Product product;
    private Wish wish;

    @BeforeEach
    public void setup() {
        member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .build();

        product = Product.builder()
                .id(1L)
                .name("Product Name")
                .price(100)
                .imageurl("https://cs.kakao.com/images/icon/img_kakaocs.png")
                .build();

        wish = Wish.builder()
                .id(1L)
                .member(member)
                .product(product)
                .build();
    }

    @Test
    public void testGetWishesByMemberId() {
        List<Wish> wishList = Collections.singletonList(wish);
        Page<Wish> wishPage = new PageImpl<>(wishList, PageRequest.of(0, 5), 1);
        Mockito.when(wishRepository.findByMemberId(Mockito.anyLong(), Mockito.any(Pageable.class)))
                .thenReturn(wishPage);

        Page<WishResponse> response = wishService.getWishesByMemberId(member.getId(), PageRequest.of(0, 5));

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getId()).isEqualTo(wish.getId());
        assertThat(response.getContent().get(0).getProductName()).isEqualTo(product.getName());
    }
}