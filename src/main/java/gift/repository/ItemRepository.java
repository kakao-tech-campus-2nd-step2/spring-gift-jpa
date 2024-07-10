package gift.repository;

import gift.model.item.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findAll();
    Optional<Item> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
}
