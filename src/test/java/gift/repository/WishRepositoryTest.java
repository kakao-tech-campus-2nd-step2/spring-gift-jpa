package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Product;
import gift.entity.Member;
import gift.entity.Wish;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
class WishRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WishRepository wishRepository;

    private Member member;

    private Product product;

    @BeforeEach
    void setUp() throws Exception {
        productRepository.deleteAll();
        memberRepository.deleteAll();
        wishRepository.deleteAll();

        member = memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user", new ArrayList<>()));

        product = productRepository.save(new Product("커피", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"));

    }

    @AfterEach
    void tearDown() throws Exception {
        wishRepository.deleteAll(); //wish부터 먼저 삭제하기
        productRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void save(){
        Wish wish = member.addWish(product);
        Wish actualWish = wishRepository.save(wish);

        assertThat(actualWish.getProduct()).isEqualTo(product);
        assertThat(actualWish.getMember()).isEqualTo(member);
        assertThat(actualWish.getQuantity()).isEqualTo(1);

        member.addWish(product);

        assertThat(actualWish.getQuantity()).isEqualTo(2);
    }

    @Test
    void updateQuantity(){
        Wish wish = member.addWish(product);
        wish.setQuantity(3);

        Wish actualWish = wishRepository.save(wish); //Wish 영속화
        assertThat(actualWish.getQuantity()).isEqualTo(3);

        wish.setQuantity(200);

        assertThat(actualWish.getQuantity()).isEqualTo(200);

    }

    @Test
    void delete(){
        Wish wish = member.addWish(product);
        wishRepository.save(wish);

        assertThat(wishRepository.count()).isEqualTo(1);
        wishRepository.deleteByMemberAndProduct_Id(member, product.getId());
        assertThat(wishRepository.count()).isEqualTo(0);
    }

}