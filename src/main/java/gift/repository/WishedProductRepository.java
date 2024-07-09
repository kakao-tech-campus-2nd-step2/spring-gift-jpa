package gift.repository;

import gift.domain.WishedProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishedProductRepository extends JpaRepository<WishedProduct, Long> {
    List<WishedProduct> findByMemberEmail(String email);
}
