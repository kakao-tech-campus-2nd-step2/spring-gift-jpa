package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.Model.Member;
import gift.Model.Product;
import gift.Model.Wishlist;
import gift.Repository.MemberRepository;
import gift.Repository.ProductRepository;
import gift.Repository.WishlistRepository;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DirtiesContext
    @Test
    void findAllByEmail(){
        Member member = memberRepository.save(new Member(1L, "1234@google.com","1234"));
        Product expected1 = productRepository.save(new Product(1L,"A",1000,"A"));
        Product expected2 = productRepository.save(new Product(2L,"B",2000,"B"));

        wishlistRepository.addProductInWishlist(member.getId(),expected1.getId());
        wishlistRepository.addProductInWishlist(member.getId(),expected2.getId());
        List<Product> products = wishlistRepository.findAllProductInWishlistByEmail(member.getEmail());
        for(Product a : products){
            System.out.println(a.getId()+" "+a.getName()+" "+a.getPrice()+" "+a.getImageUrl());
        }
        Product actual1 = products.get(0);
        Product actual2 = products.get(1);

        assertAll(
            () -> assertThat(actual1.getId()).isNotNull(),
            () -> assertThat(actual1.getName()).isEqualTo(expected1.getName()),
            () -> assertThat(actual1.getPrice()).isEqualTo(expected1.getPrice()),
            () -> assertThat(actual1.getImageUrl()).isEqualTo(expected1.getImageUrl()),

            () -> assertThat(actual2.getId()).isNotNull(),
            () -> assertThat(actual2.getName()).isEqualTo(expected2.getName()),
            () -> assertThat(actual2.getPrice()).isEqualTo(expected2.getPrice()),
            () -> assertThat(actual2.getImageUrl()).isEqualTo(expected2.getImageUrl())
        );
    }

    @DirtiesContext
    @Test
    void addProductInWishlist(){
        Member expectedMember = memberRepository.save(new Member(1L, "1234@google.com","1234"));
        Product expectedProduct = productRepository.save(new Product(1L,"A",1000,"A"));

        wishlistRepository.addProductInWishlist(expectedMember.getId(), expectedMember.getId());
        Wishlist actual = wishlistRepository.findWishlistById(wishlistRepository.getWishlistIdByMemberEmailAndProductId(expectedMember.getEmail(),expectedProduct.getId()));
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMember().getEmail()).isEqualTo(expectedMember.getEmail()),
            () -> assertThat(actual.getMember().getPassword()).isEqualTo(expectedMember.getPassword()),

            () -> assertThat(actual.getProduct().getName()).isEqualTo(expectedProduct.getName()),
            () -> assertThat(actual.getProduct().getPrice()).isEqualTo(expectedProduct.getPrice()),
            () -> assertThat(actual.getProduct().getImageUrl()).isEqualTo(expectedProduct.getImageUrl())

        );
    }

    @DirtiesContext
    @Test
    void getWishlistId(){
        Member expectedMember = memberRepository.save(new Member(1L, "1234@google.com","1234"));
        Product expectedProduct = productRepository.save(new Product(1L,"A",1000,"A"));

        wishlistRepository.addProductInWishlist(expectedMember.getId(), expectedMember.getId());
        Long actualId = wishlistRepository.getWishlistIdByMemberEmailAndProductId(expectedMember.getEmail(),expectedProduct.getId());

        assertThat(actualId).isEqualTo(1L);// 1개 만 저장했으므로 1L
    }

    @DirtiesContext
    @Test
    void changeProductMemberNull(){
        Member expectedMember = memberRepository.save(new Member(1L, "1234@google.com","1234"));
        Product expectedProduct = productRepository.save(new Product(1L,"A",1000,"A"));

        wishlistRepository.addProductInWishlist(expectedMember.getId(), expectedMember.getId());
        Long actualId = wishlistRepository.getWishlistIdByMemberEmailAndProductId(expectedMember.getEmail(),expectedProduct.getId());
        wishlistRepository.changeProductMemberNull(expectedMember.getEmail(), expectedProduct.getId());
        Wishlist actual = wishlistRepository.findWishlistById(actualId);
        assertAll(
            () -> assertThat(actual.getMember()).isNull(),
            () -> assertThat(actual.getProduct()).isNull()
        );
    }

    @DirtiesContext
    @Test
    void deleteByWishlistId(){
        Member expectedMember = memberRepository.save(new Member(1L, "1234@google.com","1234"));
        Product expectedProduct = productRepository.save(new Product(1L,"A",1000,"A"));

        wishlistRepository.addProductInWishlist(expectedMember.getId(), expectedMember.getId());
        Long actualId = wishlistRepository.getWishlistIdByMemberEmailAndProductId(expectedMember.getEmail(),expectedProduct.getId());
        wishlistRepository.changeProductMemberNull(expectedMember.getEmail(), expectedProduct.getId());
        wishlistRepository.deleteByWishlistId(actualId);

        Wishlist actual = wishlistRepository.findWishlistById(actualId);
        assertThat(actual).isNull();
    }
}
