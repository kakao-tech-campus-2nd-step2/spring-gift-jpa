package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Wish;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class WishRepositoryTest {

  @Autowired
  private WishRepository wishRepository;

  @Test
  public void testSaveAndFindById() {
    Wish wish = new Wish();
    wish.setMemberEmail("test@example.com");
    wish.setProductId(1L);

    wishRepository.save(wish);

    Optional<Wish> foundWish = wishRepository.findById(wish.getId());

    assertThat(foundWish).isPresent();
    assertThat(foundWish.get().getMemberEmail()).isEqualTo("test@example.com");
    assertThat(foundWish.get().getProductId()).isEqualTo(1L);
  }

  @Test
  public void testFindByMemberEmail() {
    Wish wish1 = new Wish();
    wish1.setMemberEmail("test@example.com");
    wish1.setProductId(1L);
    wishRepository.save(wish1);

    Wish wish2 = new Wish();
    wish2.setMemberEmail("test@example.com");
    wish2.setProductId(2L);
    wishRepository.save(wish2);

    List<Wish> wishes = wishRepository.findByMemberEmail("test@example.com");


    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(2);
  }

  @Test
  public void testFindByProductId() {
    Wish wish = new Wish();
    wish.setMemberEmail("test2@example.com");
    wish.setProductId(1L);
    wishRepository.save(wish);

    List<Wish> wishes = wishRepository.findByProductId(1L);


    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(1);
  }

  @Test
  public void testDeleteWish() {
    Wish wish = new Wish();
    wish.setMemberEmail("delete@example.com");
    wish.setProductId(3L);

    wishRepository.save(wish);

    Long wishId = wish.getId();
    wishRepository.deleteById(wishId);

    Optional<Wish> deletedWish = wishRepository.findById(wishId);

    assertThat(deletedWish).isNotPresent();
  }

}
