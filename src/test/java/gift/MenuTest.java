package gift;

import gift.domain.Menu;
import gift.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MenuTest {
    @Autowired
    private MenuRepository menus;

    @Test
    void save() {
        Menu expected = new Menu(null,"우동",3000,"naver.com");
        Menu actual = menus.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        Menu expected = new Menu(null,"우동",3000,"naver.com");
        Menu actual = menus.save(expected);
        assertAll(
                () -> assertThat(menus.findById(actual.getId()).get()).isEqualTo(expected)
        );
    }

    @Test
    void delete() {
        Menu actual = menus.save(new Menu(null,"우동",3000,"naver.com"));
        menus.deleteById(actual.getId());
    }

}
