package gift.repository;

import gift.entity.Wish;
import gift.service.ProductService;
import gift.service.WishListService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @BeforeAll
    static void dataInit(@Autowired WishListService wishListService,
                         @Autowired ProductService productService) {
        //Given
        productService.addProduct("almond", 500, "almond.jpg");
        productService.addProduct("choco", 55000, "choco.jpg");
        wishListService.addProductToWishList(MEMBER_ID, 1L, 100);
        wishListService.addProductToWishList(MEMBER_ID, 2L, 100);
    }

    private static final Long MEMBER_ID = 1L;

    @Test
    @Transactional
    void findAllByMemberIdWithProduct() {
        //When
        List<Wish> wishes = wishRepository.findAllByMemberIdWithProduct(MEMBER_ID);

        //Then
        assertThat(wishes).isNotEmpty();
        for (Wish wish : wishes) {
            System.out.println(wish.getProduct().getName());
        }
    }

    @Test
    void findByMemberIdAndProductId() {
        //When
        Optional<Wish> wish = wishRepository.findByMemberIdAndProductId(MEMBER_ID, 1L);

        //Then
        assertThat(wish).isPresent();
        assertThat(wish.get().getProduct().getName()).isEqualTo("almond");
    }

}
