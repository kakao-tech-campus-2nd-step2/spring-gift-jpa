package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.wishlist.WishList;
import gift.wishlist.WishListRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {
    @Autowired
    private WishListRepository wishListRepository;

    @Test
    @DisplayName("위시리스트 추가")
    void save(){
        //Given
        WishList wishList = new WishList("admin@email.com",1L,3);

        //When
        WishList actual = wishListRepository.save(wishList);

        //Then
        assertThat(actual.getEmail()).isEqualTo("admin@email.com");
        assertThat(actual.getProductId()).isEqualTo(1L);
        assertThat(actual.getNum()).isEqualTo(3);
    }

    @Test
    @DisplayName("이메일 위시리스트 전체 찾기")
    void findAllByEmail() {
        //Given
        wishListRepository.save(new WishList("admin@email.com",1L,3));
        wishListRepository.save(new WishList("admin@email.com",3L,10));

        //When
        List<WishList> actual = wishListRepository.findAllByEmail("admin@email.com");

        //Then
        assertThat(actual.get(1).getId()).isNotNull();
        assertThat(actual.getFirst().getProductId()).isEqualTo(1L);
        assertThat(actual.getFirst().getNum()).isEqualTo(3);
        assertThat(actual.get(1).getProductId()).isEqualTo(3L);
        assertThat(actual.get(1).getNum()).isEqualTo(10);
    }

    @Test
    @DisplayName("위시리스트 이메일과 상품 아이디로 삭제하기")
    void deleteByEmailAndProductId() {
        //Given
        WishList wishList = new WishList("admin@email.com",1L,3);
        wishListRepository.save(wishList);

        //When
        wishListRepository.deleteByEmailAndProductId("admin@email.com",1L);
        Optional<WishList> actual = wishListRepository.findById(wishList.getId());

        //Then
        assertThat(actual).isNotPresent();
    }
}
