package gift.RepositoryTest;

import gift.Model.Product;
import gift.Model.ResponseWishListDTO;
import gift.Model.WishList;
import gift.Model.WishList;
import gift.Repository.ProductRepository;
import gift.Repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WishListRepositoryTest {
    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void beforEach(){
        productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        productRepository.save(new Product("카푸치노", 4500, "카푸치노url"));
    }

    @Test
    void saveTest() {
        WishList wishList = new WishList("woo6388@naver.com", 1L, 5);
        assertThat(wishList.getId()).isNull();
        var actual = wishListRepository.save(wishList);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findWishListsByEmailTest (){
        WishList wishList1 = wishListRepository.save(new WishList("woo6388@naver.com", 1L, 1));
        WishList wishList2 = wishListRepository.save(new WishList("woo6388@naver.com", 2L, 2));
        List<ResponseWishListDTO> actual = wishListRepository.findWishListsByEmail("woo6388@naver.com");
        assertThat(actual.get(0).getName()).isEqualTo("아메리카노");
        assertThat(actual.get(0).getCount()).isEqualTo(1);
        assertThat(actual.get(1).getName()).isEqualTo("카푸치노");
        assertThat(actual.get(1).getCount()).isEqualTo(2);
    }

    @Test
    void findByEmailAndProductIdTest(){
        WishList wishList1 = wishListRepository.save(new WishList("woo6388@naver.com", 1L, 1));
        WishList wishList2 = wishListRepository.save(new WishList("woo6388@naver.com", 2L, 2));
        Optional<WishList> actual = wishListRepository.findByEmailAndProductId("woo6388@naver.com", 1L);
        assertThat(actual).isPresent();
        assertThat(actual.get().getCount()).isEqualTo(1);
        assertThat(actual.get().getId()).isEqualTo(wishList1.getId());
    }


    @Test
    void updateTest() {
        WishList wishList1 = wishListRepository.save(new WishList("woo6388@naver.com", 1L, 1));
        Optional<WishList> optionalWishList = wishListRepository.findById(wishList1.getId());
        WishList wishList = optionalWishList.get();
        wishList.setCount(5);

        var actual = wishListRepository.findById(wishList.getId());
        assertThat(actual.get().getCount()).isEqualTo(5);
    }



    @Test
    void deleteTest() {
        WishList wishList1 = wishListRepository.save(new WishList("woo6388@naver.com", 1L, 1));
        Optional<WishList> optionalWishList = wishListRepository.findById(wishList1.getId());
        assertThat(optionalWishList).isPresent();
        wishListRepository.deleteById(optionalWishList.get().getId());
        Optional<WishList> actual = wishListRepository.findById(optionalWishList.get().getId());
        assertThat(actual).isEmpty();
    }


}
