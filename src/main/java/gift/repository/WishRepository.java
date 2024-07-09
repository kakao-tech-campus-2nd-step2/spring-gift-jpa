package gift.repository;

import gift.model.Wish;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    @Override
    List<Wish> findAllById(Iterable<Long> id);

    @Override
    Wish save(Wish wish);

    @Override
    void deleteById(Long id);
}
