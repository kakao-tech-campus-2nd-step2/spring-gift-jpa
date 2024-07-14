package gift.repository;

import gift.domain.Member;
import gift.domain.WishedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishedProductRepository extends JpaRepository<WishedProduct, Long> {

    Page<WishedProduct> findByMember(Member member, Pageable pageable);
}
