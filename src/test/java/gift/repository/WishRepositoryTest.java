package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Product;
import gift.entity.Member;
import gift.entity.Wish;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WishRepository wishRepository;

    private Member member;
    private Member member2;

    private Product product;

    @BeforeEach
    void setUp() throws Exception {
        productRepository.deleteAll();
        memberRepository.deleteAll();
        wishRepository.deleteAll();

        member = memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user"));
        member2 = memberRepository.save(new Member("22345@12345.com", "2", "라이언", "default_user"));

        product = productRepository.save(new Product("커피", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"));
        product = productRepository.save(new Product("아이스 카페라뗴", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"));


    }

    @AfterEach
    void tearDown() throws Exception {
        wishRepository.deleteAll();
        productRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("위시리스트 추가")
    void save(){
        Wish wish = new Wish(member, product, 1);
        Wish actualWish = wishRepository.save(wish);

        assertThat(actualWish.getProduct()).isEqualTo(product);
        assertThat(actualWish.getMember()).isEqualTo(member);
        assertThat(actualWish.getQuantity()).isEqualTo(1);

        wish.incrementQuantity();
        wishRepository.flush();

        assertThat(wishRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("위시리스트 개수 수정")
    void updateQuantity(){
        Wish wish = new Wish(member, product, 1);
        wishRepository.save(wish);
        assertThat(wishRepository.findByMemberAndProduct(member, product).get().getQuantity()).isEqualTo(1);
        wish.changeQuantity(3);
        wishRepository.flush();

        assertThat(wishRepository.findByMemberAndProduct(member, product).get().getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("위시리스트 Member & 상품 ID로 제거")
    void delete1(){
        Wish wish = new Wish(member, product, 1);
        wishRepository.save(wish);
        assertThat(wishRepository.count()).isEqualTo(1);

        wishRepository.deleteByMemberAndProductId(member, product.getId());
        assertThat(wishRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("위시리스트 상품 ID로 제거")
    void delete2(){
        Wish wish = new Wish(member, product, 1);
        wishRepository.save(wish);
        Wish wish2 = new Wish(member2, product, 1);
        wishRepository.save(wish2);


        assertThat(wishRepository.count()).isEqualTo(2);
        wishRepository.deleteByProductId(product.getId());
        assertThat(wishRepository.count()).isEqualTo(0);
    }


}