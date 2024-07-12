package gift.repository;

import gift.domain.Member;
import gift.domain.WishedProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishedProductRepository extends JpaRepository<WishedProduct, Long> {
    List<WishedProduct> findByMember(Member member);
}
