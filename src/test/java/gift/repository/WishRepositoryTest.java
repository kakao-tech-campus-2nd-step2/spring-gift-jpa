package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entityForJpa.Item;
import gift.entityForJpa.Member;
import gift.entityForJpa.Wish;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
class WishRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WishRepository wishRepository;

    private Member member;

    private Item item;

    @BeforeEach
    void setUp() throws Exception {
        itemRepository.deleteAll();
        memberRepository.deleteAll();
        wishRepository.deleteAll();

        member = memberRepository.save(new Member("12345@12345.com", "1", "홍길동", "default_user", new ArrayList<>()));

        item = itemRepository.save(new Item("커피", 10000,
                "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"));

    }

    @AfterEach
    void tearDown() throws Exception {
        wishRepository.deleteAll(); //wish부터 먼저 삭제하기
        itemRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void save(){
        Wish wish = member.addWish(item);
        Wish actualWish = wishRepository.save(wish);

        assertThat(actualWish.getItem()).isEqualTo(item);
        assertThat(actualWish.getMember()).isEqualTo(member);
        assertThat(actualWish.getQuantity()).isEqualTo(1);

        member.addWish(item);

        assertThat(actualWish.getQuantity()).isEqualTo(2);
    }

    @Test
    void updateQuantity(){
        Wish wish = member.addWish(item);
        wish.setQuantity(3);

        Wish actualWish = wishRepository.save(wish); //Wish 영속화
        assertThat(actualWish.getQuantity()).isEqualTo(3);

        wish.setQuantity(200);

        assertThat(actualWish.getQuantity()).isEqualTo(200);

    }

    @Test
    void delete(){
        Wish wish = member.addWish(item);
        wishRepository.save(wish);

        assertThat(wishRepository.count()).isEqualTo(1);
        wishRepository.delete(wish);
        assertThat(wishRepository.count()).isEqualTo(0);
    }

}