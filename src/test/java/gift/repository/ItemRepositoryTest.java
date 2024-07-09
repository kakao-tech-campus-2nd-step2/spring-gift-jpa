package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.entityForJpa.Item;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("ProductDao의 insertProduct 메서드에 대응")
    void save() {
        Item expected = new Item("커피", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Item actual = itemRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("커피"),
                () -> assertThat(actual.getPrice()).isEqualTo(10000),
                () -> assertThat(actual.getImageUrl()).isNotNull()
        );
    }

    @Test
    @DisplayName("ProductDao의 selectProduct 메서드에 대응")
    void findAll(){
        Item item1 = new Item("커피1", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Item item2 = new Item("커피2", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> actualList = itemRepository.findAll();
        assertThat(actualList).containsExactlyInAnyOrder(item1, item2);
    }

    @Test
    @DisplayName("ProductDao의 selectOneProduct에 대응")
    void findOne(){
        Item item1 = new Item("커피1", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Item item2 = new Item("커피2", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        itemRepository.save(item1);
        itemRepository.save(item2);

        Item actual = itemRepository.findItemById(item2.getId());

        assertThat(actual).isEqualTo(item2);
    }

    @Test
    @DisplayName("ProductDao의 updateProduct에 대응, 변경감지")
    void update(){
        Item item1 = new Item("커피1", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        Item item2 = new Item("커피2", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");

        itemRepository.save(item1);
        itemRepository.save(item2);

        Item expected = itemRepository.findItemById(item2.getId());

        expected.setPrice(31240941);

        Item actual = itemRepository.findItemById(item2.getId());

        assertThat(actual).isEqualTo(expected);

    }



}

