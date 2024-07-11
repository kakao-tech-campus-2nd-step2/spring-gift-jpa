package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.Model.Wishlist;
import gift.Repository.WishlistRepository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void findAll(){


    }

    @Test
    void save(){

    }

    @Test
    void deleteById(){

    }
}
