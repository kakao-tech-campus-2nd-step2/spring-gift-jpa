package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.model.Member;
import gift.model.Product;
import gift.model.WishProduct;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishProductRepositoryTest {

    @Autowired
    WishProductRepository wishProductRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductRepository productRepository;

    WishProduct wishProduct;
    Member member;
    Product product;

    @BeforeEach
    void setUp() {
        wishProduct = new WishProduct();
        member = memberRepository.save(new Member("aaa123@a.com", "1234"));
        product = productRepository.save(new Product("productA", 1000, "https://a.com"));
        wishProduct.setMember(member);
        wishProduct.setProduct(product);
    }

    @Test
    @DisplayName("WishProduct insert 테스트")
    void insert() {
        assertThat(wishProduct.getId()).isNull();
        WishProduct saved = wishProductRepository.save(wishProduct);
        assertThat(saved.getId()).isNotNull();

        // 같은 위시상품을 중복해서 담을 수 없다.
        WishProduct duplicated = wishProductRepository.save(wishProduct);
        assertThat(saved).isEqualTo(duplicated);
    }

    @Test
    @DisplayName("WishProduct delete 테스트 ")
    void delete() {
        WishProduct saved = wishProductRepository.save(wishProduct);
        wishProductRepository.delete(saved);

        // 삭제된 위시상품을 조회할 수 없어야 한다.
        assertThat(wishProductRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("WishProduct findAll 테스트")
    void findAll() {
        List<Product> products = new ArrayList<>();
        IntStream.range(0, 10)
            .forEach( i -> {
                products.add(new Product("product"+i, 1000, "https://a.com"));
            });

        products.forEach(
            product -> {
                Product saved = productRepository.save(product);
                WishProduct wishProduct = new WishProduct(member, saved);
                wishProductRepository.save(wishProduct);
            }
        );

        List<WishProduct> findWishes = wishProductRepository.findAll();
        assertThat(findWishes).hasSize(10);

        IntStream.range(0, 10)
                .forEach(i -> {
                    WishProduct w = findWishes.get(i);
                    assertThat(w.getId()).isNotNull();
                    assertThat(w.getProduct()).isEqualTo(products.get(i));
                });

    }


}