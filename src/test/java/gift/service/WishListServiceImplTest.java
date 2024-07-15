package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.database.JpaMemberRepository;
import gift.database.JpaProductRepository;
import gift.database.JpaWishRepository;
import gift.model.Member;
import gift.model.MemberRole;
import gift.model.Product;
import gift.model.Wish;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class WishListServiceImplTest {
    @Autowired
    private JpaWishRepository jpaWishRepository;
    @Autowired
    private JpaMemberRepository jpaMemberRepository;
    @Autowired
    private JpaProductRepository jpaProductRepository;

    @Test
    @DisplayName("새로운 상품 목록 추가")
    void addProduct(){
        //given
        Member member = new Member(null,"hah@ha","1234", MemberRole.COMMON_MEMBER);
        Product product = new Product(null,"tuna",4000, "test");

        member = jpaMemberRepository.save(member);
        product = jpaProductRepository.save(product);

        //when
        Wish wish = new Wish(member,product);
        wish = jpaWishRepository.save(wish);


        //then
        assertThat(jpaWishRepository.findAllByMemberId(member.getId())).contains(wish);
        assertThat(wish.getProduct()).isEqualTo(product);

    }

    @Test
    @DisplayName("새로운 상품 여러개 추가")
    void addMultiProduct(){
        //given
        Member member = new Member(null,"hah@ha","1234", MemberRole.COMMON_MEMBER);
        Product product = new Product(null,"tuna",4000, "test");
        Product product2 = new Product(null,"tuna2",5000, "test2");
        Product product3 = new Product(null,"tuna3",6000, "test3");

        member = jpaMemberRepository.save(member);
        product = jpaProductRepository.save(product);
        product2 = jpaProductRepository.save(product2);
        product3 = jpaProductRepository.save(product3);

        //when

        member.addProduct(product);
        member.addProduct(product2);
        member.addProduct(product3);

        jpaMemberRepository.save(member);
        //then

        assertThat(jpaWishRepository.findAllByMemberId(member.getId())).isNotNull();
        assertThat(jpaWishRepository.findAllByMemberId(member.getId()).stream().anyMatch(wish -> Objects.equals(
            wish.getProduct().getName(), "tuna2"))).isTrue();



    }

}