package gift.product.repository;

import gift.product.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaAuthRepository extends JpaRepository<Member, Long>, AuthRepository {

}
