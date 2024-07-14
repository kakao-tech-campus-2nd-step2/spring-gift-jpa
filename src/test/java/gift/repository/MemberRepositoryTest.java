package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private  MemberRepository members;
    @Autowired
    private EntityManager entityManager;

    private String expectedEmail;
    private String expectedPassword;
    private Member expectedMember;
    private Product expectedProduct;
    private Wish expectedWish;

    @BeforeEach
    void setupMember() {
        expectedEmail = "a@a.com";
        expectedPassword = "1234";

        expectedMember = new Member(expectedEmail,expectedPassword);
        expectedProduct = new Product("아메리카노", 2000, "http://example.com/americano");
        expectedWish = new Wish(expectedMember, expectedProduct, 1);
    }

    @Test
    @DisplayName("멤버 저장 테스트")
    void save() {
        // when
        Member actual = members.save(expectedMember);

        // then
        assertAll(
                ()->assertThat(actual.getId()).isNotNull(),
                ()->assertThat(actual.getEmail()).isEqualTo(expectedMember.getEmail()),
                ()->assertThat(actual.getPassword()).isEqualTo(expectedMember.getPassword())
        );
    }

    @Test
    @DisplayName("멤버 이메일, 비밀번호로 조회 테스트")
    void findByEmailAndPassword() {
        // given
        Member savedMember = members.save(expectedMember);
        entityManager.flush();
        entityManager.clear();

        // when
        Member findMember = members.findByEmailAndPassword(savedMember.getEmail(),savedMember.getPassword()).get();

        // then
        assertAll(
                () -> assertThat(findMember.getId()).isNotNull(),
                () -> assertThat(findMember.getEmail()).isEqualTo(expectedEmail),
                ()->assertThat(findMember.getPassword()).isEqualTo(expectedPassword)
        );
    }

    @Test
    @DisplayName("멤버 이메일 조회 테스트")
    void findByEmail() {
        // given
        Member savedMember = members.save(expectedMember);
        entityManager.flush();
        entityManager.clear();

        // when
        Member findMember = members.findByEmail(savedMember.getEmail()).get();

        // then
        assertAll(
                () -> assertThat(findMember.getId()).isNotNull(),
                () -> assertThat(findMember.getEmail()).isEqualTo(expectedEmail),
                ()->assertThat(findMember.getPassword()).isEqualTo(expectedPassword)
        );
    }

    @Test
    @DisplayName("멤버->위시 영속 전파 테스트")
    void testCascadePersist(){
        // given
        entityManager.persist(expectedProduct);

        // when
        expectedProduct.addWish(expectedWish);
        Member savedMember = members.save(expectedMember);
        entityManager.flush();
        entityManager.clear();

        // then
        Member foundMember = members.findById(savedMember.getId()).get();
        assertAll(
                () -> assertThat(foundMember).isEqualTo(savedMember),
                () -> assertThat(foundMember.getWishes().size()).isEqualTo(1),
                () -> assertThat(foundMember.getWishes().get(0)).isEqualTo(expectedWish)
        );
    }

    @Test
    @DisplayName("멤버->위시 삭제 전파 테스트")
    void testCascadeRemove(){
        // given
        entityManager.persist(expectedProduct);

        expectedProduct.addWish(expectedWish);
        Member savedMember = members.save(expectedMember);
        entityManager.flush();
        entityManager.clear();

        // when
        Member foundMember = members.findById(savedMember.getId()).get();
        members.deleteById(foundMember.getId());
        entityManager.flush();
        entityManager.clear();

        //then
        List<Member> findMembers = members.findAll();
        Wish deletedWish = entityManager.find(Wish.class, expectedWish.getId());
        assertAll(
                () -> assertThat(findMembers.size()).isEqualTo(0),
                () -> assertThat(deletedWish).isNull()
        );
    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    void testOrphanRemoval(){
        // given
        entityManager.persist(expectedProduct);

        expectedProduct.addWish(expectedWish);
        Member savedMember = members.save(expectedMember);
        entityManager.flush();
        entityManager.clear();

        // when
        Member foundMember = members.findById(savedMember.getId()).get();
        Wish foudnWish = foundMember.getWishes().get(0);
        foundMember.removeWish(foudnWish);
        entityManager.flush();
        entityManager.clear();

        // then
        Wish orphanedWish = entityManager.find(Wish.class, expectedWish.getId());
        assertThat(orphanedWish).isNull();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    void testLazyFetch(){
        // given
        entityManager.persist(expectedProduct);

        expectedProduct.addWish(expectedWish);
        Member savedMember = members.save(expectedMember);
        entityManager.flush();
        entityManager.clear();

        // when
        // Product 조회 (지연 로딩이므로 연관관계 조회 안함, Product 객체만 조회함)
        Member foundMember = members.findById(savedMember.getId()).get();

        // Wish 조회 (Wish 객체도 조회함)
        List<Wish> wishes = foundMember.getWishes();

        // then
        assertAll(
                () -> assertThat(wishes.size()).isEqualTo(1),
                () -> assertThat(wishes.get(0)).isEqualTo(expectedWish)
        );
    }
}