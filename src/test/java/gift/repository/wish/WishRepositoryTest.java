package gift.repository.wish;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("저장 테스트")
    void 저장_테스트(){
        //given
        Member member = new Member.Builder()
                .email("test@pusan.ac.kr")
                .password("abc")
                .build();

        Product product = new Product.Builder()
                .name("테스트")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Wish wish = new Wish.Builder()
                .member(member)
                .product(product)
                .count(3)
                .build();
        entityManager.persist(member);
        entityManager.persist(product);
        entityManager.flush();

        //when
        Wish savedWish = wishRepository.save(wish);

        //then
        assertAll(
                () -> assertThat(savedWish.getId()).isNotNull(),
                () -> assertThat(savedWish.getMember().getId()).isNotNull(),
                () -> assertThat(savedWish.getMember().getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(savedWish.getProduct().getName()).isEqualTo(product.getName()),
                () -> assertThat(savedWish.getCount()).isEqualTo(wish.getCount())
        );
    }

    @Test
    @DisplayName("단건 조회")
    void 단건_조회_테스트(){
        //given
        Member member = new Member.Builder()
                .email("test@pusan.ac.kr")
                .password("abc")
                .build();

        Product product = new Product.Builder()
                .name("테스트")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Wish wish = new Wish.Builder()
                .member(member)
                .product(product)
                .count(3)
                .build();
        entityManager.persist(member);
        entityManager.persist(product);
        entityManager.flush();

        Wish savedWish = wishRepository.save(wish);
        entityManager.clear();

        //when
        Wish findWish = wishRepository.findById(savedWish.getId()).get();

        //then
        assertAll(
                () -> assertThat(findWish.getId()).isNotNull(),
                () -> assertThat(findWish.getMember().getId()).isNotNull(),
                () -> assertThat(findWish.getMember().getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(findWish.getProduct().getName()).isEqualTo(product.getName()),
                () -> assertThat(findWish.getCount()).isEqualTo(wish.getCount())
        );
    }

    @Test
    @DisplayName("위시 이메일로 조회")
    void 위시_이메일_조회_테스트(){
        //given
        Member member = new Member.Builder()
                .email("test@pusan.ac.kr")
                .password("abc")
                .build();

        Product product1 = new Product.Builder()
                .name("테스트1")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Product product2 = new Product.Builder()
                .name("테스트2")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Wish wish1 = new Wish.Builder()
                .member(member)
                .product(product1)
                .count(3)
                .build();

        Wish wish2 = new Wish.Builder()
                .member(member)
                .product(product2)
                .count(3)
                .build();

        entityManager.persist(member);
        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.flush();

        wishRepository.save(wish1);
        wishRepository.save(wish2);
        entityManager.flush();
        entityManager.clear();

        //when
        List<Wish> findWishes = wishRepository.findWishByByMemberEmail(member.getEmail());

        //then
        assertThat(findWishes.size()).isEqualTo(2);
        assertThat(findWishes.get(0).getProduct().getName()).isEqualTo("테스트1");
    }

    @Test
    @DisplayName("ID 와 EMAIL로 조회")
    void 아이디_이메일_조회(){
        //given
        Member member = new Member.Builder()
                .email("test@pusan.ac.kr")
                .password("abc")
                .build();

        Product product = new Product.Builder()
                .name("테스트")
                .price(123)
                .imageUrl("abc.png")
                .build();

        Wish wish = new Wish.Builder()
                .member(member)
                .product(product)
                .count(3)
                .build();

        entityManager.persist(member);
        entityManager.persist(product);
        entityManager.flush();

        Wish savedWish = wishRepository.save(wish);
        entityManager.flush();
        entityManager.clear();

        //when
        Wish findWish = wishRepository.findWishByIdAndMemberEmail(savedWish.getId(), member.getEmail()).get();
        Optional<Wish> emptyWish = wishRepository.findWishByIdAndMemberEmail(savedWish.getId(), "없는@pusan.ac.kr");

        //then
        assertAll(
                () -> assertThat(findWish.getId()).isNotNull(),
                () -> assertThat(findWish.getMember().getId()).isNotNull(),
                () -> assertThat(findWish.getMember().getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(findWish.getProduct().getName()).isEqualTo(product.getName()),
                () -> assertThat(findWish.getCount()).isEqualTo(wish.getCount()),
                () -> assertThat(emptyWish.isEmpty()).isTrue()
        );
    }


}