package gift;

import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.TokenRepository;
import gift.repository.WishListRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class RepositoryMockConfiguration {

    @MockBean
    MemberRepository memberRepository;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    TokenRepository tokenRepository;
    @MockBean
    WishListRepository wishListRepository;
    //커밋 테스트2
}
