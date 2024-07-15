package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Wish;
import gift.entity.Member;
import gift.entity.Product;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishRepositoryTest {

  @Autowired
  private WishRepository wishRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void testSaveAndFindById() {
    Member member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
    memberRepository.save(member);

    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(100);
    product.setImageUrl("http://example.com/image.jpg");
    productRepository.save(product);

    Wish wish = new Wish();
    wish.setMember(member);
    wish.setProduct(product);

    wishRepository.save(wish);

    Optional<Wish> foundWish = wishRepository.findById(wish.getId());

    assertThat(foundWish).isPresent();
    assertThat(foundWish.get().getMember().getEmail()).isEqualTo("test@example.com");
    assertThat(foundWish.get().getProduct().getId()).isEqualTo(product.getId());

  }

  @Test
  public void testFindByMemberEmail() {
    Member member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
    memberRepository.save(member);

    Product product1 = new Product();
    product1.setName("Test Product 1");
    product1.setPrice(100);
    product1.setImageUrl("http://example.com/image1.jpg");
    productRepository.save(product1);

    Product product2 = new Product();
    product2.setName("Test Product 2");
    product2.setPrice(200);
    product2.setImageUrl("http://example.com/image2.jpg");
    productRepository.save(product2);

    Wish wish1 = new Wish();
    wish1.setMember(member);
    wish1.setProduct(product1);
    wishRepository.save(wish1);

    Wish wish2 = new Wish();
    wish2.setMember(member);
    wish2.setProduct(product2);
    wishRepository.save(wish2);

    List<Wish> wishes = wishRepository.findByMember(member);


    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(2);
  }

  @Test
  public void testFindByProductId() {
    Member member = new Member();
    member.setEmail("test2@example.com");
    member.setPassword("password");
    memberRepository.save(member);

    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(100);
    product.setImageUrl("http://example.com/image.jpg");
    productRepository.save(product);

    Wish wish = new Wish();
    wish.setMember(member);
    wish.setProduct(product);
    wishRepository.save(wish);

    List<Wish> wishes = wishRepository.findByProductId(product.getId());

    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(1);
  }

  @Test
  public void testDeleteWish() {
    Member member = new Member();
    member.setEmail("delete@example.com");
    member.setPassword("password");
    memberRepository.save(member);

    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(100);
    product.setImageUrl("http://example.com/image.jpg");
    productRepository.save(product);

    Wish wish = new Wish();
    wish.setMember(member);
    wish.setProduct(product);

    wishRepository.save(wish);

    Long wishId = wish.getId();
    wishRepository.deleteById(wishId);

    Optional<Wish> deletedWish = wishRepository.findById(wishId);

    assertThat(deletedWish).isNotPresent();
  }
}
