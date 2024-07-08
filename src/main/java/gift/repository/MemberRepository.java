package gift.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gift.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
