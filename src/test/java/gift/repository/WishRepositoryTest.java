package gift.repository;

import gift.vo.Member;
import gift.vo.Product;
import gift.vo.Wish;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishRepositoryTest {

    @Autowired
    private static MemberRepository memberRepository;
    private static ProductRepository productRepository;
    private static WishRepository wishRepository;

    private static Member savedMember;
    private static Product savedProduct1;
    private static Product savedProduct2;

    @BeforeAll
    static void setUp() {
        Member member = new Member("kookies@google.com", "@123");
        Product product1 = new Product("Ice Americano",
                4500,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Product product2 = new Product("Hot Americano",
                4200,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        memberRepository.save(member); // Member pre-save
        savedProduct1 = productRepository.save(product1); // Product pre-save
        savedProduct2 = productRepository.save(product2); // Product pre-save
    }

    @AfterAll
    static void tearDown() {

    }

    @Test
    @DisplayName("Wish save (add)")
    void save() {
        // given
        Wish expected = new Wish(savedMember.getId(), savedProduct1.getId());

        // when
        wishRepository.save(expected);

        // then
        assertThat(expected.getId()).isNotNull();
    }

}