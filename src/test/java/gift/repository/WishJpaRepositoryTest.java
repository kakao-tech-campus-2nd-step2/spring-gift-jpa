package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.User;
import gift.domain.Wish;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class WishJpaRepositoryTest {

    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    WishJpaRepository wishJpaRepository;
    @Autowired
    TestEntityManager entityManager;

    String email = "aaa@gmail.com";
    String password = "123";
    String accessToken = "aaa";

    @BeforeEach
    void before(){
        User user = userJpaRepository.save(new User(email, password, accessToken));
        user.addWish(new Wish(user, "product1", 3));
//        entityManager.flush();
    }
    @Test
    void findByUser() {
        User user = userJpaRepository.findByEmailAndPassword(email, password).get();
//        User user = new User(1L,email, password, accessToken);

        List<Wish> wishList = wishJpaRepository.findByUser(user, pageable);
        for(Wish wish : wishList){
            assertThat(wish.getProductName()).isEqualTo("product1");
        }
    }

    @Test
    void findByProductName() {
        Wish wish = wishJpaRepository.findByProductName("product1").get();
        assertThat(wish.getId()).isNotNull();
        assertThat(wish.getProductName()).isEqualTo("product1");
    }
}