package gift.repository;

import gift.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

interface WishRepository extends JpaRepository<Member,Long> {

}
