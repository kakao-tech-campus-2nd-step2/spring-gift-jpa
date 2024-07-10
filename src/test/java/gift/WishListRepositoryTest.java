package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.exception.RepositoryException;
import gift.model.WishList;
import gift.service.WishListRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @Test
    void save() {
        WishList wishListExpected = new WishList("test-account@test.com", 1, "product A", 1);
        wishListRepository.save(wishListExpected);
        assertThat(wishListRepository.findAll()).isNotNull();
    }

    @Test
    void findByParameters() {
        WishList wishListExpected_1 = new WishList("test-account-1@test.com", 1, "product A", 1);
        WishList wishListExpected_2 = new WishList("test-account-1@test.com", 1, "product B", 1);
        WishList wishListExpected_3 = new WishList("test-account-2@test.com", 2, "product A", 2);
        WishList wishListExpected_4 = new WishList("test-account-2@test.com", 2, "product C", 1);
        List<WishList> wishLists = new ArrayList<>();
        List<WishList> memberId1_wishLists = new ArrayList<>();
        List<WishList> memberId2_wishLists = new ArrayList<>();

        wishLists.add(wishListExpected_1);
        memberId1_wishLists.add(wishListExpected_1);
        wishLists.add(wishListExpected_2);
        memberId1_wishLists.add(wishListExpected_2);
        wishLists.add(wishListExpected_3);
        memberId2_wishLists.add(wishListExpected_3);
        wishLists.add(wishListExpected_4);
        memberId2_wishLists.add(wishListExpected_4);
        wishListRepository.save(wishListExpected_1);
        wishListRepository.save(wishListExpected_2);
        wishListRepository.save(wishListExpected_3);
        wishListRepository.save(wishListExpected_4);
        assertAll(
            () -> assertThat(wishListRepository.findAll()).isEqualTo(wishLists),
            () -> assertThat(wishListRepository.findByMemberId(1)
                .orElseThrow(() -> new RepositoryException("해당 유저의 위시 리스트가 존재하지 않습니다."))).isEqualTo(
                memberId1_wishLists),
            () -> assertThat(wishListRepository.findByMemberId(2)
                .orElseThrow(() -> new RepositoryException("해당 유저의 위시 리스트가 존재하지 않습니다."))).isEqualTo(
                memberId2_wishLists)
        );
    }

    @Test
    void delete() {
        WishList wishListExpected = new WishList("test-account@test.com", 1, "product A", 1);
        wishListRepository.save(wishListExpected);
        wishListRepository.deleteById(wishListExpected.getId());
        assertThat(wishListRepository.findAll()).isEmpty();

    }

}
