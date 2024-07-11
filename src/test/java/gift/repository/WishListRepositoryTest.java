package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.entity.WishList;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import gift.entity.Member;
import gift.entity.Product;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishListRepositoryTest {
    
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    private WishList expected;
    private WishList expected2;
    private WishList actual;

    private Member member;
    private Product product1;
    private Product product2;

    private long notExistMemberId;

    @BeforeEach
    void setUp(){

        member = new Member("testPassword", "testEmail", "testRole");
        memberRepository.save(member);
        product1 = new Product("testName", 0, "testUrl");
        product2 = new Product("testName2", 0, "testUrl");
        productRepository.save(product1);
        productRepository.save(product2);


        expected = new WishList(member, product1);
        expected2 = new WishList(member, product2);
        actual = wishListRepository.save(expected);
        notExistMemberId = 2L;
    }

    @Test
    void save(){
        
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMember()).isEqualTo(member)
        );
    }

    @Test
    void findWishListById(){

        List<WishList> expectedList = new ArrayList<>();
        expectedList.add(expected);
        expectedList.add(expected2);

        wishListRepository.save(expected2);
        List<WishList> actual = wishListRepository.findByMemberId(member.getId());

        assertThat(actual.size()).isEqualTo(expectedList.size());
        assertThat(wishListRepository.findByMemberId(notExistMemberId)).isEmpty();

        for (int i = 0; i < expectedList.size(); i++) {
            assertThat(actual.get(i).getProduct()).isEqualTo(expectedList.get(i).getProduct());
        }
    }

    @Test
    void delete(){

        wishListRepository.delete(expected);
        
        Optional<WishList> wishList = wishListRepository.findById(expected.getId());
        assertThat(wishList).isNotPresent();
    }



}
