package gift.repository;

import gift.model.Gift;
import gift.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GiftRepositoryTest {

    @Autowired
    private GiftRepository giftRepository;
    @Test
    void saveTest(){
        Gift gift = new Gift("test",1000,"abc.jpg");
        Gift actual = giftRepository.save(gift);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(gift.getName())
        );
    }


}