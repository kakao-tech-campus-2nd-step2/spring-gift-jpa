package gift.repository;

import gift.common.enums.Role;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findByMemberIdAndProductId() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String[] names = {"test1", "test2"};
        int[] prices = {10, 20};
        String[] imageUrls = {"test1", "test2"};
        Product[] products = {
                productRepository.save(new Product(names[0], prices[0], imageUrls[0])),
                productRepository.save(new Product(names[1], prices[1], imageUrls[1]))};
        int[] productCounts = {1, 2};
        wishRepository.save(new Wish(member, productCounts[0], products[0]));
        wishRepository.save(new Wish(member, productCounts[1], products[1]));

        // when
        Wish[] actuals = {
                wishRepository.findByMemberIdAndProductId(member.getId(), products[0].getId()).orElse(null),
                wishRepository.findByMemberIdAndProductId(member.getId(), products[1].getId()).orElse(null)};

        for(int i=0; i < actuals.length; i++) {
            assertThat(actuals[i].getId()).isNotNull();
            assertThat(actuals[i].getId()).isNotNull();
            assertThat(actuals[i].getMember().getEmail()).isEqualTo(email);
            assertThat(actuals[i].getMember().getPassword()).isEqualTo(password);
            assertThat(actuals[i].getMember().getRole()).isEqualTo(role);

            assertThat(actuals[i].getId()).isNotNull();
            assertThat(actuals[i].getProduct().getName()).isEqualTo(names[i]);
            assertThat(actuals[i].getProduct().getPrice()).isEqualTo(prices[i]);
            assertThat(actuals[i].getProduct().getImageUrl()).isEqualTo(imageUrls[i]);
            assertThat(actuals[i].getProduct().getCreatedAt()).isNotNull();
            assertThat(actuals[i].getProduct().getUpdatedAt()).isNotNull();

            assertThat(actuals[i].getProductCount()).isEqualTo(productCounts[i]);
        }
    }

    @Test
    void findAllByMemberIdOrderByCreatedAtAsc() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String[] names = {"test1", "test2"};
        int[] prices = {10, 20};
        String[] imageUrls = {"test1", "test2"};
        Product[] products = {
                productRepository.save(new Product(names[0], prices[0], imageUrls[0])),
                productRepository.save(new Product(names[1], prices[1], imageUrls[1]))};
        int[] productCounts = {1, 2};
        Wish[] wishes = {
                wishRepository.save(new Wish(member, productCounts[0], products[0])),
                wishRepository.save(new Wish(member, productCounts[1], products[1]))};

        // when
        List<Wish> actuals = wishRepository.findAllByMemberIdOrderByCreatedAtAsc(member.getId());

        // then
        assertThat(actuals).hasSize(wishes.length);
        for(int i=0; i < actuals.size(); i++) {
            assertThat(actuals.get(i).getId()).isNotNull();
            assertThat(actuals.get(i).getId()).isNotNull();
            assertThat(actuals.get(i).getMember().getEmail()).isEqualTo(email);
            assertThat(actuals.get(i).getMember().getPassword()).isEqualTo(password);
            assertThat(actuals.get(i).getMember().getRole()).isEqualTo(role);

            assertThat(actuals.get(i).getId()).isNotNull();
            assertThat(actuals.get(i).getProduct().getName()).isEqualTo(names[i]);
            assertThat(actuals.get(i).getProduct().getPrice()).isEqualTo(prices[i]);
            assertThat(actuals.get(i).getProduct().getImageUrl()).isEqualTo(imageUrls[i]);
            assertThat(actuals.get(i).getProduct().getCreatedAt()).isNotNull();
            assertThat(actuals.get(i).getProduct().getUpdatedAt()).isNotNull();

            assertThat(actuals.get(i).getProductCount()).isEqualTo(productCounts[i]);
        }


        assertThat(actuals.get(0).getCreatedAt()).isBefore(actuals.get(1).getCreatedAt());
    }

    @Test
    void deleteByProductIdAndMemberId() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String name = "product1";
        int price = 1000;
        String imageUrl = "imageUrl";
        Product product = productRepository.save(new Product(name, price, imageUrl));
        int productCount = 10;
        wishRepository.save(new Wish(member, productCount, product));

        // when
        wishRepository.deleteByProductIdAndMemberId(product.getId(), member.getId());
        List<Wish> actuals = wishRepository.findAll();

        // then
        assertThat(actuals.size()).isZero();
    }

    @Test
    void existsByProductIdAndMemberId() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String name = "product1";
        int price = 1000;
        String imageUrl = "imageUrl";
        Product product = productRepository.save(new Product(name, price, imageUrl));
        int productCount = 10;
        wishRepository.save(new Wish(member, productCount, product));

        // when
        boolean actual = wishRepository.existsByProductIdAndMemberId(product.getId(), member.getId());

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Member와 Product의 id만으로 Wish 저장 후 Select 테스트[성공]")
    void saveAndFindTest() {
        // given
        String email = "test@gmail.com";
        String password = "password";
        Role role = Role.USER;
        Member member = memberRepository.save(new Member(email, password, role));
        String name = "product1";
        int price = 1000;
        String imageUrl = "imageUrl";
        Product product = productRepository.save(new Product(name, price, imageUrl));
        int productCount = 10;
        Wish wish = new Wish(new Member(member.getId()), productCount, new Product(product.getId()));

        // when
        Long wishId = wishRepository.save(wish).getId();
        entityManager.clear();  // 영속성 컨텍스트 초기화
        Wish actual = wishRepository.findById(wishId).orElseThrow();

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getMember().getEmail()).isEqualTo(email);
        assertThat(actual.getMember().getPassword()).isEqualTo(password);
        assertThat(actual.getMember().getRole()).isEqualTo(role);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getProduct().getName()).isEqualTo(name);
        assertThat(actual.getProduct().getPrice()).isEqualTo(price);
        assertThat(actual.getProduct().getImageUrl()).isEqualTo(imageUrl);
        assertThat(actual.getProduct().getCreatedAt()).isNotNull();
        assertThat(actual.getProduct().getUpdatedAt()).isNotNull();

        assertThat(actual.getProductCount()).isEqualTo(productCount);
    }
}