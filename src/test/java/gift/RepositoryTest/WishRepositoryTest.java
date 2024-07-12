package gift.RepositoryTest;

import gift.Model.Product;
import gift.Model.ResponseWishDTO;
import gift.Model.Wish;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    WishRepository WishRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void beforEach(){
        productRepository.save(new Product("아메리카노", 4000, "아메리카노url"));
        productRepository.save(new Product("카푸치노", 4500, "카푸치노url"));
    }

    @Test
    void saveTest() {
        Wish wish = new Wish("woo6388@naver.com", 1L, 5);
        assertThat(wish.getId()).isNull();
        var actual = WishRepository.save(wish);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void findWishsByEmailTest (){
        Wish wish1 = WishRepository.save(new Wish("woo6388@naver.com", 1L, 1));
        Wish wish2 = WishRepository.save(new Wish("woo6388@naver.com", 2L, 2));
        List<ResponseWishDTO> actual = WishRepository.findWishsByEmail("woo6388@naver.com");
        assertThat(actual.get(0).getName()).isEqualTo("아메리카노");
        assertThat(actual.get(0).getCount()).isEqualTo(1);
        assertThat(actual.get(1).getName()).isEqualTo("카푸치노");
        assertThat(actual.get(1).getCount()).isEqualTo(2);
    }

    @Test
    void findByEmailAndProductIdTest(){
        Wish wish1 = WishRepository.save(new Wish("woo6388@naver.com", 1L, 1));
        Wish wish2 = WishRepository.save(new Wish("woo6388@naver.com", 2L, 2));
        Optional<Wish> actual = WishRepository.findByEmailAndProductId("woo6388@naver.com", 1L);
        assertThat(actual).isPresent();
        assertThat(actual.get().getCount()).isEqualTo(1);
        assertThat(actual.get().getId()).isEqualTo(wish1.getId());
    }


    @Test
    void updateTest() {
        Wish wish1 = WishRepository.save(new Wish("woo6388@naver.com", 1L, 1));
        Optional<Wish> optionalWish = WishRepository.findById(wish1.getId());
        Wish wish = optionalWish.get();
        wish.setCount(5);

        var actual = WishRepository.findById(wish.getId());
        assertThat(actual.get().getCount()).isEqualTo(5);
    }



    @Test
    void deleteTest() {
        Wish wish1 = WishRepository.save(new Wish("woo6388@naver.com", 1L, 1));
        Optional<Wish> optionalWish = WishRepository.findById(wish1.getId());
        assertThat(optionalWish).isPresent();
        WishRepository.deleteById(optionalWish.get().getId());
        Optional<Wish> actual = WishRepository.findById(optionalWish.get().getId());
        assertThat(actual).isEmpty();
    }


}
