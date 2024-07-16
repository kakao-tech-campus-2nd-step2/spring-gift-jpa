package gift;

import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import gift.service.MemberService;
import gift.service.ProductService;
import gift.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishService wishService;



    @Test
    void contextLoads() {
        // MemberRepository가 주입되었는지 확인
//        assertThat(memberService).isNotNull();
//        assertThat(productService).isNotNull();
        assertThat(wishService).isNotNull();
    }
}
